package com.aliakseipilko.flightdutytracker.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {

    @Provides
    @Singleton
    public Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
