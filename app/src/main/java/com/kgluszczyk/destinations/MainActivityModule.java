package com.kgluszczyk.destinations;

import com.kgluszczyk.destinations.ItemFragment.OnListFragmentInteractionListener;
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
