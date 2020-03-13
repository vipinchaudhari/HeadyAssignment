package com.heady.ecommerce.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.heady.ecommerce.BR;
import com.heady.ecommerce.R;
import com.heady.ecommerce.databinding.ItemProductBinding;
import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Product;
import com.heady.ecommerce.viewmodel.HomeViewModel;
import com.heady.ecommerce.viewmodel.ProductByCategoriesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private static final String TAG = ProductAdapter.class.getSimpleName();
    private final ProductByCategoriesViewModel viewModel;
    List<Product> products;

    public ProductAdapter(ProductByCategoriesViewModel viewModel) {
        this.viewModel = viewModel;
        products = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemProductBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_product, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(viewModel, position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        final ItemProductBinding binding;

        ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ProductByCategoriesViewModel viewModel, Integer position) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
