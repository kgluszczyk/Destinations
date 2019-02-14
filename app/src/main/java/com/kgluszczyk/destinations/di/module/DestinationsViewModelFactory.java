package com.kgluszczyk.destinations.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider.Factory;
import android.support.annotation.NonNull;
import com.kgluszczyk.destinations.domain.DestinationsInteractor;
import com.kgluszczyk.destinations.presentation.DestinationsViewModel;
import javax.inject.Inject;

class DestinationsViewModelFactory implements Factory {

    @Inject
    DestinationsInteractor destinationsInteractor;

    @Inject
    DestinationsViewModelFactory() {

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
        return (T) new DestinationsViewModel(destinationsInteractor);
    }
}
