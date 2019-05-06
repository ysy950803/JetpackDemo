package com.ysy.jetpackdemo2.net;

import com.ysy.jetpackdemo2.data.entity.Card;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("/card")
    Call<Card> getCard();
}
