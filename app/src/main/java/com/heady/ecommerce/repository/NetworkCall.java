package com.heady.ecommerce.repository;

import androidx.lifecycle.MutableLiveData;


import com.heady.ecommerce.application.ECommerceApp;
import com.heady.ecommerce.interfaces.DataSourceCallback;
import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.utils.Constants;
import com.heady.ecommerce.utils.RXJavaHelper;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Wrapper to fetch APIs
 * @param <T>
 */
public class NetworkCall<T> implements Constants {
    private static final String TAG = NetworkCall.class.getSimpleName();
    Single<T> single;
    AppDatabase db;

    @Inject
    public NetworkCall() {
        db = ECommerceApp.getHeadyComponent().provideDatabase();
    }

    public MutableLiveData<Resource<T>> makeCall(Single<T> single) {
        return makeCall(single, null, new MutableLiveData<>());
    }

    public MutableLiveData<Resource<T>> makeCall(Single<T> single, DataSourceCallback callback) {
        return makeCall(single, callback, new MutableLiveData<>());
    }

    public MutableLiveData<Resource<T>> makeCall(Single<T> single, DataSourceCallback callback, MutableLiveData<Resource<T>> result) {
        this.single = single;

        result.postValue(Resource.loading(null));

        //fetch api
        RXJavaHelper.getInstance().dispose(single, new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(T value) {
                if (value != null) {
                    if (callback != null) {
                        callback.onAPIFetched(value);
                    }
                    //result.postValue(Resource.success(value));
                } else {
                    result.postValue(Resource.<T>error(API_ERROR));
                }
            }

            @Override
            public void onError(Throwable e) {
                result.postValue(Resource.<T>error(e.getLocalizedMessage()));
            }
        });
        return result;
    }
}
