package com.solutionsplayer.ProjectBackend.Repositry;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.solutionsplayer.ProjectBackend.DataBaseObject.TestDao;
import com.solutionsplayer.ProjectBackend.RoomDataBase.ShareefLabDataBase;
import com.solutionsplayer.ProjectBackend.TestRateModel;

import java.util.ArrayList;
import java.util.List;

public class ShareefLabRepositry {

    private ShareefLabDataBase dataBase;
   private LiveData<List<TestRateModel>> getAlltest;

    public ShareefLabRepositry(Application application) {
        dataBase = ShareefLabDataBase.getINSTANCE(application);
        getAlltest = dataBase.dao().GetAllTest();
    }

    public void insert(List<TestRateModel> list) {
        new InsertAsynTask(dataBase).execute(list);
    }

    public LiveData<List<TestRateModel>> getGetAlltest() {
        return getAlltest;
    }

    static class InsertAsynTask extends AsyncTask<List, Void, Void> {
        public TestDao dao;

        public InsertAsynTask(ShareefLabDataBase dao) {
            this.dao = dao.dao();

        }

        @Override
        protected Void doInBackground(List... lists) {
            dao.DeleteTest();
            dao.insert((ArrayList<TestRateModel>) lists[0]);
            return null;
        }
    }

}
