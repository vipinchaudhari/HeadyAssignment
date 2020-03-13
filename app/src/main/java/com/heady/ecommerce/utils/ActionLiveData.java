package com.heady.ecommerce.utils;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class ActionLiveData<T> extends MutableLiveData<T> {

    @MainThread
    public void observe(LifecycleOwner owner, Observer<? super T> observer) {
        if (!hasObservers()) {

        }
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(T t) {

                if (t == null) return;

                observer.onChanged(t);

                setValue(null);
            }
        });
    }

    // Just a nicely named method that wraps setting the value
    @MainThread
    public void sendAction(T data) {
        postValue(data);
    }
}
