package com.kipmin.simpleweather.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kipmin.simpleweather.R;

import java.util.List;

/**
 * Created by yzl91 on 2017/9/24.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<String> dataList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView city;

        public ViewHolder(View itemView) {
            super(itemView);
            city = (TextView) itemView.findViewById(R.id.test);
        }
    }

    public ListAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cnCity = dataList.get(position);
        holder.city.setText(cnCity);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
