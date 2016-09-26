# RetrofitTest
自己封装的一个retrofit2.0+okHttp3+rxjava的网络请求工具类，拿过去直接可以使用

如下：
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        getDatas();

    }

    private void getDatas() {

        HttpUtils.execute(RestClient.getApiService().getWeatherDatas("allchina"),
                new BaseSubscriber<Weather>(this, true) {
                    @Override
                    public void onNext(Weather weather) {
                        mTvTest.setText(weather.city_info.get(5).city);
                        LogUtils.e(weather.city_info.get(1).prov);
                    }
                });

    }
}
