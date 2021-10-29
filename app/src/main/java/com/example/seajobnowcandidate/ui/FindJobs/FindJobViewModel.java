package com.example.seajobnowcandidate.ui.FindJobs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindJobViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FindJobViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Find Job");
    }

    public LiveData<String> getText() {
        return mText;
    }
}