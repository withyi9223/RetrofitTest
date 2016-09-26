package hdw.com.retrofittest.common;

import android.app.Dialog;
import android.content.Context;

import hdw.com.retrofittest.R;
import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Dialog mDialog;
    private Context context;
    private boolean isShow;//默认不显示弹窗


    public BaseSubscriber(Context context, boolean isshow) {
        this.context = context;
        this.isShow = isshow;
    }

    @Override
    public void onError(Throwable e) {

        closeDialog();
        LogUtils.e(e.getMessage());
        //错误处理


    }


    @Override
    public void onStart() {
        super.onStart();

        if (isShow) {
            showDialog();
        }


        /*// todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
            onCompleted();
        }*/

    }


    @Override
    public void onCompleted() {
        closeDialog();
    }


    //显示加载提示
    public void showDialog() {
        if (mDialog != null) {
            mDialog = null;
        }
        mDialog = DialogUtil.createLoadingDialog(context,
                R.string.loading);
        mDialog.show();
    }


    //关闭提示
    public void closeDialog() {
        assert mDialog != null;
        mDialog.dismiss();
    }
}