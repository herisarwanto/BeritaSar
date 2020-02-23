package com.sar;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sar.favorite.FavoriteFragment;
import com.sar.home.HomeFragment;
import com.sar.profile.ProfileFragment;
import com.sar.roomDB.FavoriteDatabase;
import com.sar.tools.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private String nama, nip;
    private BottomNavigationView bottomNavigation;
    public static FavoriteDatabase favoriteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = SharedPrefManager.getInstance(getApplicationContext()).getDataMahasiswa().getName();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("", ""));

        favoriteDatabase= Room.databaseBuilder(getApplicationContext(),FavoriteDatabase.class,"myfavdb").allowMainThreadQueries().build();

        //for get token fcm
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();

                Log.i(this.getClass().getName(), "onCreate newToken => "+newToken);
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_dashboard:
                            openFragment(FavoriteFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(ProfileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(this.getClass().getName(), "onStop START");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e(this.getClass().getName(), "onDestroy CLICKED ");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
