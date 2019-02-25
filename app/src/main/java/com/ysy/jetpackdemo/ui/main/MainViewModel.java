package com.ysy.jetpackdemo.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<String> message; // merge msg1 and msg2
    private MutableLiveData<String> msg1, msg2;

    public LiveData<String> getMessage() {
        if (message == null) {
            message = new MediatorLiveData<>();
            message.addSource(getMsg1(), s -> message.setValue(s));
            message.addSource(getMsg2(), s -> message.setValue(s));
        }
        return message;
    }

    private LiveData<String> getMsg1() {
        if (msg1 == null) {
            msg1 = new MutableLiveData<>();
            msg1.setValue("Now");
        }
        return Transformations.map(msg1, input -> input + " msg1");
    }

    private LiveData<String> getMsg2() {
        if (msg2 == null) {
            msg2 = new MutableLiveData<>();
            msg2.setValue("Now");
        }
        return Transformations.map(msg2, input -> input + " msg2");
    }

    public void refreshMessage() {
        new Thread(() -> {
            try {
                for (int i = 0; i < 8; i++) {
                    Thread.sleep(1000);
                    if ((i + 1) % 2 != 0) {
                        msg1.postValue((i + 1) + "s later");
                    } else {
                        msg2.postValue((i + 1) + "s later");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}