package com.maverick.medklick.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maverick.medklick.R;
import com.maverick.medklick.fragments.AboutFragment;
import com.maverick.medklick.fragments.CheckReportsFragment;
import com.maverick.medklick.fragments.CheckStatusFragment;
import com.maverick.medklick.fragments.HomeFragment;
import com.maverick.medklick.fragments.SettingsFragment;
import com.maverick.medklick.others.UserInformation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ImageView imgProfile;
    private TextView nameTextView, emailIdTextView;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_CHECK_REPORTS = "check reports";
    private static final String TAG_CHECK_STATUS = "check status";
    private static final String TAG_ABOUT = "about";
    private static final String TAG_SETTINGS = "settings";

    // index to identify current nav menu item
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        // If the user is not logged in then start LoginActivity
        if (auth.getCurrentUser() == null) {
            // close this activity
            finish();
            // start login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        databaseReference = firebaseDatabase.getReference("users");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setElevation(0);

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);

        // Set the opening and closing feature of the Navigation Drawer along with the hamburger icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //Setting the actionbarToggle to drawer layout
        drawer.addDrawerListener(toggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        // Navigation view header
        View navHeader = navigationView.getHeaderView(0);
        nameTextView = navHeader.findViewById(R.id.name);
        emailIdTextView = navHeader.findViewById(R.id.user_id);
        imgProfile = navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadMainFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like name, website
     */
    private void loadNavHeader() {

        if (user != null) {
            databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);

                    // Check for null
                    if (userInformation == null) {
                        Log.e(TAG, "User data is null!");
                    } else {
                        // Display user information
                        nameTextView.setText(userInformation.name);
                        emailIdTextView.setText(userInformation.email);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Failed to read value
                    Log.e(TAG, "Failed to read user", databaseError.toException());
                }
            });
        }

        imgProfile.setImageResource(R.mipmap.ic_launcher);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    @SuppressLint("RestrictedApi")
    private void loadMainFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getMainFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.content_main, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getMainFragment() {
        switch (navItemIndex) {
            case 0:
                // home fragment
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // check reports fragment
                CheckReportsFragment checkReportsFragment = new CheckReportsFragment();
                return checkReportsFragment;
            case 2:
                // check status fragment
                CheckStatusFragment checkStatusFragment = new CheckStatusFragment();
                return checkStatusFragment;
            case 3:
                // about fragment
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;
            case 4:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_check_reports:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CHECK_REPORTS;
                        break;
                    case R.id.nav_check_status:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CHECK_STATUS;
                        break;
                    case R.id.nav_about:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_ABOUT;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    default:
                        navItemIndex = 0;
                        break;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadMainFragment();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadMainFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
