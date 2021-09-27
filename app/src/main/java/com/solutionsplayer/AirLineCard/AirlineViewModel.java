package com.solutionsplayer.AirLineCard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.solutionsplayer.ProjectBackend.Repositry.ShareefLabRepositry;
import com.solutionsplayer.ProjectBackend.TestRateModel;

import java.util.ArrayList;
import java.util.List;

public class AirlineViewModel extends AndroidViewModel {
    private ShareefLabRepositry categoryRepositry;
    private LiveData<List<AirlineModel>> getAllAirline;

    public AirlineViewModel(@NonNull Application application) {
        super(application);
        categoryRepositry = new ShareefLabRepositry(application);
        getAllAirline = categoryRepositry.getGetAllAirline();
    }

    public void Insert(ArrayList<AirlineModel> list) {
        categoryRepositry.insertAllAirline(list);
    }

    public LiveData<List<AirlineModel>> getAllAirline() {
        return getAllAirline;
    }
}