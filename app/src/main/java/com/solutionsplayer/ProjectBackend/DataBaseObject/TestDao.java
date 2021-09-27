package com.solutionsplayer.ProjectBackend.DataBaseObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.solutionsplayer.AirLineCard.AirlineModel;
import com.solutionsplayer.ProjectBackend.TestRateModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TestDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(ArrayList<TestRateModel> listofTest);

        @Query("Select * from testRate")
        LiveData<List<TestRateModel>> GetAllTest();

        @Query("DELETE FROM testRate")
        void DeleteTest();



}
