package com.kgluszczyk.destinations.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Destination.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DestinationDao destinationDao();
}