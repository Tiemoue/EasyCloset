package com.example.easycloset.Activities;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.easycloset.Constant;
import com.example.easycloset.Fragments.ClosetFragment;
import com.example.easycloset.Fragments.ComposeFragment;
import com.example.easycloset.Fragments.FeedFragment;
import com.example.easycloset.Fragments.HomeFragment;
import com.example.easycloset.Fragments.ProfileFragment;
import com.example.easycloset.Fragments.SearchFragment;
import com.example.easycloset.Fragments.SuggestFragment;
import com.example.easycloset.Fragments.UploadFragment;
import com.example.easycloset.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentInterface, UploadFragment.UploadFragmentInterface, ClosetFragment.ClosetFragmentInterface {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final HomeFragment homeFragment = new HomeFragment(this);
    private final ClosetFragment closetFragment = new ClosetFragment(this);
    private final ProfileFragment profileFragment = new ProfileFragment(this);
    private final SuggestFragment suggestFragment = new SuggestFragment(this);
    private final UploadFragment uploadFragment = new UploadFragment(this);
    private final FeedFragment feedFragment = new FeedFragment(this);
    private final ComposeFragment composeFragment = new ComposeFragment(this);
    private final SearchFragment searchFragment = new SearchFragment(this);
    private CallbackManager callbackManager;
    private final Constant constant = new Constant();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLocationUpdates();
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        callbackManager = CallbackManager.Factory.create();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    Fragment fragment;
                    switch (item.getItemId()) {
                        case R.id.action_main:
                            fragment = homeFragment;
                            break;
                        case R.id.action_closet:
                            fragment = closetFragment;
                            break;
                        case R.id.action_suggest:
                            fragment = suggestFragment;
                            break;
                        case R.id.action_feed:
                            fragment = feedFragment;
                            break;
                        default:
                            fragment = profileFragment;
                            break;
                    }
                    setFragmentContainer(fragment);
                    return true;
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void switchToClosetFragment() {
        setFragmentContainer(getClosetFragment());
    }

    @Override
    public void switchToUploadFragment() {
        setFragmentContainer(getUploadFragment());
    }

    public ClosetFragment getClosetFragment() {
        return closetFragment;
    }

    public UploadFragment getUploadFragment() {
        return uploadFragment;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public SuggestFragment getSuggestFragment() {
        return suggestFragment;
    }

    public FeedFragment getFeedFragment() {
        return feedFragment;
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }

    public void goToSearchFragment(String place) {
        getSearchFragment().setLocation(place);
        setFragmentContainer(getSearchFragment());
    }

    public ComposeFragment getComposeFragment() {
        return composeFragment;
    }

    public void setFragmentContainer(Fragment fragment) {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.slide_out).replace(R.id.flContainer, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miLogout) {
            logOutUser();
        }
        return true;
    }

    private void logOutUser() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {
        // Create the location request to start receiving updates
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        /* 10 secs */
        long UPDATE_INTERVAL = 10 * 2000;
        locationRequest.setInterval(UPDATE_INTERVAL);
        /* 5 sec */
        long FASTEST_INTERVAL = 5000;
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        onLocationChanged(Objects.requireNonNull(locationResult.getLastLocation()));
                    }
                },
                Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == constant.getREQUEST_LOCATION_PERMISSION()) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double log = location.getLongitude();
        homeFragment.setLatitude(lat);
        homeFragment.setLongitude(log);
        homeFragment.fetchWeatherData(lat, log);
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                constant.getREQUEST_LOCATION_PERMISSION());
    }

    public void closeApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(R.string.errorMsg)
                .setCancelable(false)
                .setNeutralButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == constant.getDELETE_ITEM_REQUEST_CODE() && resultCode == RESULT_OK) {
            assert getFragmentManager() != null;
            setFragmentContainer(closetFragment);
            closetFragment.queryPosts();
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private final FacebookCallback<Sharer.Result> callback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Log.v("here", "Successfully posted");
            // Write some code to do some operations when you shared content successfully.
        }

        @Override
        public void onCancel() {
            Log.v("here", "Sharing cancelled");
            // Write some code to do some operations when you cancel sharing content.
        }

        @Override
        public void onError(FacebookException error) {
            Log.v("here", error.getMessage());
            // Write some code to do some operations when some error occurs while sharing content.
        }
    };
}