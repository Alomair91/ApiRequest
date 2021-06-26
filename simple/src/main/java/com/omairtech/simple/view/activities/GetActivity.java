package com.omairtech.simple.view.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.databinding.ActivityGetBinding;
import com.omairtech.simple.view_model.activities.GetVM;

public class GetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {
        GetVM vm = new ViewModelProvider.NewInstanceFactory().create(GetVM.class);
        vm.init(this);

        ActivityGetBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_get);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Get request");
    }
}