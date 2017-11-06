package com.kipmin.weatherbulletin;

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

import com.kipmin.weatherbulletin.Adapter.CityAdapter;
import com.kipmin.weatherbulletin.Db.CityDb;
import com.kipmin.weatherbulletin.Db.CityView;
import com.zaaach.citypicker.CityPickerActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<WeatherFragment> weatherFragmentList;

    private static final String TAG = "MainActivity";
    protected static final int REQUEST_CODE_PICK_CITY = 0;
    protected static final int UPDATE_CITY = 1;


    protected ViewPager viewPager;
    protected List<CityView> cityViewList;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherFragmentList = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentManager manager = getSupportFragmentManager();
        cityAdapter = new CityAdapter(manager, this, weatherFragmentList);
        viewPager.setAdapter(cityAdapter);
//        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
//        viewPagerTab.setViewPager(viewPager);


        cityViewList = DataSupport.findAll(CityView.class); //判断是否添加城市，重建viewpager
        if (cityViewList.isEmpty()) {
            //更换为引导页
            startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                    REQUEST_CODE_PICK_CITY);
        } else {
            for (CityView city : cityViewList) {
                String weatherId = city.getWeatherId();
                WeatherFragment fragment = new WeatherFragment().newInstance(weatherId);
                weatherFragmentList.add(fragment);
            }
            cityAdapter.notifyDataSetChanged();
        }

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

}
