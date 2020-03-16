package com.heady.ecommerce.di;

import android.content.Context;

import androidx.room.Room;

import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule implements Constants {

    private final Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
