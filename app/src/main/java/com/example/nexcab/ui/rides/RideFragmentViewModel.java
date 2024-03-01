package com.example.nexcab.ui.rides;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RideFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RideFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}