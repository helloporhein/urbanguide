package com.example.urbanguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.urbanguide.Common.LoginSignup.RetailerStartUpScreen;
import com.example.urbanguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.urbanguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.example.urbanguide.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;
    RecyclerView featuredRecycler,featuredMostViewed,featuredCategories;
    RecyclerView.Adapter adapter;
    ImageView startUp_btn,menuIcon;

    GradientDrawable gradient1,gradient2,gradient3;

    LinearLayout contentView;
    //Drawer Layout
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        featuredMostViewed = findViewById(R.id.featured_most_viewed);
        featuredCategories = findViewById(R.id.featured_categories);
        startUp_btn = findViewById(R.id.user_dash_start_up);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();

        animationDrawer();
        callStartupScreen();
        featuredRecycler();
        featuredMostViewed();
        featuredCategories();
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }
    public void animationDrawer(){

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
                super.onDrawerSlide(drawerView, slideOffset);
            }
            @Override
            public void onDrawerClosed(View drawerView){

            }
        });
    }
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_all_categories:
                startActivity(new Intent(getApplicationContext(),AllCategories.class));
                break;
        }
        return true;
    }
    public void callStartupScreen(){
        startUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RetailerStartUpScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonalds_img,"Mcdonald's","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.eldorado_img,"Eldorado's","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.sp_bakery_img,"SP Bakery & Cafe","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }
    private void featuredMostViewed() {

        featuredMostViewed.setHasFixedSize(true);
        featuredMostViewed.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonalds_img,"Mcdonald's","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.eldorado_img,"Eldorado's","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.sp_bakery_img,"SP Bakery & Cafe","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredMostViewed.setAdapter(adapter);

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }
    private void featuredCategories() {

        featuredCategories.setHasFixedSize(true);
        featuredCategories.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonalds_img,"Mcdonald's","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.eldorado_img,"Eldorado's","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.sp_bakery_img,"SP Bakery & Cafe","We provide almost all the numbers of all departments and shops registered with us. You can perform by UrbanGuide"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredCategories.setAdapter(adapter);

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400, 0xffaff600});
    }

}