package hdw.com.retrofittest.bean;

import java.util.List;

/**
 * @author new
 *         create at 2016/9/18 15:59
 */
public class Weather {


    public String status;

    public List<CityInfoBean> city_info;

    public static class CityInfoBean {
        public String city;
        public String cnty;
        public String id;
        public String lat;
        public String lon;
        public String prov;
    }
}
