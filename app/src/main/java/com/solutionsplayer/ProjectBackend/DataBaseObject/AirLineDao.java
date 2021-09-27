package com.solutionsplayer.ProjectBackend.DataBaseObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.solutionsplayer.AirLineCard.AirlineModel;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface AirLineDao {

    //That is for Airline
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<AirlineModel> ListOfAirLine);

    @Query("Select * from AirlineModel")
    LiveData<List<AirlineModel>> GetAllAirLine();

    @Query("DELETE FROM AirlineModel")
    void Delete();
}
