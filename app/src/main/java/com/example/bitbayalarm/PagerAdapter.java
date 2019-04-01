package com.example.bitbayalarm;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bitbayalarm.fragments.Tab1ExchangeRate;
import com.example.bitbayalarm.fragments.Tab2Alarms;
import com.example.bitbayalarm.fragments.Tab3ProfitCalculator;
import com.example.bitbayalarm.fragments.Tab4Calculator;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;
    private Context mContext;

    public PagerAdapter(FragmentManager fm, int numberOfTabs, Context mContext) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab1ExchangeRate();
            case 1:
                return new Tab2Alarms();
            case 2:
                return new Tab3ProfitCalculator();
            case 3:
                return new Tab4Calculator();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.Exchange_rate);
            case 1:
                return mContext.getString(R.string.Alarms);
            case 2:
                return mContext.getString(R.string.calculator1);
            case 3:
                return mContext.getString(R.string.calculator2);
        }
        return null;
    }
}
