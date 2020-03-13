package com.heady.ecommerce.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.heady.ecommerce.R;
import com.heady.ecommerce.view.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    public void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }
}