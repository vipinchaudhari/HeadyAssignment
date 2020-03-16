package com.heady.ecommerce.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.FragmentChildCategoriesBinding;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.utils.Constants;
import com.heady.ecommerce.view.adapter.CategoryPagerAdapter;
import com.heady.ecommerce.view.adapter.PagerAdapter;
import com.heady.ecommerce.viewmodel.ChildCategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChildCategoriesFragment extends BaseFragment {
    private static final String TAG = ChildCategoriesFragment.class.getSimpleName();
    FragmentChildCategoriesBinding binding;
    private ChildCategoriesViewModel childCategoriesViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childCategoriesViewModel = ViewModelProviders.of(getActivity()).get(ChildCategoriesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_child_categories, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, CATEGORY_ID + getArguments().getInt(CATEGORY_ID));
        childCategoriesViewModel.getChildCategories(getArguments().getInt(CATEGORY_ID))
                .observe(this, new Observer<Resource<List<Category>>>() {
                    @Override
                    public void onChanged(Resource<List<Category>> listResource) {
                        Log.d(TAG, "onChanged() " + listResource.data);
                        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
                        binding.vpProducts.setAdapter(adapter);
                        switch (listResource.status) {
                            case SUCCESS:
                                if (listResource.data != null && listResource.data.size() > 0) {
                                    adapter.setCategories(listResource.data);
                                } else {
                                    listResource.data = new ArrayList<>();
                                    listResource.data.add(new Category(getArguments().getInt(CATEGORY_ID), getArguments().getString(CATEGORY_NAME)));
                                    adapter.setCategories(listResource.data);
                                }
                                break;
                            case ERROR:

                                break;
                            case LOADING:

                                break;

                        }
                    }
                });

    }

}
