package com.kgluszczyk.destinations.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.kgluszczyk.destinations.di.annotation.qualifiers.ApplicationContext;
import com.kgluszczyk.destinations.data.AppDatabase;
import com.kgluszczyk.destinations.data.DestinationDao;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class StorageModule {

    @Singleton
    @Provides
    AppDatabase provideHouseDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "destinations").build();
    }

    @Singleton
    @Provides
    DestinationDao provideDestinationDao(AppDatabase appDatabase) {
        return appDatabase.destinationDao();
    }
}
