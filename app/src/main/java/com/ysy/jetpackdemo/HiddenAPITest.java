package com.ysy.jetpackdemo;

import android.app.AppOpsManager;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HiddenAPITest {

    public static int checkPermission(Context context, String permissionName) {
        try {
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            String pkg = context.getApplicationContext().getPackageName();
            int uid = context.getApplicationInfo().uid;
            Class appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(permissionName);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return (int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg);
        } catch (Throwable e) {
            e.printStackTrace();
            return 1;
        }
    }
}
