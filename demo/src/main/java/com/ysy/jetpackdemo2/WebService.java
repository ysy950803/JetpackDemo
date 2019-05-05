package com.ysy.jetpackdemo2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("/card")
    Call<Card> getCard();
}
