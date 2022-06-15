package com.example.mainmodule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FotoViewModel extends AndroidViewModel {

    private FotoRepository mRepository;
    private final LiveData<List<FotoData>> mAllFoto;

    public FotoViewModel (Application application) {
        super(application);
        mRepository = new FotoRepository(application);
        mAllFoto = mRepository.getAllFoto();
    }
    LiveData<List<FotoData>> getAllFoto() { return mAllFoto; }

    public void insert(FotoData foto) { mRepository.insert(foto); }
    public void deleteItem(FotoData foto) { mRepository.deleteItem(foto); }
    public void deleteAll(){mRepository.deleteAll();}

}
