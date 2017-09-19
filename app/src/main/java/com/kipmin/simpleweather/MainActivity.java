package com.kipmin.simpleweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kipmin.simpleweather.Db.CityDb;
import com.kipmin.simpleweather.Utility.HttpUtil;
import com.kipmin.simpleweather.Utility.Utility;
import com.zaaach.citypicker.CityPickerActivity;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_PICK_CITY = 0;
    protected static int FIRST_OPEN = 0;

    private ViewPager viewPager;
    private CityAdapter cityAdapter;
    private TextView result;
    private String city;
    private List<WeatherFragment> weatherFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainAvtivity", "onCreate: " + FIRST_OPEN);
        if (FIRST_OPEN == 0) {
            String address = "https://www.kipmin.cc/h/cityList.json";
            InitDatabases(address);
            FIRST_OPEN = 1;
        }

        weatherFragmentList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fragment);
        setSupportActionBar(toolbar);
        Button button = (Button) findViewById(R.id.city_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        result = (TextView) findViewById(R.id.result_city);
        FragmentManager manager = getSupportFragmentManager();
        cityAdapter = new CityAdapter(manager, this, weatherFragmentList);
        viewPager.setAdapter(cityAdapter);


        //启动
        startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                REQUEST_CODE_PICK_CITY);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.add_list, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.add:
//                startActivityForResult(new Intent(this, CityPickerActivity.class),
//                        REQUEST_CODE_PICK_CITY);
//                break;
//            default:
//        }
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                List<CityDb> cityDbList;
                String cityId;
//                if (!city.isEmpty()) {
                    cityDbList = DataSupport.where("cnCity = ?", String.valueOf(city)).find(CityDb.class);
                    cityId = cityDbList.get(0).getWeatherId();
//                } else {
//                    cityId = city;
//                }
                WeatherFragment fragment = new WeatherFragment().newInstance(cityId);
                weatherFragmentList.add(fragment);
                cityAdapter.notifyDataSetChanged();
            }
        }
    }

    public void InitDatabases(String address) {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MainActivity", "onFailure: ",e );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.handleCity(responseText);
            }
        });
    }
}
