package com.solutionsplayer.ProjectBackend.Repositry;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.solutionsplayer.AirLineCard.AirlineModel;
import com.solutionsplayer.ProjectBackend.DataBaseObject.AirLineDao;
import com.solutionsplayer.ProjectBackend.DataBaseObject.TestDao;
import com.solutionsplayer.ProjectBackend.RoomDataBase.ShareefLabDataBase;
import com.solutionsplayer.ProjectBackend.TestRateModel;

import java.util.ArrayList;
import java.util.List;

public class ShareefLabRepositry {

    private ShareefLabDataBase dataBase;
   private LiveData<List<TestRateModel>> getAlltest;
   private LiveData<List<AirlineModel>> getAllAirline;

    public ShareefLabRepositry(Application application) {
        dataBase = ShareefLabDataBase.getINSTANCE(application);
        getAlltest = dataBase.dao().GetAllTest();
        getAllAirline = dataBase.dao2().GetAllAirLine();
    }

    public void insert(List<TestRateModel> list) {
        new InsertAsynTask(dataBase).execute(list);
    }

    public void insertAllAirline(List<AirlineModel> list) {
        new InsertAsynTask2(dataBase).execute(list);
    }

    public LiveData<List<TestRateModel>> getGetAlltest() {
        return getAlltest;
    }

    public LiveData<List<AirlineModel>> getGetAllAirline() {
        return getAllAirline;
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
    static class InsertAsynTask2 extends AsyncTask<List, Void, Void> {
        public AirLineDao dao2;

        public InsertAsynTask2(ShareefLabDataBase dao) {
            this.dao2 = dao.dao2();
        }

        @Override
        protected Void doInBackground(List... lists) {
            
            dao2.Delete();
            dao2.insert((ArrayList<AirlineModel>) lists[0]);
            return null;
        }
    }

}
