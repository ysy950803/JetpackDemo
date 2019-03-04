package com.ysy.jetpackdemo.ui.main2;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class BlankViewModel extends ViewModel {

    private final MutableLiveData<String> linkData = new MutableLiveData<>();

    public MutableLiveData<String> getLinkData() {
        return linkData;
    }

    public void sendLinkData(String data) {
        linkData.setValue(data);
    }
}
