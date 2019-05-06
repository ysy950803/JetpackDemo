package com.ysy.jetpackdemo2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ysy.jetpackdemo2.data.entity.Card;
import com.ysy.jetpackdemo2.data.entity.RepoState;
import com.ysy.jetpackdemo2.data.entity.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private TextView mTestTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initViewModel();
    }

    private void initView() {
        mTestTxtView = findViewById(R.id.text_txt);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.getRepoState().observe(this, new Observer<RepoState>() {
            @Override
            public void onChanged(RepoState repoState) {
                // update tips UI
                Log.d("TEST-1", repoState.getState() + "");
            }
        });
        mViewModel.getCard().observe(this, new Observer<Card>() {
            @Override
            public void onChanged(Card card) {
                // update UI
                mTestTxtView.setText(card.getTitle());
            }
        });
        mViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                // update UI
            }
        });
    }

    private void refreshAll() {
        mViewModel.getUser();
        mViewModel.getCard();
    }
}
