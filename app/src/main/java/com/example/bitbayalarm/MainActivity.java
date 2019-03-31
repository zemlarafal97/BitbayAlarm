package com.example.bitbayalarm;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {


    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    public static ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),4,this);

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


    }


}
