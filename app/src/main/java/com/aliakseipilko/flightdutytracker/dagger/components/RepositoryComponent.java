package com.aliakseipilko.flightdutytracker.dagger.components;

import com.aliakseipilko.flightdutytracker.dagger.modules.RealmModule;
import com.aliakseipilko.flightdutytracker.realm.repository.impl.FlightRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RealmModule.class})
public interface RepositoryComponent {
    void inject(FlightRepository repository);
}
