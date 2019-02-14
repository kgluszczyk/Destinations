package com.kgluszczyk.destinations.di.module;

import com.kgluszczyk.destinations.view.ItemFragment;
import com.kgluszczyk.destinations.view.ItemFragment.OnListFragmentInteractionListener;
import com.kgluszczyk.destinations.di.annotation.scope.FragmentScope;
import com.kgluszczyk.destinations.view.MainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @Binds
    public abstract OnListFragmentInteractionListener provideOnListFragmentInteractionListener(MainActivity activity);

    @FragmentScope
    @ContributesAndroidInjector
    abstract ItemFragment contributeItemFragment();
}
