package com.example.raymond.a301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    foodNearMeFragment foodNearMeFragment = new foodNearMeFragment();
    profileFragment profileFragment = new profileFragment();
    private FrameLayout fraMain;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_food_nearby:
                    //mTextMessage.setText(R.string.title_food_nearby);
                    return true;
                case R.id.navigation_find:
                    //mTextMessage.setText(R.string.title_find);
                    return true;
                case R.id.navigation_profile:
                    //mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fraMain = (FrameLayout) findViewById(R.id.fraMain);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        set_fragment((Fragment) profileFragment);

    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraMain, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    /* Change the fragment/view/form. by putting the fragment onto the main frame. */
    protected void set_fragment(Fragment fragment) {


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Put the fragment onto the main frame.
        fragmentTransaction.replace(R.id.fraMain,fragment);
        fragmentTransaction.commit();


    }

}
