package com.heady.ecommerce.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.ProductVariants;
import com.heady.ecommerce.model.Variant;
import com.heady.ecommerce.repository.DatabaseCall;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;

import java.util.List;

import javax.inject.Inject;

public class ProductViewModel extends BaseViewModel {
    private MutableLiveData<Resource<ProductVariants>> liveData = new MutableLiveData<>();
    private ProductVariants productVariants;
    @Inject
    DatabaseCall<ProductVariants> databaseCall;
    @Inject
    AppDatabase db;

    Context context;
    private ArrayAdapter<Object> colorsArrayAdapter;
    private ArrayAdapter<String> sizesArrayAdapter;

    {
        ECommerceApp.getHeadyComponent().inject(this);
        context = ECommerceApp.getHeadyComponent().provideContext();
    }

    public MutableLiveData<Resource<ProductVariants>> getProductDetails(int productId) {
        return databaseCall.query(db.productDao().getProductWithVariant(productId), null, liveData);
    }

    public MutableLiveData<Resource<ProductVariants>> getProductVariants() {
        return liveData;
    }


    public ArrayAdapter getColorsAdapter() {
        return colorsArrayAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
    }

    public ArrayAdapter getSizesAdapter() {
        return sizesArrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1);
    }

    public void setColors(List<Variant> variants) {
        if (colorsArrayAdapter != null && variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                colorsArrayAdapter.add(variants.get(i).getColor());
            }
            colorsArrayAdapter.notifyDataSetChanged();
        }
    }

    public void setSizes(List<Variant> variants) {
        if (sizesArrayAdapter != null && variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                sizesArrayAdapter.add(String.valueOf(variants.get(i).getSize()));
            }
            sizesArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        context = null;
    }
}
