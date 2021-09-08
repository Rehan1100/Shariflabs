package com.solutionsplayer.TestRate;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.solutionsplayer.ProjectBackend.Repositry.ShareefLabRepositry;
import com.solutionsplayer.ProjectBackend.TestRateModel;

import java.util.ArrayList;
import java.util.List;

public class TestViewModel extends AndroidViewModel {
    private ShareefLabRepositry categoryRepositry;
    private LiveData<List<TestRateModel>> getAllCategory;

    public TestViewModel(@NonNull Application application) {
        super(application);
        categoryRepositry= new ShareefLabRepositry(application);
        getAllCategory= categoryRepositry.getGetAlltest();
    }
    public void Insert(ArrayList<TestRateModel> list)
    {
        categoryRepositry.insert(list);
    }
    public LiveData<List<TestRateModel>> getALLCategry()
    {
        return getAllCategory;
    }


}
