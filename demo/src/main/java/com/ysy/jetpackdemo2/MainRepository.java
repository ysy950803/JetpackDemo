package com.ysy.jetpackdemo2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private static final MainRepository INSTANCE = new MainRepository();
    private final MutableLiveData<Card> card = new MutableLiveData<>();
    private WebService webService;

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        return INSTANCE;
    }

    public LiveData<Card> getCard() {
        webService.getCard().enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                card.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
            }
        });
        return card;
    }
}
