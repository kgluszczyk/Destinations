package com.kgluszczyk.myapplication;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract ItemFragment contributeItemFragment();
}
