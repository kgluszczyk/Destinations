package com.kgluszczyk.destinations.di.module;

import com.kgluszczyk.destinations.di.annotation.scope.FragmentScope;
import com.kgluszczyk.destinations.view.DestinationsFragment;
import com.kgluszczyk.destinations.view.DestinationsFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.destinations.view.MainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @Binds
    public abstract OnListFragmentInteractionListener provideOnListFragmentInteractionListener(MainActivity activity);

    @FragmentScope
    @ContributesAndroidInjector(modules = DestinationsFragmentModule.class)
    abstract DestinationsFragment contributeItemFragment();
}
