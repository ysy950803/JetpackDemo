package com.ysy.jetpackdemo2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static OkHttpClient sOkHttpClient;
    private static Retrofit sRetrofit;

    public static WebService getService() {
        // 构造request
        return getRetrofit().create(WebService.class);
    }

    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (ApiClient.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl("http://xxx.xxx")
                            .client(getClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    private static OkHttpClient getClient() {
        if (sOkHttpClient == null) {
            synchronized (ApiClient.class) {
                if (sOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(20, TimeUnit.SECONDS);
                    sOkHttpClient = builder.build();
                }
            }
        }
        return sOkHttpClient;
    }
}
