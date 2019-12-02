package com.example.morse_lock.ui.LockSet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MorseSetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MorseSetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Morse set fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}