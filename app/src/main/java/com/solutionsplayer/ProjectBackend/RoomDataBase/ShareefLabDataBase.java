package com.solutionsplayer.ProjectBackend.RoomDataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.solutionsplayer.ProjectBackend.DataBaseObject.TestDao;
import com.solutionsplayer.ProjectBackend.TestRateModel;

@Database(entities = {TestRateModel.class}, version = 2)
public abstract class ShareefLabDataBase extends RoomDatabase {

    private static final String DatabaseName = "ShareefLabDataBase";

    public abstract TestDao dao();

    private static volatile ShareefLabDataBase INSTANCE;

    public static ShareefLabDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (ShareefLabDataBase.class) {
                INSTANCE = Room.databaseBuilder(context, ShareefLabDataBase.class
                        , DatabaseName)
                        .addCallback(callback)
                        .build();
            }
        }

        return INSTANCE;
    }

    static Callback callback= new Callback() {
        @Override
        public void onCreate(@NonNull  SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsync(INSTANCE);

        }
    };

    static class PopulateAsync extends AsyncTask<Void,Void,Void>
    {
        private TestDao dao;

        public PopulateAsync(ShareefLabDataBase dataBase) {
            this.dao =  dataBase.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.DeleteTest();
            return null;
        }
    }

}
