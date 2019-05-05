package com.ysy.jetpackdemo2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private LiveData<User> user;
    private LiveData<Card> card;

    public void loadData() {
        loadUser();
        loadCard();
    }

    public LiveData<User> getUser() {
        loadUser();
        return user;
    }

    public LiveData<Card> getCard() {
        loadCard();
        return card;
    }

    private void loadUser() {
    }

    private void loadCard() {
        card = MainRepository.getInstance().getCard();
    }
}
