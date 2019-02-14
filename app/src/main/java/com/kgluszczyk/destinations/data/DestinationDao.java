package com.kgluszczyk.destinations.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public abstract class DestinationDao {

    @Query("SELECT * FROM destination")
    public abstract Flowable<List<Destination>> getAllRx();

    @Query("SELECT * FROM destination")
    public abstract List<Destination> getAll();

    @Transaction
    public boolean replaceAll(List<Destination> destinations) {
        if (!getAll().equals(destinations)) {
            deleteAll();
            insertAll(destinations);
            return true;
        }
        return false;
    }

    @Query("DELETE FROM destination")
    abstract void deleteAll();

    @Insert
    abstract void insertAll(List<Destination> destinations);
}
