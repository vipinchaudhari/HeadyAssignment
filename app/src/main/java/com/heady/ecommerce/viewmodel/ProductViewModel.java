package com.heady.ecommerce.viewmodel;

import android.content.Context;
import android.text.TextUtils;
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

import java.util.HashSet;
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

    String size = "";
    String color = "";

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

    public void setSizes(List<Variant> variants) {
        HashSet<String> arrayStrings = new HashSet<String>();
        if (sizesArrayAdapter != null && variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                arrayStrings.add(String.valueOf(variants.get(i).getSize()));
            }
            sizesArrayAdapter.notifyDataSetChanged();
        }
        sizesArrayAdapter.clear();
        sizesArrayAdapter.addAll(arrayStrings);
        sizesArrayAdapter.notifyDataSetChanged();
    }

    public void setColors(List<Variant> variants) {
        HashSet<String> arrayStrings = new HashSet<>();
        if (colorsArrayAdapter != null && variants != null && variants.size() > 0) {
            for (int i = 0; i < variants.size(); i++) {
                arrayStrings.add(variants.get(i).getColor());
            }
        }
        colorsArrayAdapter.clear();
        colorsArrayAdapter.addAll(arrayStrings);
        colorsArrayAdapter.notifyDataSetChanged();
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


    //calculate the price on variant change
    public void onColorChanged(int i) {
        Variant variant;
        variant = productVariants.variants.get(i);
        color = variant.getColor();
        if (TextUtils.equals(String.valueOf(variant.getSize()), size)) {
            setPrice(Double.valueOf(variant.getPrice()));
        } else {
            price.set("0.00");
            total.set("0.00");
        }
    }

    //calculate the price on variant change
    public void onSizeChanged(int i) {
        Variant variant;

        variant = productVariants.variants.get(i);
        size = String.valueOf(variant.getSize());
        if (TextUtils.equals(String.valueOf(variant.getColor()), color)) {
            setPrice(Double.valueOf(variant.getPrice()));
        } else {
            price.set("0.00");
            total.set("0.00");
        }

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        context = null;
    }

}
