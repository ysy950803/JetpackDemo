package com.ysy.jetpackdemo2.data;

import android.util.Log;

import com.ysy.jetpackdemo2.data.entity.User;
import com.ysy.jetpackdemo2.net.ApiClient;
import com.ysy.jetpackdemo2.data.entity.Card;
import com.ysy.jetpackdemo2.data.entity.RepoState;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private static final MainRepository INSTANCE = new MainRepository();
    private static RepoState sRepoState = new RepoState();

    // 可为数据获取状态单独设置LiveData，对外暴露“加载中”、“加载失败”等状态
    private final MutableLiveData<RepoState> repoState = new MutableLiveData<>();
    private final MutableLiveData<Card> card = new MutableLiveData<>();
    private final MutableLiveData<User> user = new MutableLiveData<>();

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        return INSTANCE;
    }

    private void updateState(int stateValue) {
        sRepoState.setState(stateValue);
        repoState.setValue(sRepoState);
    }

    public LiveData<Card> getCard() {
        updateState(RepoState.LOADING);
        // 本地数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO 模拟异步加载数据库或磁盘缓存
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (repoState.getValue() != null && !repoState.getValue().isFinished()) {
                    Card localData = new Card();
                    localData.setTitle("test");
                    card.postValue(localData);
                }
            }
        }).start();

        // 网络数据
        ApiClient.getService().getCard().enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                updateState(RepoState.SUCCESS);
                card.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                updateState(RepoState.FAIL);
                Log.d("TEST-1", t.toString());
            }
        });

        return card;
    }

    public LiveData<User> getUser() {
        return user;
    }
}
