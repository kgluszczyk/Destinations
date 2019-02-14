package com.kgluszczyk.destinations.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Destination.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DestinationDao destinationDao();
}