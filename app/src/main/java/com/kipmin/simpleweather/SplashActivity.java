package com.kipmin.simpleweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.kipmin.simpleweather.Db.CityDb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    private SharedPreferences isFirstPreferences, versionPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        isFirstOpen();
    }

    private boolean isFirstOpen() {
        isFirstPreferences = getSharedPreferences("count", MODE_PRIVATE);
        float nowVersionCode = getVersionCode(this);
        Log.i("MainActivity", "isFirstOpen: " + nowVersionCode);
        versionPreferences = getSharedPreferences("version", MODE_PRIVATE);
        int count = isFirstPreferences.getInt("count", 0);
        float spVersionCode = versionPreferences.getFloat("version", nowVersionCode);
        SharedPreferences.Editor versionEditor = versionPreferences.edit();
        SharedPreferences.Editor isFirstEditor = isFirstPreferences.edit();

        if (count == 0) { //应用被首次安装启动
            versionEditor.putFloat("spVersionCode", nowVersionCode);
            new Thread(new Runnable() {
                public void run() {
                    InitDatabases(getFromAssets("cityList.json"));
                    // Update the progress bar
                    mProgressStatus = mProgress.getProgress();
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }).start();
        } else if (nowVersionCode > spVersionCode){ //更新后首次启动
            versionEditor.putFloat("spVersionCode", nowVersionCode);
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
        isFirstEditor.putInt("count", ++count);
        versionEditor.apply();
        isFirstEditor.apply();
        return true;
    }

    private void InitDatabases(String response) {
        handleCity(response);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
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

    public boolean handleCity (String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject cityObject = jsonObject.getJSONObject("WeatherList");
            JSONArray cityArray = cityObject.getJSONArray("cityList");
            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject city = cityArray.getJSONObject(i);
                CityDb cityDb = new CityDb();
                cityDb.setCnName(city.getString("cnName"));
                cityDb.setWeatherId(city.getString("weatherId"));
//                cityDb.setLatitude(Double.valueOf(city.getString("lati")));
//                cityDb.setLongitude(Double.valueOf(city.getString("long")));
                cityDb.save();
                mProgress.incrementProgressBy(1);
            }
            Log.d("Kipmin", "handleCity: OK");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
