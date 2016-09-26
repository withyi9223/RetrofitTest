package hdw.com.retrofittest.common;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hdw.com.retrofittest.MyApp;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    public static final String TAG = "RestClient";
    private static Retrofit retrofit;
    private static APIService apiService;
    private static OkHttpClient okHttpClient;
    private static String URL_BASE = "https://api.heweather.com/x3/";
    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;


    public static APIService getApiService() {

        if (apiService == null) {
            apiService = getRetrofit().create(APIService.class);
        }
        return apiService;
    }


    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())//加入json解析
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
                    .client(getClient())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getClient() {

       /* HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);*/

        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()

                    .cache(cache()) //设置缓存目录

                    //.addInterceptor(headerInterceptor) //设置头部信息

                    .addInterceptor(addQueryParameterInterceptor) //公共参数

                    .addInterceptor(new LogInterceptor(TAG))//打印日志

                    .addNetworkInterceptor(new CacheControlInterceptor())//设置缓存
                    .addInterceptor(new CacheControlInterceptor())

                    .retryOnConnectionFailure(true) //失败重连

                    .connectTimeout(5, TimeUnit.SECONDS) //超时
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)

                    .build();
        }
        return okHttpClient;
    }


    /**
     * okhttp缓存目录设置
     */
    private static Cache cache() {
        //设置缓存路径
        File baseDir = MyApp.getInstance()
                .getApplicationContext()
                .getCacheDir()
                .getAbsoluteFile();//程序安装目录下的临时目录
        File cacheDir = new File(baseDir, "ResponseCache");


        //设置缓存 10M
        return new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
    }


    /**
     * 公共参数
     */
    private static Interceptor addQueryParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request request;
            //String method = originalRequest.method();
            //Headers headers = originalRequest.headers();

            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("key", "44e1f30358644870acbabb390d202f5a")
                    .build();
            request = originalRequest.newBuilder().url(modifiedUrl).build();


            return chain.proceed(request);
        }
    };
    /**
     * 头部信息
     */
    private static Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .header("AppType", "TPOS")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .method(originalRequest.method(), originalRequest.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    };
}