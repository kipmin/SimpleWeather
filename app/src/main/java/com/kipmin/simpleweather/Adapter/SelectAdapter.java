package com.kipmin.simpleweather.Adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kipmin.simpleweather.Db.CityView;
import com.kipmin.simpleweather.R;

import java.util.List;

/**
 * Created by yzl91 on 2017/10/3.
 */

public class SelectAdapter extends BaseItemDraggableAdapter<CityView, BaseViewHolder> {

    public SelectAdapter(int layoutId ,List<CityView> data) {
        super(layoutId, data);
//        Log.d(TAG, "SelectAdapter: " + data.get(0).getCnName());
    }

    @Override
    protected void convert(BaseViewHolder helper, CityView item) {
        Log.d(TAG, "convert: ");
        helper.setText(R.id.list_city_name, item.getCnName());
    }

//    OnItemDragListener

}
