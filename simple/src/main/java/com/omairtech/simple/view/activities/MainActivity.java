package com.omairtech.simple.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.simple.R;

import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.databinding.ActivityMainBinding;
import com.omairtech.simple.view_model.activities.MainVM;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    MainVM vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBinding();
        handleEvent();
    }

    private void initBinding() {
        vm = new ViewModelProvider.NewInstanceFactory().create(MainVM.class);
        vm.init(this);
        binding.setVm(vm);
    }


    private void handleEvent() {
        vm.event.observe(this, event -> {
            switch (event) {
                case "GetActivity":
                    startActivity(new Intent(this, GetActivity.class));
                    break;
                case "PostActivity":
                    startActivity(new Intent(this, PostActivity.class));
                    break;
                case "PutActivity":
                    startActivity(new Intent(this, PutActivity.class));
                    break;
                case "DeleteActivity":
                    startActivity(new Intent(this, DeleteActivity.class));
                    break;
            }
        });
    }
}