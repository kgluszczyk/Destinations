package com.kgluszczyk.destinations.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kgluszczyk.destinations.api.DestinationsService;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final int CONNECTION_TIMEOUT_S = 20;

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        Builder builder = new Builder()
                .readTimeout(CONNECTION_TIMEOUT_S, TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIMEOUT_S, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/kgluszczyk/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    DestinationsService provideDestinationsService(Retrofit retrofit) {
        return retrofit.create(DestinationsService.class);
    }
}
