package com.heady.ecommerce.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.FragmentHomeBinding;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.utils.Constants;
import com.heady.ecommerce.viewmodel.HomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment implements Constants {
    private static final String TAG = HomeFragment.class.getSimpleName();
    FragmentHomeBinding binding;
    HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setViewModel(homeViewModel);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        homeViewModel.getCategories().observe(this, new Observer<Resource<List<Category>>>() {
            @Override
            public void onChanged(Resource<List<Category>> listResource) {
                Log.d(TAG, String.valueOf(listResource.data));
                switch (listResource.status) {
                    case SUCCESS:
                        if (listResource.data != null && listResource.data.size() > 0) {
                            homeViewModel.getCategoryAdapter().setCategories(listResource.data);
                        }
                        break;
                    case LOADING:
                        break;
                    case ERROR:
                        break;
                }
            }
        });

        homeViewModel.registerToActions().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                Log.d(TAG, "registerToActions()" + event);
                switch (event.getEvent()) {
                    case CATEGORY_SELECTED:
                        Toast.makeText(getContext(), "category clicked", Toast.LENGTH_LONG).show();
                        ChildCategoriesFragment childCategoriesFragment = new ChildCategoriesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt(CATEGORY_ID, ((Category) event.getData()).id);
                        bundle.putString(CATEGORY_NAME, ((Category) event.getData()).name);
                        childCategoriesFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, childCategoriesFragment)
                                .addToBackStack(ChildCategoriesFragment.class.getName())
                                .commit();
                        break;
                }
            }
        });

    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }
}
