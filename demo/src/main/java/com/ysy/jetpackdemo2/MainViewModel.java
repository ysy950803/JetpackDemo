package com.ysy.jetpackdemo2;

import com.ysy.jetpackdemo2.data.entity.Card;
import com.ysy.jetpackdemo2.data.MainRepository;
import com.ysy.jetpackdemo2.data.entity.User;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public LiveData<User> getUser() {
        // 可用Repository给出的源数据做一些转换操作
        return Transformations.switchMap(MainRepository.getInstance().getUser(), new Function<User, LiveData<User>>() {
            @Override
            public LiveData<User> apply(User input) {
                input.setName(input.getName() + "_modified");
                return new MutableLiveData<>(input);
            }
        });
    }

    public LiveData<Card> getCard() {
        return MainRepository.getInstance().getCard();
    }
}
