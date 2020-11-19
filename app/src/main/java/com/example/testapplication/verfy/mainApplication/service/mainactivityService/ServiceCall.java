package com.example.testapplication.verfy.mainApplication.service.mainactivityService;


import com.example.testapplication.verfy.mainApplication.service.IListener;
import com.example.testapplication.verfy.mainApplication.service.IService;
import com.example.testapplication.verfy.mainApplication.service.ServiceUtil;

public class ServiceCall extends IService {

    private final String mDetailURL;

    private static final String URL = ServiceUtil.getBaseUrl();

    public ServiceCall(String mDetailURL) {
        this.mDetailURL = mDetailURL;
    }

    public String getUrl(IListener iListener) {

        String requestUrl = URL + "xyz" ;
        return requestUrl;

    }

    @Override
    public String getUrl() {
        String requestUrl = URL + "xyz";
        return requestUrl;
    }
}
