package com.hxjx.appliationplugin.lib.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hxjx.appliationplugin.PluginViewStyleWaterBlue;
import com.hxjx.appliationplugin.R;

/**
 * author : openXu
 * create at : 2017/2/22 14:33
 * class describe：
 */
public class SizeAlertDialog {
    Activity activity;
    android.app.AlertDialog ad;
    EditText et_w, et_h;
    Button btn_ok, btn_cancel;

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener onClickListener){
        this.listener = onClickListener;
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strW = et_w.getText().toString().trim();
                String strH = et_h.getText().toString().trim();

                boolean ok = true;
                int intW = -1;
                int intH = -1;

                try{
                    intW = Integer.parseInt(strW);
                    intW = intW> PluginViewStyleWaterBlue.MAX_WIDTH?PluginViewStyleWaterBlue.MAX_WIDTH:intW;
                    intW = intW<PluginViewStyleWaterBlue.MIN_WIDTH?PluginViewStyleWaterBlue.MIN_WIDTH:intW;
                }catch (Exception e){
                    e.printStackTrace();
                    ok = false;
                }
                try{
                    intH = Integer.parseInt(strH);
                    intH = intH>PluginViewStyleWaterBlue.MAX_HEIGHT?PluginViewStyleWaterBlue.MAX_HEIGHT:intH;
                    intH = intH<PluginViewStyleWaterBlue.MIN_HEIGHT?PluginViewStyleWaterBlue.MIN_HEIGHT:intH;
                }catch (Exception e){
                    ok = false;
                }
                Log.i("openXu", "设置宽高："+intW+" * "+intH);
                InputMethodManager imm = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_w,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(et_w.getWindowToken(), 0); //强制隐藏键盘
                if(ok){
                    listener.onSizeChange(intW, intH);
                }else{
                    dismiss();
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SizeAlertDialog.this.dismiss();
            }
        });
    }

    public interface OnClickListener{
        public abstract void onSizeChange(int w, int h);
    }

    public SizeAlertDialog(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity=activity;

        ad=new android.app.AlertDialog.Builder(activity).create();
        ad.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        //需要添加此句，才能弹出软键盘
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_size);
        //加这句代码，new一个空的edittext，解决输入法无法弹出的问题
        ad.setView(new EditText(activity));
        et_w=(EditText)window.findViewById(R.id.et_w);
        et_h=(EditText)window.findViewById(R.id.et_h);
        btn_cancel=(Button)window.findViewById(R.id.btn_cancel);
        btn_ok=(Button)window.findViewById(R.id.btn_ok);
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }
}
