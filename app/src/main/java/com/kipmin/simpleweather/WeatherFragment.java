package com.kipmin.simpleweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yzl91 on 2017/9/18.
 */

public class WeatherFragment extends Fragment {

    public static WeatherFragment newInstance(String msg) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city", msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.weather_fragment, null);
        TextView textView = (TextView) view.findViewById(R.id.result_city);

        Bundle bundle = getArguments();
        String city;
        if (!bundle.getString("city", "").isEmpty()) {
            city = bundle.getString("city");
            textView.setText(city);
        } else {
            textView.setText("null");
        }
        return view;
    }

}
