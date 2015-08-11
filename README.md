
#Andtink_Android概述：


####借鉴了很多大神的思维，将MVC模式用于android开发中，完成了对此框架的初步封装，有不对的地方请大神多多指教，结构如图：

![](https://github.com/thekey123/Andthink_Android/blob/master/images/andthink_android框架图一.png)

#1.Model (IDataSource)

```java
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

```
#2.View（IDataAdapter）

```java
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

```

#3Controller (Activity,Fragment)

Activity负责调度，建议写在父类，子类只需返回需要的参数


```java
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

```

#在巨人肩上做事才会事半功倍:
</br></br>


* ###fastjson:阿里巴巴FastJson是一个Json处理工具包，包括“序列化”和“反序列化”两部分，它具备如下特征：速度最快，测试表明，fastjson具有极快的性能，超越任其他的Java Json parser。包括自称最快的JackJson；功能强大，完全支持Java Bean、集合、Map、日期、Enum，支持范型，支持自省；无依赖，能够直接运行在Java SE 5.0以上版本；支持Android；开源 (Apache 2.0)
</br></br></br>


* ###android-async-http :本框架的一切HTTP请求都基于此，so，不是一般的强大
</br></br></br>


* ###systembartint ：改变状态栏颜色--作为一个程序员也要有审美好伐~
</br></br></br>


##有问题反馈
</br>
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件(zhengzheng#andthink.cn, 把#换成@)
* QQ: 1512178050
