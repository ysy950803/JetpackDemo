package com.ysy.jetpackdemo.ui.main2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ysy.jetpackdemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class BlankFragment extends Fragment {

    private BlankViewModel mViewModel;
    private EditText mEdt1;
    private Button mBtn1;

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blank_fragment, container, false);
        mEdt1 = view.findViewById(R.id.edt1);
        mBtn1 = view.findViewById(R.id.btn1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(BlankViewModel.class);
        mBtn1.setOnClickListener(v -> mViewModel.sendLinkData(mEdt1.getText().toString()));
    }
}
