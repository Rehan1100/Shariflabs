package com.solutionsplayer.BookingAppointment;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookingViewModel extends ViewModel {

    private MutableLiveData<String> name,email,cellNo,message,date,title;

    public BookingViewModel(Context context) {
        title= new MutableLiveData<>();
        title.setValue("Booking");

    }

    public LiveData<String> getText() {
        return title;
    }

}