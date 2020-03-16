package com.heady.ecommerce.viewmodel;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.model.ProductVariants;
import com.heady.ecommerce.model.Variant;
import com.heady.ecommerce.repository.DatabaseCall;
import com.heady.ecommerce.repository.Resource;
import com.heady.ecommerce.repository.database.AppDatabase;

import java.util.ArrayList;
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

    ObservableField<String> price = new ObservableField<>();
    ObservableField<String> total = new ObservableField<>();

    {
        ECommerceApp.getHeadyComponent().inject(this);
        context = ECommerceApp.getHeadyComponent().provideContext();
    }

    public MutableLiveData<Resource<ProductVariants>> getProductDetails(int productId) {
        return databaseCall.query(db.productDao().getProductWithVariant(productId), null, liveData);
    }

    public LiveData<Resource<ProductVariants>> getProductVariants() {
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

    public ArrayAdapter setColors(List<Variant> variants) {
        ArrayList<String> arrayStrings = new ArrayList<String>();
        if (colorsArrayAdapter != null && variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                arrayStrings.add(variants.get(i).getColor());
            }
        }
        return colorsArrayAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1, arrayStrings);
    }

    public ObservableField<String> getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price.set(String.valueOf(price));
        setTotal();
    }

    public ObservableField<String> getTotal() {
        return total;
    }

    public void setTotal() {
        Double totalPriceWithTaax = productVariants.product.taxValue + Double.parseDouble(price.get());
        this.total.set(String.valueOf(totalPriceWithTaax));
    }

    public void setProductVariants(ProductVariants productVariants) {
        this.productVariants = productVariants;
    }

    public ArrayAdapter setSizes(List<Variant> variants) {
        ArrayList<String> arrayStrings = new ArrayList<String>();
        if (sizesArrayAdapter != null && variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                arrayStrings.add(String.valueOf(variants.get(i).getSize()));
            }
            sizesArrayAdapter.notifyDataSetChanged();
        }
        return sizesArrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_dropdown_item_1line, android.R.id.text1, arrayStrings);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        context = null;
    }
}
