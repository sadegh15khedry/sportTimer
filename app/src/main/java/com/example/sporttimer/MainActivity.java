package com.example.sporttimer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


 //                Toast.makeText(getContext(),"working",
//                Toast.LENGTH_SHORT).show();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new QuickFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_quick);
    }





    //bottom nav item select
    final private BottomNavigationView.OnItemSelectedListener bottomNavigationItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_moves) {
                selectedFragment = new MovesFragment();

            } else if (item.getItemId() == R.id.nav_routine) {
                selectedFragment = new RoutinesFragment();
            }
//            }
            else if (item.getItemId() == R.id.nav_quick) {
                selectedFragment = new QuickFragment();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }


    };
}