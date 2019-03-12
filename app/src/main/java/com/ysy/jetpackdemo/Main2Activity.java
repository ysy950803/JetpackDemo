package com.ysy.jetpackdemo;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ysy.jetpackdemo.ui.main2.BlankFragment;
import com.ysy.jetpackdemo.ui.main2.BlankFragment2;
import com.ysy.jetpackdemo.ui.main2.ItemListDialogFragment;

public class Main2Activity extends AppCompatActivity implements ItemListDialogFragment.Listener,
        BlankFragment2.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private View mLayout1, mLayout2, mLayout3;
    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment3;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
//                    mLayout1.setVisibility(View.VISIBLE);
//                    mLayout2.setVisibility(View.GONE);
//                    mLayout3.setVisibility(View.GONE);
                    replaceFragment(R.id.fragment1, mFragment1);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
//                    mLayout1.setVisibility(View.GONE);
//                    mLayout2.setVisibility(View.VISIBLE);
//                    mLayout3.setVisibility(View.GONE);
                    replaceFragment(R.id.fragment1, mFragment2);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
//                    mLayout1.setVisibility(View.GONE);
//                    mLayout2.setVisibility(View.GONE);
//                    mLayout3.setVisibility(View.VISIBLE);
                    replaceFragment(R.id.fragment1, mFragment3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mTextMessage = findViewById(R.id.message);
        mLayout1 = findViewById(R.id.fragment1);
        mLayout2 = findViewById(R.id.fragment2);
        mLayout3 = findViewById(R.id.fragment3);
        mFragment1 = BlankFragment.newInstance();
        mFragment2 = ItemListDialogFragment.newInstance(3);
        mFragment3 = BlankFragment2.newInstance("p1", "p2");

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        replaceFragment(R.id.fragment1, mFragment1);
//        replaceFragment(R.id.fragment2, mFragment2);
//        replaceFragment(R.id.fragment3, mFragment3);
    }

    private void replaceFragment(int resId, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(resId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
