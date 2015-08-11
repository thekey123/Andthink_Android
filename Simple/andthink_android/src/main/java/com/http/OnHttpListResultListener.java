package com.http;

import java.util.List;

/**
 * Created by Leo on 2015/5/31.
 *
 * 返回链表数据的统一回调
 */
public interface OnHttpListResultListener<T> {

    void onHttpResult(List<T> results);

}
