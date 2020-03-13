package com.heady.ecommerce.di;

import com.heady.ecommerce.repository.HeadyApi;
import com.heady.ecommerce.repository.HeadyService;
import com.heady.ecommerce.repository.NetworkCall;
import com.heady.ecommerce.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule implements Constants {

    @Singleton
    @Provides
    public HeadyApi provideHeadyApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HeadyApi.class);
    }

    @Provides
    public HeadyService provideHeadyService() {
        return new HeadyService();
    }

    @Provides
    public NetworkCall provideNetworkCall() {
        return new NetworkCall();
    }
}
