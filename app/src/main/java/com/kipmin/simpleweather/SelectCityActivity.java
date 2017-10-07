package com.kipmin.simpleweather;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.kipmin.simpleweather.Adapter.SelectAdapter;
import com.kipmin.simpleweather.Db.CityView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SelectCityActivity extends AppCompatActivity {
    
    private static final String TAG = "SelectCityActivity";

    private RecyclerView selectCity;
    private SelectAdapter myAdapter;
    private List<CityView> dataList;
    private int posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        dataList = DataSupport.findAll(CityView.class);
        selectCity = (RecyclerView) findViewById(R.id.city_list);


        selectCity.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new SelectAdapter(R.layout.city_list, dataList);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(selectCity);

        //开启拖拽
        myAdapter.enableDragItem(itemTouchHelper, R.id.test, true);
        myAdapter.setOnItemDragListener(onItemDragListener);

        //开启滑动删除
        myAdapter.enableSwipeItem();
        myAdapter.setOnItemSwipeListener(onItemSwipeListener);
        
        selectCity.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));// 添加分割线
        selectCity.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        Button selectBack = (Button) findViewById(R.id.select_back);
        selectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //拖拽的回调方法，未完成
    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "onItemDragStart: "+ pos);
        }

        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

        }

        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "onItemDragEnd: "+ pos);
        }
    };

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "onItemSwipeStart: "+ pos);
        }

        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d(TAG, "clearView: "+ pos);
        }

        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            posi = pos;
            Log.d(TAG, "onItemSwiped: " + pos + "dataList: " + dataList.get(pos).getCnCity());
            DataSupport.deleteAll(CityView.class, "cnCity = ?", dataList.get(pos).getCnCity());
            Intent intent = new Intent();
            intent.putExtra("posi", pos);
            setResult(RESULT_OK, intent);
            finish();
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

        }
    };

}
