package com.heady.ecommerce.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.heady.ecommerce.model.Category;
import com.heady.ecommerce.model.Event;
import com.heady.ecommerce.utils.ActionLiveData;
import com.heady.ecommerce.utils.Constants;

public class BaseViewModel<T> extends ViewModel implements Constants {
    protected ActionLiveData<Event<T>> eventLiveData = new ActionLiveData<Event<T>>();
    public MutableLiveData<Event<T>> registerToActions() {
        eventLiveData.postValue(new Event<T>(NONE, null, 0));
        return eventLiveData;
    }
}
