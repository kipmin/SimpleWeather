package com.kipmin.simpleweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kipmin.simpleweather.Adapter.CityAdapter;
import com.kipmin.simpleweather.Db.CityDb;
import com.kipmin.simpleweather.Db.CityView;
import com.zaaach.citypicker.CityPickerActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    protected static final int REQUEST_CODE_PICK_CITY = 0;
    protected static final int UPDATE_CITY = 1;


    private CityAdapter cityAdapter;
    private ViewPager viewPager;
//    private SharedPreferences isFirstPreferences, versionPreferences;
    private List<CityView> cityViewList;
    public List<WeatherFragment> weatherFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        isFirstOpen();//判断是否首次启动或更新后首次启动
        weatherFragmentList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        cityAdapter = new CityAdapter(manager, this, weatherFragmentList);
        viewPager.setAdapter(cityAdapter);

        cityViewList = DataSupport.findAll(CityView.class); //判断是否添加城市，重建viewpager
        if (cityViewList.isEmpty()) {
            //更换为引导页
//            startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
//                    REQUEST_CODE_PICK_CITY);
        } else {
            for (CityView city : cityViewList) {
                String weatherId = city.getWeatherId();
                WeatherFragment fragment = new WeatherFragment().newInstance(weatherId);
                weatherFragmentList.add(fragment);
            }
            cityAdapter.notifyDataSetChanged();
        }

    }

//    private boolean isFirstOpen() {
//        isFirstPreferences = getSharedPreferences("count", MODE_PRIVATE);
//        float nowVersionCode = getVersionCode(this);
//        Log.i("MainActivity", "isFirstOpen: " + nowVersionCode);
//        versionPreferences = getSharedPreferences("version", MODE_PRIVATE);
//        int count = isFirstPreferences.getInt("count", 0);
//        float spVersionCode = versionPreferences.getFloat("version", 0);
//        SharedPreferences.Editor versionEditor = versionPreferences.edit();
//        SharedPreferences.Editor isFirstEditor = isFirstPreferences.edit();
//
//        if (count == 0) { //应用被首次安装启动
//            versionEditor.putFloat("spVersionCode", nowVersionCode);
//            InitDatabases(getFromAssets("cityList.json"));
//        } else if (nowVersionCode > spVersionCode){ //更新后首次启动
//            versionEditor.putFloat("spVersionCode", nowVersionCode);
//        }
//        isFirstEditor.putInt("count", ++count);
//        versionEditor.apply();
//        isFirstEditor.apply();
//        return true;
//    }
//
//    private void InitDatabases(String response) {
//        Utility.handleCity(response);
//    }

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
                Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                startActivityForResult(intent, UPDATE_CITY);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //每次选择城市
        String countCity;
        switch (requestCode){
            case REQUEST_CODE_PICK_CITY:
                if (data != null){
                    String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                    if (!DataSupport.where("cnName = ?", city).find(CityView.class).isEmpty()) {
                        countCity = DataSupport.where("cnName = ?", city).find(CityView.class).get(0).getCnName();
                    } else {
                        countCity = null;
                    }
                    if (!city.equals(countCity)) {
                        List<CityDb> cityDbList = DataSupport.where("cnName = ?", String.valueOf(city)).find(CityDb.class);
                        String weatherId = cityDbList.get(0).getWeatherId();
                        CityView cityView = new CityView();
                        cityView.setCnName(city);
                        cityView.setWeatherId(weatherId);
                        cityView.save();
                        WeatherFragment fragment = new WeatherFragment().newInstance(weatherId);
                        weatherFragmentList.add(fragment);
                        cityAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "已存在该城市，请勿重复添加", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case UPDATE_CITY:
                int removePosition = data.getIntExtra("posi", weatherFragmentList.size()+1);
                if (removePosition < weatherFragmentList.size()) {
                    weatherFragmentList.remove(removePosition);
                    cityAdapter.notifyDataSetChanged();
                } else {
//                    Toast.makeText(this, "删除城市失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Log.d(TAG, "onActivityResult: default");
                break;
        }
    }

//    private float getVersionCode(Context context) {
//        float versionCode = 0;
//        try {
//            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        return  versionCode;
//    }
//
//    // 读取assets中的文件
//    private String getFromAssets(String fileName){
//        try {
//            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
//            BufferedReader bufReader = new BufferedReader(inputReader);
//            String line="";
//            String Result="";
//            while((line = bufReader.readLine()) != null)
//                Result += line;
//            return Result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

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
