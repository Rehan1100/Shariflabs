package com.solutionsplayer.BottomNavigation.ui.ContactsUs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContactUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Contact Us");
    }

    public LiveData<String> getText() {
        return mText;
    }
}