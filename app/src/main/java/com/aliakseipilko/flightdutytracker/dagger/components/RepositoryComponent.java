package com.aliakseipilko.flightdutytracker.dagger.components;

import com.aliakseipilko.flightdutytracker.dagger.modules.RealmModule;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.Realm;

@Singleton
@Component(modules = {RealmModule.class})
public interface RepositoryComponent {
    Realm inject();
}
