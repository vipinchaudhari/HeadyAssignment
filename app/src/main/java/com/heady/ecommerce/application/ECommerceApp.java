package com.heady.ecommerce.application;

import android.app.Application;

import com.heady.ecommerce.di.ApiModule;
import com.heady.ecommerce.di.ContextModule;
import com.heady.ecommerce.di.DBModule;
import com.heady.ecommerce.di.DaggerHeadyComponent;
import com.heady.ecommerce.di.HeadyComponent;
import com.heady.ecommerce.di.ReactiveXModule;
import com.heady.ecommerce.utils.Constants;


public class ECommerceApp extends Application implements Constants {

    static HeadyComponent headyComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        headyComponent = DaggerHeadyComponent.builder()
                .apiModule(new ApiModule())
                .dBModule(new DBModule(this))
                .contextModule(new ContextModule(this))
                .reactiveXModule(new ReactiveXModule()).build();
    }

    public static HeadyComponent getHeadyComponent() {
        return headyComponent;
    }

}
