package com.heady.ecommerce.di;

import android.content.Context;

import androidx.room.Room;

import com.heady.ecommerce.repository.database.AppDatabase;
import com.heady.ecommerce.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule implements Constants {

    private final Context context;

    public DBModule(Context context){
        this.context = context;
    }

    @Singleton
    @Provides
    public AppDatabase provideDatabase(){
        return Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME).build();
    }
}
