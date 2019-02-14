package com.kgluszczyk.destinations.api;

import com.kgluszczyk.destinations.data.Destination;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;

public interface DestinationsService {
    @GET("fake-server/destinations")
    Single<List<Destination>> getDestinationsSingle();
}
