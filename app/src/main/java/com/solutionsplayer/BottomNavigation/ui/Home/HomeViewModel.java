package com.solutionsplayer.BottomNavigation.ui.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<ArrayList<String>> data;
      public   ArrayList<String> data2;
    public HomeViewModel() {

        data=new MutableLiveData<>();
        data2=new ArrayList<>();
        data2.add("About Us");
        data2.add("Our Services");
        data2.add("Booking");
        data2.add("Test Rate");
        data2.add("Lab Report");
        data2.add("Contact Us");

        data.setValue(data2);


    }

    public MutableLiveData<ArrayList<String>> getTextAboutUs() {

        return data;
    }

}