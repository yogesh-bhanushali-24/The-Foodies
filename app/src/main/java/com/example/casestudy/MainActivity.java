package com.example.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name, email;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        toolbar = findViewById(R.id.toolbar);
//        navigationView = findViewById(R.id.navigationView);
//        drawerLayout = findViewById(R.id.drawer);


        //this code for load fragment onCreate
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new homeFragment()).commit();
        //end load fragment onCreate code


        //  bottom navigation bar code
        bottomNavigationView = findViewById(R.id.bottomnavigationmenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        replace(new homeFragment());
                        break;
                    case R.id.cart:
                        replace(new cartFragment());
                        break;

//                    case R.id.myOrder:
//
//                        break;
                    case R.id.search:
                        replace(new searchFragment());
                        break;
//
                    case R.id.user_profile:
                        replace(new UserProfileFragment());
                        break;
                }

                return true;
            }
        });
        //end bottom navigation bar


        //Navigation bar code

        getSupportActionBar().hide();
        //  setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("App");

//        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//                switch (item.getItemId()){
////                    case R.id.login:
////                        Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
////                        break;
//                    case R.id.my_order:
//                        Toast.makeText(MainActivity.this, "My order", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.cart:
//                        Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
//                        break;
////                    case R.id.wallet:
////                        Toast.makeText(MainActivity.this, "Wallet", Toast.LENGTH_SHORT).show();
////                        break;
////                    case R.id.history:
////                        Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
////                        break;
//                    case R.id.subscription:
//                        Toast.makeText(MainActivity.this, "Subscription", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.share:
//                        Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });

//        View hView =  navigationView.getHeaderView(0);
//        name = hView.findViewById(R.id.name);
//        email = hView.findViewById(R.id.email);
//        name.setText("Yogesh Bhanushali");
//        email.setText("bhanushali@gmail.com");

    }
    //end Navigation drawer


    //fragment replace
    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
    //end fragment replace


//    @Override
//    public void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else {
//            super.onBackPressed();
//        }
//    }
}