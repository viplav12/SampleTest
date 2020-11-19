package com.example.testapplication.verfy.mainApplication.ui.mainactivity;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.example.testapplication.verfy.mainApplication.service.IListener;
import com.example.testapplication.verfy.mainApplication.service.mainactivityService.ServiceCall;

public class MainActivityInteractor implements IMainActivityInteractor{


    @Override
    public void signIn(final MainActivityListener listener, final String mDetailURL) {
        AsyncTask<Void, Void, String> execute = new AsyncTask<Void, Void, String>() {
            private Exception mException;

            @Override
            protected String doInBackground(Void... params) {
                ServiceCall service = new ServiceCall(mDetailURL);
                service.getUrl(new IListener() {

                    @Override
                    public void onSuccess(@NonNull Object result) {
                    }

                    @Override
                    public void onFailure(Exception e) {

                        mException = e;
                    }
                });
                return mDetailURL;
            }

            @Override
            protected void onPostExecute(String mUrl) {
                if (mException != null) {
                    listener.onDetailSuccess(mUrl);
                } else {
                    listener.onDetailFailed(mUrl);
                }
            }
        }.execute();
    }

    @Override
    public void signUp(MainActivityListener listener, String serviceUrl) {

    }

}
