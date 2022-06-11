package com.example.mainmodule;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FotoRepository {
    private FotoDao mFotoDao;
    private LiveData<List<FotoData>> mAllFoto;
    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples

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
}
