package com.ysy.jetpackdemo.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysy.jetpackdemo.Navigator;
import com.ysy.jetpackdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
        mMsgTextView.setOnClickListener(v -> {
            if (mMsgTextView.getText().toString().contains("8s")) {
//                startActivity(new Intent(getContext(), Main2Activity.class));
                Navigator.startMain2Activity(getContext());
            }
        });
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
