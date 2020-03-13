package com.heady.ecommerce.repository;

import androidx.lifecycle.MutableLiveData;

import com.heady.ecommerce.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NetworkCall<T> implements Constants {
    Single<T> single;
    CompositeDisposable disposable = new CompositeDisposable();
    @Inject
    public NetworkCall() {
    }

    public MutableLiveData<Resource<T>> makeCall(Single<T> single) {
        this.single = single;
        final MutableLiveData<Resource<T>> result = new MutableLiveData<>();
        result.postValue((Resource<T>) Resource.loading(null));
        disposable.add((Disposable) single.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<T>() {
                    @Override
                    public void onSuccess(T value) {
                        if(value!=null){
                            result.postValue(Resource.success(value));
                        }else{
                            result.postValue(Resource.<T>error(API_ERROR));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.postValue(Resource.<T>error(e.getLocalizedMessage()));
                    }
                }));
        return result;
    }
}
