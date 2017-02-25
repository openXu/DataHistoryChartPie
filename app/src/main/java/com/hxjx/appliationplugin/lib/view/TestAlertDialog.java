package com.hxjx.appliationplugin.lib.view;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hxjx.appliationplugin.R;

/**
 * author : openXu
 * create at : 2017/2/22 14:33
 * class describe：
 */
public class TestAlertDialog {
    Activity activity;
    android.app.AlertDialog ad;
    Button btn_size, btn_test;

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
        btn_size.setOnClickListener(onClickListener);
        btn_test.setOnClickListener(onClickListener);
    }

    public TestAlertDialog(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity=activity;
        ad=new android.app.AlertDialog.Builder(activity).create();
        ad.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        window.setContentView(R.layout.dialog_test);
        btn_size=(Button)window.findViewById(R.id.btn_size);
        btn_test=(Button)window.findViewById(R.id.btn_test);
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }
}
