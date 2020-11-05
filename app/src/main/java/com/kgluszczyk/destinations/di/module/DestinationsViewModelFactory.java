package com.kgluszczyk.destinations.di.module;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.kgluszczyk.destinations.domain.DestinationsInteractor;
import com.kgluszczyk.destinations.presentation.DestinationsViewModel;
import javax.inject.Inject;

class DestinationsViewModelFactory implements ViewModelProvider.Factory {

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
