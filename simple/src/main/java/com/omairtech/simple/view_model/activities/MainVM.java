package com.omairtech.simple.view_model.activities;

import android.view.View;

import com.omairtech.simple.base.BaseViewModel;

public class MainVM extends BaseViewModel {

    public void getEx(View view) {
        event.setValue("GetActivity");
    }

    public void postEx(View view) {
        event.setValue("PostActivity");
    }

    public void putEx(View view) {
        event.setValue("PutActivity");
    }

    public void deleteEx(View view) {
        event.setValue("DeleteActivity");
    }
}