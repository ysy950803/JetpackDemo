package com.ysy.jetpackdemo.workmanager;

import android.content.Context;

import com.ysy.jetpackdemo.room.AppDatabase;
import com.ysy.jetpackdemo.room.User;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UserWorker extends Worker {

    public static final String DATA_KEY = "uid";

    public UserWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        User user = new User();
        user.uid = getInputData().getInt(DATA_KEY, 0);
        user.firstName = "Update" + " " + System.currentTimeMillis();
        if (db.userDao().update(user) == 1) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }
}
