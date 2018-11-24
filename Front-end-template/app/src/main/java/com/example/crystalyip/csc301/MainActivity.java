package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    public static String userId;
    public static FirebaseDatabase mDatabase;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up the toolbar with the navigation button - i.e. the icon with 3 vertical bars
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // drawer layout contains the menu items including search, popular near you, active orders
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // sets the drawer menu items
        navigationView.setNavigationItemSelectedListener(this);


        OneSignal.startInit(this).init();

        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            //mDatabase.setPersistenceEnabled(true);
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();
        userId="12";
        OneSignal.sendTag("User_ID", "11");

        /*****************************************************************************************
         * TODO: YOU NEED TO ADD CODE WHEN BACKEND IMPLEMENTED THAT COUNTS NUMBER OF ACTIVE ORDERS.
         * DEPENDING ON NUMBER OF ACTIVE ORDERS, NAVIGATION MENU MAY SHOW MORE THAN ONE ACTIVE
         * ORDER.
         *****************************************************************************************/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // this will call a problem with rotating
        // when you rotate, destroy activity and create a new one
        // need this if statement
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SearchFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_search);
        }


    }

    /**
     * This function modifies the "back press" to close the navigation drawer menu when the drawer
     * is open. Otherwise, if the drawer is already closed, use the default "back press"
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                System.out.println("henlobirb");
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * Invoked when setNavigationItemSelectedListener is called above. Routes each drawer menu
     * item to its respective fragments.
     * @param menuItem - the drawer menu
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.nav_popular_menus:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //new PopMenusFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ShowListings()).commit();
                break;
            case R.id.nav_active:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ActiveFragment()).commit();
                break;
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddListing()).commit();
                break;
            case R.id.personal_listing:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CookListingTracker()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
