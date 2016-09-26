package hdw.com.retrofittest;

import android.app.Application;

/**
 * @author new
 *         create at 2016/9/26 15:12
 */
public class MyApp extends Application {


    public static final String TAG = "MyApp";
    public static MyApp mInstance = null;

    public MyApp() {
    }

    public static synchronized MyApp getInstance() {
        if (mInstance == null) {
            mInstance = new MyApp();
        }
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }
}
