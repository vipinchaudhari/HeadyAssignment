package com.heady.ecommerce.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.view.fragment.ProductsByCategoriesFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = PagerAdapter.class.getSimpleName();
    List<Category> categories = new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    }

    @Override
    public Fragment getItem(int position) {
        return ProductsByCategoriesFragment.newInstance(categories.get(position).getId());
    }

    @Override
    public int getCount() {
        return categories != null ? categories.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
    public void setCategories(List<Category> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }
}
