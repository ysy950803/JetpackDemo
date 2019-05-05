package com.ysy.jetpackdemo2;

import android.os.Bundle;
import android.widget.TextView;

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
        mViewModel.getCard().observe(this, new Observer<Card>() {
            @Override
            public void onChanged(Card card) {
                // update UI
                mTestTxtView.setText(card.getTitle());
            }
        });
    }

    // 提供任意时机调用
    private void refresh() {
        mViewModel.loadData();
    }
}
