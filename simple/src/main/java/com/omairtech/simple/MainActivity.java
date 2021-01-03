package com.omairtech.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.omairtech.simple.activity.DeleteActivity;
import com.omairtech.simple.activity.GetActivity;
import com.omairtech.simple.activity.PostActivity;
import com.omairtech.simple.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getEx(View view) {
        startActivity(new Intent(this, GetActivity.class));
    }

    public void postEx(View view) {
        startActivity(new Intent(this, PostActivity.class));
    }

    public void putEx(View view) {
        startActivity(new Intent(this, PostActivity.class));
    }

    public void deleteEx(View view) {
        startActivity(new Intent(this, DeleteActivity.class));
    }
}