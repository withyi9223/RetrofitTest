package hdw.com.retrofittest.common;

import java.io.IOException;

import hdw.com.retrofittest.MyApp;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 离线读取本地缓存，在线获取最新数据(读取单个请求的请求头，亦可统一设置)
 *
 * @author zengyi
 *         create at 2016/9/18 13:54
 */
public class CacheControlInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (!NetUtils.isNetworkConnected(MyApp.getInstance())) {
            request = request.newBuilder()
                    //强制使用缓存
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        if (NetUtils.isNetworkConnected(MyApp.getInstance())) {
            //有网的时候读接口上的@Headers里的配置，进行统一的设置
            // 有网络时 设置缓存超时时间0个小时
            int maxAge = 0;
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // 超时时间4周
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }

    }
}