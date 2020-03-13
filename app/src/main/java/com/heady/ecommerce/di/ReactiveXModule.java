package com.heady.ecommerce.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ReactiveXModule {

    @Singleton
    @Provides
    public CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }
}
