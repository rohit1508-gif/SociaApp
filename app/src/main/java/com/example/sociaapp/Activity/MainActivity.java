package com.example.sociaapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sociaapp.Fragment.HomeFragment;
import com.example.sociaapp.R;
import com.example.sociaapp.Fragment.RandomFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    NavigationView navigationView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                new HomeFragment()).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_random:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                        new RandomFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);}
        else{
            HomeFragment myFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT");
            if (myFragment != null && myFragment.isVisible()) {
                finishAffinity();
            }
            else
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                        new HomeFragment(),"HOME_FRAGMENT").commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
        }
    }
    @Override
    protected void onResume() {
        navigationView.setCheckedItem(R.id.nav_home);

        super.onResume();
    }
}