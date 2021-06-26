package com.omairtech.simple.view.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.omairtech.simple.R;
import com.omairtech.simple.base.BaseActivity;
import com.omairtech.simple.databinding.ActivityDeleteBinding;
import com.omairtech.simple.view_model.activities.DeleteVM;

public class DeleteActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
    }

    private void initBinding() {
        DeleteVM vm = new ViewModelProvider.NewInstanceFactory().create(DeleteVM.class);
        vm.init(this);

        ActivityDeleteBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_delete);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Delete request");
    }
}