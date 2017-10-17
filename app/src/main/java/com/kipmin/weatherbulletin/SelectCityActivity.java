package com.kipmin.weatherbulletin;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.kipmin.weatherbulletin.Adapter.SelectAdapter;
import com.kipmin.weatherbulletin.Db.CityView;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 删除城市界面，拖拽城市排序
 */

public class SelectCityActivity extends SwipeBackActivity{
    
    private static final String TAG = "SelectCityActivity";

    private RecyclerView selectCity;
    private SelectAdapter myAdapter;
    private List<CityView> dataList;
    private final Paint paint = new Paint();;
    private int posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        dataList = DataSupport.findAll(CityView.class);
        selectCity = (RecyclerView) findViewById(R.id.city_list);

        //开启侧滑返回
        setSwipeBackEnable(true);
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        selectCity.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new SelectAdapter(R.layout.city_list, dataList);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(selectCity);

        //开启拖拽
        myAdapter.enableDragItem(itemTouchHelper, R.id.list_city_name, true);
        myAdapter.setOnItemDragListener(onItemDragListener);

        //开启滑动删除
        paint.setAntiAlias(true);
        paint.setTextSize(56);
        paint.setColor(Color.BLACK);
        myAdapter.enableSwipeItem();
        myAdapter.setOnItemSwipeListener(onItemSwipeListener);
        
        selectCity.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));// 添加分割线
        selectCity.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        setResult(RESULT_OK, new Intent());//防止返回出错

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
            Log.d(TAG, "onItemSwiped: " + pos + "dataList: " + dataList.get(pos).getCnName());
            DataSupport.deleteAll(CityView.class, "cnName = ?", dataList.get(pos).getCnName());
            Intent intent = new Intent();
            intent.putExtra("posi", pos);
            setResult(RESULT_OK, intent);
            Toast.makeText(SelectCityActivity.this, dataList.get(pos).getCnName() + " 已删除 ", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            canvas.drawColor(ContextCompat.getColor(SelectCityActivity.this, R.color.themePrimary));
            canvas.drawText("删除", 200, 100, paint);
        }
    };

}
