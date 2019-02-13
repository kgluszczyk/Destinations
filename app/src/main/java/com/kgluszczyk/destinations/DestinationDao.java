package com.kgluszczyk.destinations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface DestinationDao {

    @Query("SELECT * FROM destination")
    Flowable<List<Destination>> getAll();

    @Query("DELETE FROM destination")
    void deleteAll();

    @Insert
    void insertAll(List<Destination> destinations);
}
