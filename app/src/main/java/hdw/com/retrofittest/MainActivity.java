package hdw.com.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hdw.com.retrofittest.bean.Weather;
import hdw.com.retrofittest.common.BaseSubscriber;
import hdw.com.retrofittest.common.HttpUtils;
import hdw.com.retrofittest.common.LogUtils;
import hdw.com.retrofittest.common.RestClient;

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
