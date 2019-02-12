package com.kgluszczyk.myapplication;

import io.reactivex.Single;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DestinationsService {
    @GET("fake-server/destinations")
    Call<List<Destination>> getDestinationsCall();

    @GET("fake-server/destinations")
    Single<List<Destination>> getDestinationsSingle();
}
