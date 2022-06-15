package com.example.mainmodule;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FotoDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FotoData fotoData);

    @Delete
    void deleteItem(FotoData fotoData);

    @Query("DELETE FROM fotoData")
    void deleteAll();

    @Query("SELECT * FROM fotoData")
    LiveData<List<FotoData>> getAllData();
}