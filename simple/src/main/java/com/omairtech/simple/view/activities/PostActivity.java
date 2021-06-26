package com.omairtech.simple.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.apirequest.ApiRequest;
import com.omairtech.apirequest.enums.InitialTimeout;
import com.omairtech.apirequest.enums.RequestType;
import com.omairtech.apirequest.enums.ResponseType;
import com.omairtech.apirequest.model.NetworkResponse;
import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.databinding.ActivityPostBinding;
import com.omairtech.simple.view_model.activities.PostVM;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

public class PostActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {
        PostVM vm = new ViewModelProvider.NewInstanceFactory().create(PostVM.class);
        vm.init(this);

        ActivityPostBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_post);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Post request");
    }
}