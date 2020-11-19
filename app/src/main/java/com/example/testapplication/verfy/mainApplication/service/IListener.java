package com.example.testapplication.verfy.mainApplication.service;


import androidx.annotation.NonNull;

public interface IListener<T> {

    void onSuccess(@NonNull T result);

    void onFailure(@NonNull Exception e);

}
