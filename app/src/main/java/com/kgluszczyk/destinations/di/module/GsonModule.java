package com.kgluszczyk.destinations.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class GsonModule {

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }
}
