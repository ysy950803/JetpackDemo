package com.ysy.jetpackdemo2;

import com.ysy.jetpackdemo2.data.entity.Card;
import com.ysy.jetpackdemo2.data.MainRepository;
import com.ysy.jetpackdemo2.data.entity.RepoState;
import com.ysy.jetpackdemo2.data.entity.User;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MainRepository repository;
    private LiveData<User> user;

    public MainViewModel() {
        repository = MainRepository.getInstance();
    }

    public LiveData<RepoState> getRepoState() {
        return repository.getRepoState();
    }

    public LiveData<User> getUser() {
        // 可用Repository给出的源数据做一些转换操作
        user = Transformations.switchMap(repository.getUser(), new Function<User, LiveData<User>>() {
            @Override
            public LiveData<User> apply(User input) {
                input.setName(input.getName() + "_modified");
                return new MutableLiveData<>(input);
            }
        });
        return user;
    }

    public void modifyUser(String newName) {
        User u = user.getValue();
        if (u != null) {
            u.setName(newName);
        }
        repository.modifyUser(u);
    }

    public LiveData<Card> getCard() {
        return repository.getCard();
    }
}
