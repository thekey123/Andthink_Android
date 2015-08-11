
#Andtink_Android������


####����˺ܶ�����˼ά����MVCģʽ����android�����У�����˶Դ˿�ܵĳ�����װ���в��Եĵط��������ָ�̣��ṹ��ͼ��

![](https://github.com/thekey123/Andthink_Android/blob/master/images/andthink_android���ͼһ.png)

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
    //����ˢ��
    private final static int PULL_DOWN = 1;
    //����ˢ��
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
        //��������url
        httpEngine.setUrl(url);
        //�����������
        httpEngine.setRequestParams(hashMap);
        httpEngine.SynPost(new AsyncHttpResponseHandler(Looper.getMainLooper()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = httpEngine.getResultFromHttp(responseBody);
                //resultsΪ�ӷ�������ȡ������
                List<T> results = JSON.parseArray(result, tClass);
                //��ȡ����ǰ���ص���������
                int pre_num = data.size();
                //���������ظ�������ȥ��
                data.removeAll(results);
                //���������ϵ�������ӵ����ص�����
                data.addAll(results);
                //����
                Collections.sort(data, comparator);
                //��������������
                int update_num = data.size() - pre_num;
                start += update_num;
                //���������ˢ�£��Ҽ������������ݣ���ô�ͽ�hasData��Ϊfalse
                if (update_num == 0 && pullType == PULL_UP) {
                    hasData = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //���ٶԻ�ȡʧ�����������û�гɹ�����list��ֱ����ʾʧ�ܽ���
            }
        });
        return data;
    }
}

```
#2.View��IDataAdapter��

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
     * ��MVCHelper��ֱ�Ӳ�������
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

Activity������ȣ�����д�ڸ��࣬����ֻ�践����Ҫ�Ĳ���


```java
public abstract class BaseListActivity<T> extends BaseActivity implements OnhttpBase<T>, Comparator<T> {

    private MVCHelper<List<T>> listViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getListview()== null) {
            listViewHelper = new MVCPullrefshHelper<List<T>>(getPulltorefreshView());
            // ����������
            listViewHelper.setAdapter(getAdapter());
            // ��������Դ
            listViewHelper.setDataSource(new DataSource<T>(getUrlForList(), getRequestParams(), getClazz(), this));
            // ��������
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
        // �ͷ���Դ
        if (listViewHelper != null)
            listViewHelper.destory();
    }

```

#�ھ��˼������²Ż��°빦��:
</br></br>


* ###fastjson:����Ͱ�FastJson��һ��Json�����߰������������л����͡������л��������֣����߱������������ٶ���죬���Ա�����fastjson���м�������ܣ���Խ��������Java Json parser�������Գ�����JackJson������ǿ����ȫ֧��Java Bean�����ϡ�Map�����ڡ�Enum��֧�ַ��ͣ�֧����ʡ�����������ܹ�ֱ��������Java SE 5.0���ϰ汾��֧��Android����Դ (Apache 2.0)
</br></br></br>


* ###android-async-http :����ܵ�һ��HTTP���󶼻��ڴˣ�so������һ���ǿ��
</br></br></br>


* ###systembartint ���ı�״̬����ɫ--��Ϊһ������ԱҲҪ�������÷�~
</br></br></br>


##�����ⷴ��
</br>
��ʹ�������κ����⣬��ӭ�������ң�������������ϵ��ʽ���ҽ���

* �ʼ�(zhengzheng#andthink.cn, ��#����@)
* QQ: 1512178050
