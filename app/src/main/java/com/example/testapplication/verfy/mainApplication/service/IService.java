package com.example.testapplication.verfy.mainApplication.service;




import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public abstract class IService<T> {

    private OkHttpClient client;
    private Response response;
    public static final String SSL = "SSL";

    public abstract String getUrl();

    public void getApi(IListener listener) {
        String mDetailURL = getUrl();
        Request request = new Request.Builder().url(mDetailURL).headers(getHeaders().build()).build();
        response = executeCall(request, listener);
    }

    public Headers.Builder getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("Accept", "*/*");
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
        String cookie = cookieManager.getCookie(getUrl());
        if (cookie != null) {
            builder.add("Cookie", cookie);
        }
        return builder;
    }

    private Response executeCall(Request request, IListener listener) {
        Response responseCall = null;
        try {
            responseCall = getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            // In case of error, logging exception to get to know which call was the one it failed
            listener.onFailure(e);
        }
        return responseCall;
    }

    public OkHttpClient getOkHttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            final TrustManager[] trustAllCerts = getTrustManagers();
            final SSLSocketFactory sslSocketFactory = getSSLSocketFactory(trustAllCerts);
            client = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.SECONDS)
                    .cookieJar(new JavaNetCookieJar(cookieManager))
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            try {
                                URL url = new URL(ServiceUtil.getBaseUrl());
                                if (url != null && url.getHost().equals(hostname))
                                    return true;
                            } catch (MalformedURLException e) {

                            }
                            return false;
                        }
                    }).build();


        return client;
    }

    /**
     * Create a trust manager that does not validate certificate chains
     *
     * @return TrustManager[]
     */
    private static TrustManager[] getTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

    /**
     * Create an ssl socket factory with our all-trusting manager
     *
     * @param trustAllCerts
     * @return SSLSocketFactory
     */
    private static SSLSocketFactory getSSLSocketFactory(TrustManager[] trustAllCerts) {
        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance(SSL);
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {

        }
        return null;
    }
}
