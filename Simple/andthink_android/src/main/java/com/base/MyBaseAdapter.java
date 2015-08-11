package com.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Mr.zheng on 2015/5/7.
 *
 * 封装BaseAdapter,无需数据的list适用
 *
 */
public abstract class MyBaseAdapter extends BaseAdapter {

    protected Context getCurrentContext;
    protected LayoutInflater getCurrentInflater;
    protected List<? extends Object> getCurrentData;

    public MyBaseAdapter( Context context, List<? extends Object> data){
        getCurrentContext = context;
        getCurrentData = data;
        getCurrentInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return getCurrentData == null ? 0 : getCurrentData.size();
    }

    @Override
    public Object getItem(int position) {
        return getCurrentData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
