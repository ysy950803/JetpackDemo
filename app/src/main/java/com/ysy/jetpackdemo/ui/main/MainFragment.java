package com.ysy.jetpackdemo.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysy.jetpackdemo.Main2Activity;
import com.ysy.jetpackdemo.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView mMsgTextView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View view) {
        mMsgTextView = view.findViewById(R.id.message);
        mMsgTextView.setOnClickListener(v -> startActivity(new Intent(getContext(), Main2Activity.class)));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getMessage().observe(this, msg -> {
            mMsgTextView.setText(msg);
        });
        mViewModel.refreshMessage();
    }
}
