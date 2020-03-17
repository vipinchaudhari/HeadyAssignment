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
import com.heady.ecommerce.databinding.FragmentProductsByCategoriesBinding;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.view.activity.MainActivity;
import com.heady.ecommerce.viewmodel.ProductByCategoriesViewModel;

import java.util.List;

public class ProductsByCategoriesFragment extends BaseFragment {
    private static final String TAG = ProductsByCategoriesFragment.class.getSimpleName();
    FragmentProductsByCategoriesBinding binding;
    ProductByCategoriesViewModel productByCategoriesViewModel;

    public static Fragment newInstance(int categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, categoryId);
        ProductsByCategoriesFragment productsByCategoriesFragment = new ProductsByCategoriesFragment();
        productsByCategoriesFragment.setArguments(bundle);
        return productsByCategoriesFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productByCategoriesViewModel = ViewModelProviders.of(this).get(ProductByCategoriesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_by_categories, container, false);
        binding.setViewModel(productByCategoriesViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, CATEGORY_ID + getArguments().getInt(CATEGORY_ID));

        productByCategoriesViewModel.getProductsByCategory(getArguments().getInt(CATEGORY_ID))
                .observe(this, new Observer<Resource<List<Product>>>() {
                    @Override
                    public void onChanged(Resource<List<Product>> listResource) {
                        Log.d(TAG, "onChange() " + listResource.data);
                        switch (listResource.status) {
                            case SUCCESS:
                                if (listResource.data != null && listResource.data.size() > 0) {
                                    productByCategoriesViewModel.setProducts(listResource.data);
                                }
                                break;
                            case LOADING:
                                break;
                            case ERROR:
                                break;
                        }
                    }
                });

        productByCategoriesViewModel.registerToActions().observe(this, (Observer<Event<Product>>) event -> {
            switch (event.getEvent()) {
                case PRODUCT_SELECTED:
                    gotoProduct(event.getData());
                    break;
            }
        });

        initFilters();
    }

    //filter the products
    private void initFilters() {
        binding.cgFilters.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.cMostPurchased:
                    productByCategoriesViewModel.setPurchasedFilter(MOST_PURCHARED_FILTER);
                    break;
                case R.id.cMostShared:
                    productByCategoriesViewModel.setSharedFilter(MOST_SHARED_FILTER);
                    break;
                case R.id.cMostViewed:
                    productByCategoriesViewModel.setViewedFilter(MOST_VIEW_FILTER);
                    break;
                default:
                    productByCategoriesViewModel.getProductsByCategory(getArguments().getInt(CATEGORY_ID));
            }

        });
    }

    private void gotoProduct(final Product product) {
        ((MainActivity) getActivity()).inflateFragment(ProductFragment.newInstance(product.getId()));
    }
}
