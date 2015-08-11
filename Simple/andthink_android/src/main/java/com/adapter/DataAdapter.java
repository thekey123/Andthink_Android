package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.andthink.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.zheng on 2015/7/20.
 * <p/>
 * 用来处理将所有的数据装配到adapter的操作
 */
public abstract class DataAdapter<T> extends BaseAdapter implements IDataAdapter<List<T>> {

    protected List<T> data = new ArrayList<T>();
    protected Context context;
    protected LayoutInflater inflater;

    public DataAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 在MVCHelper中直接操作界面
    */
    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public boolean isEmpty() {
        return data.size() == 0;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
