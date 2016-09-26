package hdw.com.retrofittest.common;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 工具类 获取网络数据 json
 *
 * @author zengyi
 *         create at 2016/9/20 9:27
 */
public class HttpUtils {


    public static <T> T execute(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }


}
