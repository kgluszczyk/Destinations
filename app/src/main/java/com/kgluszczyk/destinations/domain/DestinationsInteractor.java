package com.kgluszczyk.destinations.domain;

import com.kgluszczyk.destinations.data.Destination;
import com.kgluszczyk.destinations.repository.DestinationsRepository;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

public class DestinationsInteractor {

    @Inject
    DestinationsRepository destinationsRepository;

    @Inject
    public DestinationsInteractor() {
    }

    public Flowable<List<Destination>> fetch() {
        return destinationsRepository.fetch();
    }

    public boolean replace(final List<Destination> destinations) {
       return destinationsRepository.replace(destinations);
    }

    public Flowable<List<Destination>> get() {
        return destinationsRepository.get();
    }
}
