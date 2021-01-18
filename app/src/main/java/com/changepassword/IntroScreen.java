package com.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroScreen extends AppCompatActivity {

    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabLayout;
    Button buttonNext, buttonGet;
    int position = 0;
    TextView skip;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro_screen);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //   getSupportActionBar().hide();
        tabLayout = findViewById(R.id.tabLayout);
        buttonNext = findViewById(R.id.next);
        buttonGet = findViewById(R.id.getStarted);
        buttonGet.setVisibility(View.INVISIBLE);
        skip = findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(IntroScreen.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });


        List<ScreenItem> itemList = new ArrayList<>();


        itemList.add(new ScreenItem("Android", "First IntroScreen", R.drawable.ic_android));
        itemList.add(new ScreenItem("Cup", "Second IntroScreen", R.drawable.ic_cup));
        itemList.add(new ScreenItem("Coffee", "Third IntroScreen", R.drawable.ic_hot_cup));

        introViewPagerAdapter = new IntroViewPagerAdapter(this, itemList);
        viewPager = findViewById(R.id.screen_view_pager);
        viewPager.setAdapter(introViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = viewPager.getCurrentItem();
                if (position < itemList.size()) {
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if (position == itemList.size()) {
                    loadLastScreen();
                }
            }
        });

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(IntroScreen.this, MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == itemList.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadLastScreen() {
        buttonNext.setVisibility(View.INVISIBLE);
        buttonGet.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.INVISIBLE);

    }


}