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
 * Created by Mr.zheng on 2015/7/25.
 *
 *
 *
 */
public abstract class BaseListFragment<T> extends BaseFragment implements OnhttpBase<T>, Comparator<T> {

    private MVCHelper<List<T>> listViewHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getListview()== null) {
            listViewHelper = new MVCPullrefshHelper<List<T>>(getPulltorefreshView());
            listViewHelper.setAdapter(getAdapter());
            listViewHelper.setDataSource(new DataSource<T>(getUrlForList(), getRequestParams(), getClazz(), this));
            listViewHelper.refresh();
        } else {
            listViewHelper = new MVCNormalHelper<List<T>>(getListview(),getPulltorefreshView());
            listViewHelper.setAdapter(getAdapter());
            listViewHelper.setDataSource(new DataSource<T>(getUrlForList(), getRequestParams(), getClazz(), this));
            listViewHelper.refresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listViewHelper != null)
            listViewHelper.destory();
    }

}
