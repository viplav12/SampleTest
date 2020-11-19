package com.example.testapplication.verfy.mainApplication.ui.mainactivity;



public interface IMainActivityInteractor {

    void signIn(MainActivityListener listener, String serviceUrl);
    void signUp(MainActivityListener listener, String serviceUrl);

}
