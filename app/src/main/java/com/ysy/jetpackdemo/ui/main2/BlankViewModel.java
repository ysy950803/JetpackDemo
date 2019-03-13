package com.ysy.jetpackdemo.ui.main2;

import android.app.Application;

import com.ysy.jetpackdemo.room.AppDatabase;
import com.ysy.jetpackdemo.room.User;
import com.ysy.jetpackdemo.room.UserDao;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BlankViewModel extends AndroidViewModel {

    private final MutableLiveData<String> linkData = new MutableLiveData<>();
    private UserDao mUserDao;

    public BlankViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public MutableLiveData<String> getLinkData() {
        return linkData;
    }

    public void sendLinkData(String data) {
        linkData.setValue(data);
    }

    public LiveData<List<User>> getAllUsers() {
        return mUserDao.getAll();
    }

    public void insertUser(String name) {
        new Thread(() -> {
            User user = new User();
            user.firstName = name + " " + System.currentTimeMillis();
            user.lastName = " ";
            mUserDao.insert(user);
        }).start();
    }
}
