package com.heady.ecommerce.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.interfaces.DataSourceCallback;
import com.heady.ecommerce.utils.Constants;
import com.heady.ecommerce.utils.RXJavaHelper;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Wrapper to fire database query
 * @param <T>
 */
public class DatabaseCall<T> implements Constants {
    private static final String TAG = DatabaseCall.class.getSimpleName();
    @Inject
    public DatabaseCall() {

    }

    public MutableLiveData<Resource<T>> query(Single<T> single) {
        return query(single, null, new MutableLiveData<>());
    }

    public MutableLiveData<Resource<T>> query(Single<T> single, DataSourceCallback<T> callback) {
        return query(single, callback, new MutableLiveData<>());
    }

    public MutableLiveData<Resource<T>> query(Single<T> single, DataSourceCallback<T> callback, final MutableLiveData<Resource<T>> result) {

        result.postValue(Resource.loading(null));
        RXJavaHelper.getInstance().dispose(single, new DisposableSingleObserver<T>() {
            @Override
            public void onSuccess(T value) {
                if (value != null) {
                    if (callback != null) {
                        callback.getCacheData(value);
                    }
                    Log.d(TAG,"query() "+value);
                    result.postValue(Resource.success(value));
                } else {
                    result.postValue(Resource.error(Constants.ERROR));
                }
            }

            @Override
            public void onError(Throwable e) {
                result.postValue(Resource.error(e.getLocalizedMessage()));
            }
        });
        return result;
    }
}
