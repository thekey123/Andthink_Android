package com.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2015/5/5.
 *
 * HTTP工厂类
 */
public class HttpEngine<T> {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    /**访问url*/
    private String url;
    /**请求参数*/
    private RequestParams params = new RequestParams();

    static{
        //HttpClient配置
        client.setTimeout(10 * 1000);
    }

    /**
     * 获取Http对象
     * @return
     */
    public static HttpEngine getInstance(){
        return new HttpEngine();
    }

    /**
     * 获取HttpClient对象（单例模式）
     * @return
     */
    public static synchronized AsyncHttpClient getHttpClientInstance(){
        return client;
    }

    /**
     * 设置Url
     * @param url
     * @return
     */
    public HttpEngine setUrl(String url){
        this.url = url;
        return this;
    }

    /**
     * 设置请求参数
     * @param hashMap
     * @return
     */
    public HttpEngine setRequestParams( HashMap<String,Object> hashMap){

        Iterator iter = hashMap.entrySet().iterator();
        JSONObject jsonObject = new JSONObject();
        while( iter.hasNext() ){
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Object object = entry.getValue();
            jsonObject.put(key,object);
        }
        params.put("data",jsonObject.toJSONString());
        return this;
    }

    /**
     * 添加上传文件
     * @param key
     * @param file
     * @return
     */
    public HttpEngine putUploadFile( String key,File file ){
        try {
            params.put(key,file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 将从网络上获取的byte字节数组转为我们需要的字符串
     * @param responseBody
     * @return
     */
    public String getResultFromHttp( byte[] responseBody ){
        try {
            String result = new String(responseBody,"gb2312");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回对象数据
     */
    public void doPostForObjectResult(final Class cls, final OnHttpObjectResultListener<T> listener){
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = getResultFromHttp(responseBody);
                if( result == null)
                    listener.onHttpResult(null);
                else{
                    T t = (T) JSON.parseObject(result, cls);
                    listener.onHttpResult(t);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    listener.onHttpResult(null);
            }
        });
    }

    /**
     * 返回链表数据
     * @param listener
     */
    public void doPostForListResult(final Class cls, final OnHttpListResultListener<T> listener){
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = getResultFromHttp(responseBody);

                if( result == null)
                    listener.onHttpResult(new ArrayList<T>());
                else{
                    List<T> results = JSON.parseArray(result, cls);
                    listener.onHttpResult(results);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                listener.onHttpResult(new ArrayList<T>());

            }
        });
    }

    /**
     * 返回String数据
     * @param listener
     */
    public void doPostForStringResult(final OnHttpResultListener listener){
        client.post(url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = getResultFromHttp(responseBody);
                    listener.onHttpResult(result);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

        });
    }

    /**
     * 使用原生的方式请求服务器
     * @param handler
     */
    public void AsynPost( AsyncHttpResponseHandler handler ){
        client.post(url,params,handler);
    }

    /**
     * 使用原生同步请求的方式访问服务器
     */
    public void SynPost(AsyncHttpResponseHandler handler) {
        syncHttpClient.post(url, params, handler);
    }
}
