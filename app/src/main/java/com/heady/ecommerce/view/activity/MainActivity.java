package com.heady.ecommerce.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ActivityMainBinding;
import com.heady.ecommerce.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initFragment();
    }

    public void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    public void inflateFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }
    public void displayLoader(){
        binding.loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        binding.loader.setVisibility(View.GONE);
    }
}