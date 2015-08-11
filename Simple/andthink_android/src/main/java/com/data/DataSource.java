package com.data;

import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.andthink.mvc.IDataSource;
import com.http.HttpEngine;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Mr.zheng on 2015/7/18.
 * <p/>
 * 所有的数据源
 */
public class DataSource<T> implements IDataSource<List<T>> {

    private int start = 0;
    private String url;
    private HashMap<String, Object> hashMap;
    private Class<T> tClass;
    private boolean hasData = true;
    private List<T> data;
    private Comparator<T> comparator;
    //下拉刷新
    private final static int PULL_DOWN = 1;
    //上拉刷新
    private final static int PULL_UP = 2;

    public DataSource(String url, HashMap<String, Object> hashMap, Class<T> tClass, Comparator<T> comparator) {
        this.url = url;
        this.hashMap = hashMap;
        this.tClass = tClass;
        this.comparator = comparator;
    }

    @Override
    public List<T> refresh() throws Exception {
        HashMap<String, Object> params = hashMap;
        params.put("start", 0);
        return loadData(hashMap, PULL_DOWN);
    }

    @Override
    public List<T> loadMore() throws Exception {
        hashMap.put("start", start);
        return loadData(hashMap,PULL_UP);
    }

    @Override
    public boolean hasMore() {
        return hasData;
    }

    @Override
    public void setData(List<T> list) {
        this.data = list;
    }

    private List<T> loadData(HashMap<String, Object> hashMap, final int pullType) {
        final HttpEngine<T> httpEngine = new HttpEngine<>();
        //设置请求url
        httpEngine.setUrl(url);
        //设置请求参数
        httpEngine.setRequestParams(hashMap);
        httpEngine.SynPost(new AsyncHttpResponseHandler(Looper.getMainLooper()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = httpEngine.getResultFromHttp(responseBody);
                //results为从服务器获取的数据
                List<T> results = JSON.parseArray(result, tClass);
                //获取更新前本地的数据条数
                int pre_num = data.size();
                //将本地上重复的数据去掉
                data.removeAll(results);
                //将服务器上的数据添加到本地的数据
                data.addAll(results);
                //排序
                Collections.sort(data, comparator);
                //新增的数据条数
                int update_num = data.size() - pre_num;
                start += update_num;
                //如果是上拉刷新，且加载完所有数据，那么就将hasData设为false
                if (update_num == 0 && pullType == PULL_UP) {
                    hasData = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //不再对获取失败做处理，如果没有成功返回list则直接显示失败界面
            }
        });
        return data;
    }
}
