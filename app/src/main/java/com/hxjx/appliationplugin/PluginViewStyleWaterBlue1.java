package com.hxjx.appliationplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hxjx.appliationplugin.lib.bean.PieDataEntity;
import com.hxjx.appliationplugin.lib.util.Constant;
import com.hxjx.appliationplugin.lib.view.PieChart1;
import com.hxjx.appliationplugin.lib.view.SizeAlertDialog;
import com.hxjx.appliationplugin.lib.view.TestAlertDialog;

import java.util.ArrayList;

public class PluginViewStyleWaterBlue1 extends Activity {


    private PieChart1 pieChart1;
    private LinearLayout ll, ll_content1, ll_content2;
    private ScrollView sv_content;
    private RelativeLayout rl_lik;

    private ArrayList<PieDataEntity> pieDatas;
    private int selectIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_view_style_water_blue1);
        pieChart1 = (PieChart1) findViewById(R.id.chart);
        sv_content = (ScrollView) findViewById(R.id.sv_content);
        rl_lik = (RelativeLayout) findViewById(R.id.rl_lik);
        ll = (LinearLayout) findViewById(R.id.ll);
        ll_content1 = (LinearLayout) findViewById(R.id.ll_content1);
        ll_content2 = (LinearLayout) findViewById(R.id.ll_content2);

        pieDatas = getIntent().getParcelableArrayListExtra("pieDatas");

        pieChart1.setSizeChangeListenr(new PieChart1.SizeChangeListenr() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scaleContent(w, h);
            }
        });

        selectIndex = getIntent().getIntExtra("selectIndex", 42);

        Log.v("openXu", selectIndex+ "   "+pieDatas+"");

        /**test*/
        if(pieDatas==null||pieDatas.size()<=0) {
            pieDatas = new ArrayList<>();
            pieDatas.add(new PieDataEntity("张三", 56, Constant.CHARTCOLOR[0]));
            pieDatas.add(new PieDataEntity("李四", 25, Constant.CHARTCOLOR[1]));
            pieDatas.add(new PieDataEntity("王五", 39, Constant.CHARTCOLOR[2]));
            pieDatas.add(new PieDataEntity("赵六", 45, Constant.CHARTCOLOR[3]));
            pieDatas.add(new PieDataEntity("田七", 87, Constant.CHARTCOLOR[4]));
            pieDatas.add(new PieDataEntity("欧阳修", 78, Constant.CHARTCOLOR[5]));
            pieDatas.add(new PieDataEntity("西门庆", 46, Constant.CHARTCOLOR[6]));
            pieDatas.add(new PieDataEntity("南宫雪", 36, Constant.CHARTCOLOR[7]));
            pieDatas.add(new PieDataEntity("张飞", 97, Constant.CHARTCOLOR[8]));
            pieDatas.add(new PieDataEntity("赵云", 78, Constant.CHARTCOLOR[9]));
        }

        //刷新数据
        Set_ChangeView_layoutShowTestDataFresh();
        rl_lik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_onClick_SettingDialog();
            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_onClick_SettingDialog();
            }
        });

    }


    /**查询本地数据*/
    private void Send_MainLayoutDataSourceQuery(){
        ArrayList<PieDataEntity> list =  pieChart1.getDataList();
    }
    /**弹出设置界面*/
    private void Set_onClick_SettingDialog(){
        final TestAlertDialog textDialog = new TestAlertDialog(PluginViewStyleWaterBlue1.this);
        textDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDialog.dismiss();
                switch (v.getId()){
                    case R.id.btn_size:
                        final SizeAlertDialog sizeDialog = new SizeAlertDialog(PluginViewStyleWaterBlue1.this);
                        sizeDialog.setOnClickListener(new SizeAlertDialog.OnClickListener() {
                            @Override
                            public void onSizeChange(int w, int h) {
                                sizeDialog.dismiss();
                                Set_ChangeView_layoutShowDimension(w, h);
                            }
                        });
                        break;
                    case R.id.btn_test:
                        Intent intent = new Intent(PluginViewStyleWaterBlue1.this, OutsourceViewSetting.class);
                        intent.putExtra("selectIndex",selectIndex);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });

    }
    /**实时更新主页尺寸变化*/
    private void Set_ChangeView_layoutShowDimension (int w,int h ){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pieChart1.getLayoutParams();
        params.width = w;
        params.height = h;
        pieChart1.setLayoutParams(params);

    }

    private void scaleContent(int w, int h){
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int screenWidth = screenWidth = display.getWidth();
        int screenHeight = screenHeight = display.getHeight();
        Log.v("openxu","手机分辨率："+screenWidth+"*"+screenHeight);
        float scale = (h*1.0f)/(screenHeight*1.0f);
        Log.e("openXu","获取控件高度为"+h+"   scale="+scale);
        if(scale>1){
            scale = 1;
        }
        if(scale<0.68f){
            scale = 0.68f;
        }
        sv_content.setScaleX(scale);
        sv_content.setScaleY(scale);
    }

    /**实时更新主页显示数据*/
    private void Set_ChangeView_layoutShowTestDataFresh(){
        pieChart1.setTitle("2016年度房屋销售统计表");

        LayoutInflater mInFlater = LayoutInflater.from(this);
        if(pieDatas!=null && pieDatas.size()>0){
            int count = pieDatas.size();
            for(int i = 0; i<15; i++){
                if(i<count){
                    PieDataEntity entry = pieDatas.get(i);
                    final RelativeLayout view = (RelativeLayout)mInFlater.inflate(R.layout.item_persent, null);
                    View view_color = view.findViewById(R.id.view_color);
                    TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

                    tv_name.setText(entry.getName());
                    view_color.setBackgroundColor(entry.getColor());

                    ll_content2.addView(view);
                }else{
                    break;
                }
            }
            if(count > 15){
                for(int i = 15; i<30; i++){
                    if(i<count){
                        PieDataEntity entry = pieDatas.get(i);
                        final RelativeLayout view = (RelativeLayout)mInFlater.inflate(R.layout.item_persent, null);
                        View view_color = view.findViewById(R.id.view_color);
                        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

                        tv_name.setText(entry.getName());
                        view_color.setBackgroundColor(entry.getColor());

                        ll_content1.addView(view);
                    }else{
                        break;
                    }
                }
            }

            pieChart1.setDataList(pieDatas);
        }

    }






}
