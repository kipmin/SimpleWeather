package com.kipmin.simpleweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.kipmin.simpleweather.Adapter.ListAdapter;
import com.kipmin.simpleweather.Db.CityView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SelectCityActivity extends AppCompatActivity {

    private ListView selectCity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListAdapter myAdapter;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        dataList = getIntent().getStringArrayListExtra("dataList");
        layoutManager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.city_list);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new ListAdapter(dataList);
//        myAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, dataList);
        recyclerView.setAdapter(myAdapter);

    }

//    public void getDataList(List<String> dataList) {
//        this.dataList = dataList;
//    }

    private List<String> getDataList() {
        List<String> dataList = new ArrayList<>();
        List<CityView> cityViewList = DataSupport.findAll(CityView.class);
        for (CityView city : cityViewList) {
            dataList.add(city.getCnCity());
        }
        return dataList;
    }

}
