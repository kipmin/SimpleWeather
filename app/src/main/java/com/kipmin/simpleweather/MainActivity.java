package com.kipmin.simpleweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kipmin.simpleweather.Adapter.CityAdapter;
import com.kipmin.simpleweather.Db.CityDb;
import com.kipmin.simpleweather.Utility.Utility;
import com.zaaach.citypicker.CityPickerActivity;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_PICK_CITY = 0;

    private ViewPager viewPager;
    private CityAdapter cityAdapter;
    private SharedPreferences isFirstPreferences, versionPreferences;
    private TextView result;
    private String city;
    private List<WeatherFragment> weatherFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isFirstOpen();//判断是否首次启动或更新后首次启动
        weatherFragmentList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        result = (TextView) findViewById(R.id.result_city);
        FragmentManager manager = getSupportFragmentManager();
        cityAdapter = new CityAdapter(manager, this, weatherFragmentList);
        viewPager.setAdapter(cityAdapter);


        //启动
        startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                REQUEST_CODE_PICK_CITY);
    }

    private boolean isFirstOpen() {
        isFirstPreferences = getSharedPreferences("count", MODE_PRIVATE);
        float nowVersionCode = getVersionCode(this);
        Log.i("MainActivity", "isFirstOpen: " + nowVersionCode);
        versionPreferences = getSharedPreferences("version", MODE_PRIVATE);
        int count = isFirstPreferences.getInt("count", 0);
        float spVersionCode = versionPreferences.getFloat("version", 0);
        SharedPreferences.Editor versionEditor = versionPreferences.edit();
        SharedPreferences.Editor isFirstEditor = isFirstPreferences.edit();

        if (count == 0) { //应用被首次安装启动
            versionEditor.putFloat("spVersionCode", nowVersionCode);
            InitDatabases(getFromAssets("cityList.json"));
        } else if (nowVersionCode > spVersionCode){ //更新后首次启动
            versionEditor.putFloat("spVersionCode", nowVersionCode);
        }
        isFirstEditor.putInt("count", ++count);
        versionEditor.apply();
        isFirstEditor.apply();
        return true;
    }

    private void InitDatabases(String response) {
        Utility.handleCity(response);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            case R.id.about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.select_city:
                startActivity(new Intent(MainActivity.this, SelectCityActivity.class));
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                List<CityDb> cityDbList;
                String cityId;
                cityDbList = DataSupport.where("cnCity = ?", String.valueOf(city)).find(CityDb.class);
                cityId = cityDbList.get(0).getWeatherId();
                WeatherFragment fragment = new WeatherFragment().newInstance(cityId);
                weatherFragmentList.add(fragment);
                cityAdapter.notifyDataSetChanged();
            }
        }
    }

    private float getVersionCode(Context context) {
        float versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  versionCode;
    }

    // 读取assets中的文件
    private String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public void InitDatabases(String address) {
//        HttpUtil.sendOkHttpRequest(address, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Toast.makeText(MainActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                if (Utility.handleCity(responseText));
//                else {
//                    throw new IOException("JSON未成功解析");
//                }
//            }
//        });
//    }

}
