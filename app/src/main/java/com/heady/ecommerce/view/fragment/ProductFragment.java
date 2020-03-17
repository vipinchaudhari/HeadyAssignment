package com.heady.ecommerce.view.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.heady.ecommerce.databinding.FragmentProductBinding;
import com.heady.ecommerce.model.ProductVariants;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.viewmodel.ProductViewModel;


public class ProductFragment extends BaseFragment {
    private static String TAG = ProductFragment.class.getSimpleName();
    FragmentProductBinding binding;
    ProductViewModel productViewModel;

    public static Fragment newInstance(int productId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, productId);
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        binding.setViewModel(productViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set the adapters to dropdowns
        binding.ddColor.setAdapter(productViewModel.getColorsAdapter());
        binding.ddSize.setAdapter(productViewModel.getSizesAdapter());

        final int productId = getArguments().getInt(PRODUCT_ID);

        productViewModel.getProductDetails(productId).observe(this, productVariantsResource -> {
            switch (productVariantsResource.status) {
                case SUCCESS:
                    if (productVariantsResource.data != null) {
                        updateView(productVariantsResource.data);
                    }
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });
    }

    private void updateView(ProductVariants productVariants) {
        productViewModel.setProductVariants(productVariants);

        productViewModel.setSizes(productVariants.variants);
        productViewModel.setColors(productVariants.variants);

        binding.ddSize.setOnItemClickListener((parent, view, position, id) -> productViewModel.onSizeChanged(position));
        binding.ddColor.setOnItemClickListener((parent, view, position, id) -> productViewModel.onColorChanged(position));
        binding.setViewModel(productViewModel);
    }
}
