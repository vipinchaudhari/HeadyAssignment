package com.heady.ecommerce.view.fragment;

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
import com.heady.ecommerce.databinding.FragmentCategoriesBinding;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.view.activity.MainActivity;
import com.heady.ecommerce.viewmodel.CategoriesViewModel;

import java.util.List;

public class CategoriesFragment extends BaseFragment {
    private static final String TAG = CategoriesFragment.class.getSimpleName();
    FragmentCategoriesBinding binding;
    CategoriesViewModel categoriesViewModel;

    public static Fragment newInstance(int categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, categoryId);
        CategoriesFragment categoriesFragment = new CategoriesFragment();
        categoriesFragment.setArguments(bundle);
        return categoriesFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false);
        binding.setViewModel(categoriesViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, CATEGORY_ID + getArguments().getInt(CATEGORY_ID));
        //register live data to get the categories
        categoriesViewModel.getCategories(getArguments().getInt(CATEGORY_ID))
                .observe(this, listResource -> {
                    Log.d(TAG, "onChange() " + listResource.data);
                    switch (listResource.status) {
                        case SUCCESS:
                            //set category list to viewmodel so that the recycler view will get refreshed
                            if (listResource.data != null && listResource.data.size() > 0) {
                                categoriesViewModel.setCategoryList(listResource.data);
                            }
                            break;
                        case LOADING:
                            break;
                        case ERROR:
                            break;
                    }
                });

        //get the UI clicks
        categoriesViewModel.registerToActions().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                Log.d(TAG, "registerToActions()" + event);
                switch (event.getEvent()) {
                    case CATEGORY_SELECTED:
                        ChildCategoriesFragment childCategoriesFragment = new ChildCategoriesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(CATEGORY_ID, ((Category) event.getData()).id);
                        bundle.putString(CATEGORY_NAME, ((Category) event.getData()).name);
                        childCategoriesFragment.setArguments(bundle);
                        ((MainActivity) getActivity()).inflateFragment(childCategoriesFragment);
                        break;
                }
            }
        });
    }
}
