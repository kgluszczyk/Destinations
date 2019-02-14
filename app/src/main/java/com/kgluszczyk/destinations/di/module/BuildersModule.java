package com.kgluszczyk.destinations.di.module;

import com.kgluszczyk.destinations.view.MainActivity;
import com.kgluszczyk.destinations.di.annotation.scope.ActivityScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivity();
}
