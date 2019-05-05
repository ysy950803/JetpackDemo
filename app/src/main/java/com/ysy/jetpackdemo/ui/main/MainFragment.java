package com.ysy.jetpackdemo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ysy.jetpackdemo.R;
import com.ysy.jetpackdemo.HiddenAPITest;

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
//            if (mMsgTextView.getText().toString().contains("8s")) {
//                startActivity(new Intent(getContext(), Main2Activity.class));
//                startActivity(Navigator.intentMain2Activity(getContext()));
            Toast.makeText(getContext(), "" + HiddenAPITest.checkPermission(getContext(), "OP_POST_NOTIFICATION"),
                    Toast.LENGTH_SHORT).show();
//            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TEST-1", "MainFragment onDestroy");
    }
}
