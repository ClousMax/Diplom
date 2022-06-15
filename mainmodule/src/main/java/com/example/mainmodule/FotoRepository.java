package com.example.mainmodule;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FotoRepository {
    private FotoDao mFotoDao;
    private LiveData<List<FotoData>> mAllFoto;


    FotoRepository(Application application) {
        fotoRoomDatabase db = fotoRoomDatabase.getDatabase(application);
        mFotoDao = db.fotoDao();
        mAllFoto = mFotoDao.getAllData();
    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    LiveData<List<FotoData>> getAllFoto() {
        return mAllFoto;
    }

    void insert(FotoData foto) {
        fotoRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFotoDao.insert(foto);
        });
    }

    void deleteItem(FotoData foto) {
        fotoRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFotoDao.deleteItem(foto);
        });

    }

    void deleteAll() {
        fotoRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFotoDao.deleteAll();
        });
    }
}
