package com.omairtech.simple.view.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.databinding.ActivityPostBinding;
import com.omairtech.simple.view_model.activities.PostVM;

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