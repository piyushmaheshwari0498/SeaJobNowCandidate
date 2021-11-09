package com.example.seajobnowcandidate.ui.FindJobs;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindJobViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();

    public FindJobViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Find Job");
    }

    public void selectItem(String item) {
        selectedItem.setValue(item);
    }
    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }

    public LiveData<String> getText() {
        return mText;
    }
}