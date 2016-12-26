package com.aliakseipilko.flightdutytracker.dagger.modules;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class AppModule {

    private Application mApplication;

    private static final String PREFS_NAME = "prefs";

    public AppModule(Application application){
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(){
        return mApplication.getSharedPreferences(PREFS_NAME, 0);
    }

    @Provides
    @Singleton
    public Context provideApplicationContext(){
        return mApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public Realm provideRealm(){
        return Realm.getInstance(new RealmConfiguration.Builder()
                .name("db.realm")
                .build());
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
        return gsonBuilder.create();
    }
}
