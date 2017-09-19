package com.kipmin.simpleweather;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * Created by yzl91 on 2017/9/18.
 */

public class CityAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<WeatherFragment> weatherFragments;

    public CityAdapter(FragmentManager fm, Context context, List<WeatherFragment> weatherFragments) {
        super(fm);
        this.context = context;
        this.weatherFragments = weatherFragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return weatherFragments.get(position);
    }

    @Override
    public int getCount() {
        return weatherFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

}
