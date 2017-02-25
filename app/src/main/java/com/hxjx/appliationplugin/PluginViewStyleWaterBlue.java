package com.hxjx.appliationplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.hxjx.appliationplugin.lib.bean.PieDataEntity;
import com.hxjx.appliationplugin.lib.util.Constant;
import com.hxjx.appliationplugin.lib.view.CustomLayout;
import com.hxjx.appliationplugin.lib.view.PieChart;
import com.hxjx.appliationplugin.lib.view.SbChart;
import com.hxjx.appliationplugin.lib.view.SizeAlertDialog;
import com.hxjx.appliationplugin.lib.view.TestAlertDialog;

import java.util.ArrayList;

public class PluginViewStyleWaterBlue extends Activity {


    private int sbHeightInit=-1;
    private int sbWidthInit=-1;
    private CustomLayout customLayout;
    private PieChart pieChart;
    private SbChart sbChart;

    private ArrayList<PieDataEntity> pieDatas;
    private int selectIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_view_style_water_blue);
        customLayout = (CustomLayout) findViewById(R.id.custom);
        pieChart = (PieChart) findViewById(R.id.chart);
        sbChart = (SbChart) findViewById(R.id.sbChart);

        pieDatas = getIntent().getParcelableArrayListExtra("pieDatas");
/*

        ll_child3.setSizeChangeListenr(new MyLineLayout.SizeChangeListenr() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scaleContent(w, h);
            }
        });
*/

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
        customLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sbHeightInit==-1){
                    sbHeightInit=customLayout.getChildAt(2).getMeasuredHeight();
                }
                if(sbWidthInit==-1){
                    sbWidthInit=customLayout.getChildAt(2).getMeasuredWidth();
                }
                Log.e("openxu","色标初始宽高："+sbWidthInit+"*"+sbHeightInit);
                Set_onClick_SettingDialog();
            }
        });

    }


    /**查询本地数据*/
    private void Send_MainLayoutDataSourceQuery(){
       ArrayList<PieDataEntity> list =  pieChart.getDataList();
    }
    /**弹出设置界面*/
    private void Set_onClick_SettingDialog(){
        final TestAlertDialog textDialog = new TestAlertDialog(PluginViewStyleWaterBlue.this);
        textDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textDialog.dismiss();
                switch (v.getId()){
                    case R.id.btn_size:
                        final SizeAlertDialog sizeDialog = new SizeAlertDialog(PluginViewStyleWaterBlue.this);
                        sizeDialog.setOnClickListener(new SizeAlertDialog.OnClickListener() {
                            @Override
                            public void onSizeChange(int w, int h) {
                                sizeDialog.dismiss();
                                Set_ChangeView_layoutShowDimension(w, h);
                            }
                        });
                        break;
                    case R.id.btn_test:
                        Intent intent = new Intent(PluginViewStyleWaterBlue.this, OutsourceViewSetting.class);
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)customLayout.getLayoutParams();
        params.width = w;
        params.height = h;
        customLayout.setLayoutParams(params);
    }

    /**实时更新主页显示数据*/
    private void Set_ChangeView_layoutShowTestDataFresh(){
        sbChart.setDataList(pieDatas);
        pieChart.setDataList(pieDatas);
    }


}
