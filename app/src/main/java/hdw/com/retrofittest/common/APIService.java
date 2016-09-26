package hdw.com.retrofittest.common;

import hdw.com.retrofittest.bean.Weather;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 接口服务类
 *
 * @author zengyi
 *         create at 2016/9/18 16:57
 */
public interface APIService {

    //两个参数的
    /*@POST("citylist")
    Call<Weather> getWeather(
            @QueryMap Map<String, String> map);*/

    //测试公共参数
    @POST("citylist")
    Call<Weather> getWeather(
            @Query("search") String search);

    // @Headers("Cache-Control: public, max-age=3600")
    @POST("citylist")
    Observable<HttpResult> getWeatherDatas1(@Query("search") String search);

    @POST("citylist")
    Observable<Weather> getWeatherDatas(@Query("search") String search);
}
