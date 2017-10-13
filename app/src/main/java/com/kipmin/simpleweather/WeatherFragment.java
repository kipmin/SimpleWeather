package com.kipmin.simpleweather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kipmin.simpleweather.Gson.Weather.Weather;
import com.kipmin.simpleweather.Utility.HttpUtil;
import com.kipmin.simpleweather.Utility.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalBase.TAG;

/**
 * Created by yzl91 on 2017/9/18.
 * 每个天气View的实现
 */

public class WeatherFragment extends Fragment {

    private static final String HEFEN_KEY = "fe14c5fd224e466894854e7b19d1634b";

    private SwipeRefreshLayout swipeRefreshLayout;
    private String mWeatherId;
    private String city;
    private TextView cityName, temperature;

    public WeatherFragment newInstance(String msg) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city", msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view  = inflater.inflate(R.layout.weather_fragment, null);
        cityName = (TextView) view.findViewById(R.id.result_city);
        temperature = (TextView) view.findViewById(R.id.result_temperature);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        Bundle bundle = getArguments();//读取城市天气Id
        if (!bundle.getString("city", "").isEmpty()) {
            city = bundle.getString("city");
//            cityName.setText(city);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherString = preferences.getString("weather", null);

//        if (weatherString != null) {
            //有缓存的时候
        // 直接解析天气数据
//            Log.d(TAG, "onCreateView: if");
//            Weather weather = Utility.handleWeatherResponse(weatherString);
//            showWeatherInfo(weather);
//        } else {
            //无缓存的时候去服务器查询天气
            mWeatherId = city;
//            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
//        }

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新天气
                requestWeather(city);
                Log.d("Kipmin", "onRefresh: 刷新");
            }
        });

        return view;
    }

    public void requestWeather(final String weatherId) {

        String weatherUrl = "https://free-api.heweather.com/v5/weather?city=" + weatherId + "&key=" + HEFEN_KEY;
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: ");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                Log.d(TAG, "onResponse: ");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ");
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(getActivity(), "获取天气失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Weather weather) {
        String strCityName = weather.basic.cityName;
        String strTemperature = weather.now.temperature + "℃";

        cityName.setText(strCityName);
        temperature.setText(strTemperature);
        Log.d(TAG, "showWeatherInfo: ");
    }

}
