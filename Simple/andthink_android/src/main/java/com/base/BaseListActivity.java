package com.base;

import android.os.Bundle;

import com.andthink.mvc.MVCHelper;
import com.andthink.mvc.MVCNormalHelper;
import com.andthink.mvc.MVCPullrefshHelper;
import com.data.DataSource;
import com.http.OnhttpBase;
import java.util.Comparator;
import java.util.List;
/**
 * Created by Mr.zheng on 2015/7/20.
 * <p/>
 * 所有需要用列表呈现的界面的父类
 *
 */
public abstract class BaseListActivity<T> extends BaseActivity implements OnhttpBase<T>, Comparator<T> {

    private MVCHelper<List<T>> listViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getListview()== null) {
            listViewHelper = new MVCPullrefshHelper<List<T>>(getPulltorefreshView());
            // 设置适配器
            listViewHelper.setAdapter(getAdapter());
            // 设置数据源
            listViewHelper.setDataSource(new DataSource<T>(getUrlForList(), getRequestParams(), getClazz(), this));
            // 加载数据
            listViewHelper.refresh();
        } else {
            listViewHelper = new MVCNormalHelper<List<T>>(getListview(),getPulltorefreshView());
            listViewHelper.setAdapter(getAdapter());
            listViewHelper.setDataSource(new DataSource<T>(getUrlForList(), getRequestParams(), getClazz(), this));
            listViewHelper.refresh();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        if (listViewHelper != null)
            listViewHelper.destory();
    }

}
