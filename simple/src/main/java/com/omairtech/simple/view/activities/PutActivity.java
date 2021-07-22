package com.omairtech.simple.view.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.apirequest.remote.model.NetworkResponse;
import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.databinding.ActivityPutBindingImpl;
import com.omairtech.simple.view_model.activities.PutVM;

public class PutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {
        PutVM vm = new ViewModelProvider.NewInstanceFactory().create(PutVM.class);
        vm.init(this);

        ActivityPutBindingImpl binding = DataBindingUtil.setContentView(this, R.layout.activity_put);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Put request");
    }
}