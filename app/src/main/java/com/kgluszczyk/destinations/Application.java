package com.kgluszczyk.destinations;

import com.kgluszczyk.destinations.di.AppComponent;
import com.kgluszczyk.destinations.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class Application extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent.Builder builder = DaggerAppComponent.builder();
        builder.seedInstance(this);
        return builder.build();
    }
}
