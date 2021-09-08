package com.solutionsplayer.BottomNavigation.ui.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServicesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ServicesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Our Services");
    }

    public LiveData<String> getText() {
        return mText;
    }
}