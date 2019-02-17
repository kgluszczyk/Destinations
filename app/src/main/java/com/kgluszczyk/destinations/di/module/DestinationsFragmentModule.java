package com.kgluszczyk.destinations.di.module;

import android.arch.lifecycle.ViewModelProviders;
import com.kgluszczyk.destinations.di.annotation.scope.FragmentScope;
import com.kgluszczyk.destinations.presentation.DestinationsViewModel;
import com.kgluszczyk.destinations.view.DestinationsFragment;
import dagger.Module;
import dagger.Provides;

@Module
public class DestinationsFragmentModule {

    @Provides
    @FragmentScope
    DestinationsViewModel provideDestinationsViewModel(DestinationsFragment fragment, DestinationsViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(DestinationsViewModel.class);
    }
}
