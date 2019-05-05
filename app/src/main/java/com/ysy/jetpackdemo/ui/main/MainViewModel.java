package com.ysy.jetpackdemo.ui.main;

import android.util.Log;

import com.ysy.jetpackdemo.workmanager.UserWorker;

import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<String> message; // merge msg1 and msg2
    private MutableLiveData<String> msg1;
    private MutableLiveData<Integer> msg2;

    private WorkManager mWorkManager;

    public MainViewModel() {
        mWorkManager = WorkManager.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("TEST-1", "MainViewModel onCleared");
    }

    public LiveData<String> getMessage() {
        if (message == null) {
            message = new MediatorLiveData<>();
            message.addSource(getMsg1(), s -> message.setValue(s));
            message.addSource(getMsg2(), i -> message.setValue(i));
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
            msg2.setValue(0);
        }
        return Transformations.switchMap(msg2, this::switchMsg2);
    }

    private LiveData<String> switchMsg2(int input) {
        MutableLiveData<String> result = new MutableLiveData<>();
        result.setValue(String.valueOf(input) + "s later msg2");
        return result;
    }

    public void refreshMessage() {
        new Thread(() -> {
            try {
                for (int i = 0; i < 8; i++) {
                    Thread.sleep(1000);
                    if ((i + 1) % 2 != 0) {
                        msg1.postValue((i + 1) + "s later");
                    } else {
                        msg2.postValue(i + 1);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        scheduleWork();
    }

    private void scheduleWork() {
        Data data = new Data.Builder()
                .putInt(UserWorker.DATA_KEY, 1)
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UserWorker.class)
                .setInputData(data)
                .setInitialDelay(1000 * 8, TimeUnit.MILLISECONDS)
                .build();
        mWorkManager.enqueue(request);
    }
}