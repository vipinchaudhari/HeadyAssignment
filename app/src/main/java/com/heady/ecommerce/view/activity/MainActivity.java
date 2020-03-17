package com.heady.ecommerce.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ActivityMainBinding;
import com.heady.ecommerce.view.fragment.ChildCategoriesFragment;
import com.heady.ecommerce.view.fragment.HomeFragment;
import com.heady.ecommerce.view.fragment.ProductFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        registerStackChangeListener();
        initFragment();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    //common function to insert the fragment into backstack
    public void inflateFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    //common method to display loader
    public void displayLoader() {
        binding.loader.setVisibility(View.VISIBLE);
    }

    //common method to hide loader
    public void hideLoader() {
        binding.loader.setVisibility(View.GONE);
    }

    //handle back button visibility and screen title
    public void registerStackChangeListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    String name = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                    if (TextUtils.equals(ChildCategoriesFragment.class.getSimpleName(), name)) {
                        setTitle(getString(R.string.categories));
                        getSupportActionBar().setHomeButtonEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    } else if (TextUtils.equals(name, ProductFragment.class.getSimpleName())) {
                        setTitle(getString(R.string.products));
                        getSupportActionBar().setHomeButtonEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                } else {
                    setTitle(getString(R.string.app_name));
                    getSupportActionBar().setHomeButtonEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        });
    }
}