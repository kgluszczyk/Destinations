package com.kgluszczyk.myapplication;

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
