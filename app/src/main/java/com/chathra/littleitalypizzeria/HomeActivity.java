package com.chathra.littleitalypizzeria;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chathra.littleitalypizzeria.Helper.AlertBar;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (id) {

            case R.id.nav_food:
                ft.replace(R.id.content_frame, new FoodActivity());
                ft.commit();
                break;

            case R.id.nav_profile:
                ft.replace(R.id.content_frame, new ProfileActivity());
                ft.commit();
                break;

            case R.id.nav_contact:
                ft.replace(R.id.content_frame, new ContactActivity());
                ft.commit();
                break;

            case R.id.nav_logout:
                new KAlertDialog(this, KAlertDialog.CUSTOM_IMAGE_TYPE)
                        .cancelButtonColor(R.color.colorPrimaryDark)
                        .confirmButtonColor(R.color.colorPrimaryDark)
                        .setCancelText("CANCEL")
                        .setContentText("Do you want to logout?")
                        .setConfirmText("YES")
                        .setCustomImage(R.drawable.caution)
                        .showCancelButton(true)
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                Animatoo.animateSplit(HomeActivity.this);
                                kAlertDialog.cancel();
                            }
                        })
                        .show();
                break;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        AlertBar.notifyBackPressed(HomeActivity.this, "Please click BACK again to exit!");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}