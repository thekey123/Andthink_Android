package com.http;

import android.widget.ListView;

import com.adapter.DataAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.HashMap;

/**
 * Created by Mr.zheng on 2015/7/18.
 */
public interface OnhttpBase<T> {

    /**
     * 获取链表数据的url
     * @return
     */
    String getUrlForList();

    /**
     * 获取请求的参数
     * @return
     */
    HashMap<String,Object> getRequestParams();

    /**
     * 返回T的字节码类型
     */
    Class<T> getClazz();

    /**
     * 获取pulltorefreshlistview
     */
    PullToRefreshBase<?> getPulltorefreshView();

    /**
     * 获取在scrollview下的listview，
     */

    ListView getListview();

    /**
     * 获取当前使用的Adapter
     *
     */
    DataAdapter<T> getAdapter();

}
