package com.kgluszczyk.destinations.repository;

import com.kgluszczyk.destinations.api.DestinationsService;
import com.kgluszczyk.destinations.data.Destination;
import com.kgluszczyk.destinations.data.DestinationDao;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

public class DestinationsRepository {

    @Inject
    DestinationsService destinationsService;

    @Inject
    DestinationDao destinationDao;

    @Inject
    public DestinationsRepository() {
    }

    public Flowable<List<Destination>> fetch() {
        return destinationsService.getDestinationsSingle().toFlowable();

    }

    public boolean replace(final List<Destination> destinations) {
        return destinationDao.replaceAll(destinations);

    }

    public Flowable<List<Destination>> get() {
        return destinationDao.getAllRx();
    }
}
