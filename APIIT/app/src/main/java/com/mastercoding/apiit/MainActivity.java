package com.mastercoding.apiit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mastercoding.apiit.ebook.EbookActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.frame_layout);

        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        

        toggle = new ActionBarDrawerToggle(this ,drawerLayout,R.string.start,R.string.close);



       drawerLayout.addDrawerListener(toggle);
       toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.navigation_developers:
                Toast.makeText(this,"Aditi Vatsa & Abhishek Pandey",Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_share:
                Intent s = new Intent(Intent.ACTION_SEND);
                s.setType("https://www.apiit.edu.in/");
                startActivity(Intent.createChooser(s,"Choose a platform"));
                Toast.makeText(this,"Share",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_website:
                Intent w = new Intent(Intent.ACTION_VIEW);
                w.setData(Uri.parse("https://www.apiit.edu.in/"));
                startActivity(w);

                Toast.makeText(this,"Welcome to APIIT",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_ebook:
                Intent E = new Intent(this,EbookActivity.class);
                startActivity(E);


                break;
            case R.id.navigation_rateUs:
                Toast.makeText(this,"Rate Us",Toast.LENGTH_SHORT).show();
                break;



        }

        return true;

    }
}