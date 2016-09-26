package hdw.com.retrofittest.common;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 日志过滤器，输出请求相关信息
 */
public class LogInterceptor implements Interceptor {
    public  String TAG;


    public LogInterceptor(String tag) {
        TAG=tag;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.e(TAG, "request's url " + request.url());//打印请求URL
        long t1 = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.currentTimeMillis();
        LogUtils.e(TAG, "request's time  " + (t2 - t1) + "");//打印请求所需要的时间
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.e(TAG, "response body:  " + content);//打印请求得到的结果
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}