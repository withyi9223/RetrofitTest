package hdw.com.retrofittest.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hdw.com.retrofittest.R;


public class DialogUtil {

    public static AnimationDrawable animDraw = null;

    public static Dialog createLoadingDialog(Context context, int msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 加载布局
        TextView tipTextView = (TextView) v.findViewById(R.id.tvLoadingTitle);// 提示文字
        tipTextView.setText(msg);// 设置加载信息


        ImageView ivAnim = (ImageView) v.findViewById(R.id.iv_anim);
        //获取到xml布局文件
        animDraw = (AnimationDrawable) ivAnim.getBackground();
        ivAnim.post(new Runnable() {
            @Override
            public void run() {
                animDraw.start();
            }
        });


        Dialog loadingDialog = new Dialog(context, R.style.MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局

        return loadingDialog;
    }

}