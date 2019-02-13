package com.kgluszczyk;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.kgluszczyk.myapplication.AppDatabase;
import com.kgluszczyk.myapplication.ApplicationContext;
import com.kgluszczyk.myapplication.DestinationDao;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class StorageModule {

    @Singleton
    @Provides
    AppDatabase provideHouseDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "database-name").build();
    }

    @Singleton
    @Provides
    DestinationDao provideDestinationDao(AppDatabase appDatabase) {
        return appDatabase.destinationDao();
    }
}
