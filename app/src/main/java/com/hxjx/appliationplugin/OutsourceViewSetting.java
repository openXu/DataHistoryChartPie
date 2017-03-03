package com.hxjx.appliationplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxjx.appliationplugin.lib.util.Constant;
import com.hxjx.appliationplugin.lib.bean.PieDataEntity;

import java.util.ArrayList;

public class OutsourceViewSetting extends Activity implements View.OnClickListener {

    private TextView tv_1, tv_2, tv_3, tv_41, tv_42, tv_5;
    private ImageView iv_1, iv_2, iv_3, iv_41, iv_42, iv_5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.out_source_view_setting);

        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_2 = (TextView)findViewById(R.id.tv_2);
        tv_3 = (TextView)findViewById(R.id.tv_3);
        tv_41 = (TextView)findViewById(R.id.tv_41);
        tv_42 = (TextView)findViewById(R.id.tv_42);
        tv_5 = (TextView)findViewById(R.id.tv_5);

        iv_1 = (ImageView)findViewById(R.id.iv_1);
        iv_2 = (ImageView)findViewById(R.id.iv_2);
        iv_3 = (ImageView)findViewById(R.id.iv_3);
        iv_41 = (ImageView)findViewById(R.id.iv_41);
        iv_42= (ImageView)findViewById(R.id.iv_42);
        iv_5= (ImageView)findViewById(R.id.iv_5);

        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_41.setOnClickListener(this);
        tv_42.setOnClickListener(this);
        tv_5.setOnClickListener(this);

        setSelected(getIntent().getIntExtra("selectIndex", 1));
    }

    private void setSelected(int index){
        iv_1.setVisibility(View.GONE);
        iv_2.setVisibility(View.GONE);
        iv_3.setVisibility(View.GONE);
        iv_41.setVisibility(View.GONE);
        iv_42.setVisibility(View.GONE);
        iv_5.setVisibility(View.GONE);
        switch (index){
            case 1:
                iv_1.setVisibility(View.VISIBLE);
                break;
            case 2:
                iv_2.setVisibility(View.VISIBLE);
                break;
            case 3:
                iv_3.setVisibility(View.VISIBLE);
                break;
            case 41:
                iv_41.setVisibility(View.VISIBLE);
                break;
            case 42:
                iv_42.setVisibility(View.VISIBLE);
                break;
            case 5:
                iv_5.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onClick(View v) {

        ArrayList<PieDataEntity> pieDatas = new ArrayList<>();

        int selectIndex = 0;
        switch (v.getId()) {
            case R.id.tv_1:
                selectIndex = 1;
                pieDatas.add(new PieDataEntity("张三", 5, Constant.CHARTCOLOR[0]));
                break;
            case R.id.tv_2:
                selectIndex = 2;
                pieDatas.add(new PieDataEntity("张三", 0, Constant.CHARTCOLOR[0]));
                break;
            case R.id.tv_3:
                selectIndex = 3;
                pieDatas.add(new PieDataEntity("欧阳修", 0, Constant.CHARTCOLOR[0]));
                pieDatas.add(new PieDataEntity("西门庆", 0, Constant.CHARTCOLOR[1]));
                pieDatas.add(new PieDataEntity("南宫雪", 0, Constant.CHARTCOLOR[2]));
                pieDatas.add(new PieDataEntity("张飞", 0, Constant.CHARTCOLOR[3]));
                pieDatas.add(new PieDataEntity("赵云", 0, Constant.CHARTCOLOR[4]));
                pieDatas.add(new PieDataEntity("刘备", 0, Constant.CHARTCOLOR[5]));
                pieDatas.add(new PieDataEntity("王朝", 0, Constant.CHARTCOLOR[6]));
                pieDatas.add(new PieDataEntity("马汉", 0, Constant.CHARTCOLOR[7]));
                break;
            case R.id.tv_41:
                selectIndex = 41;
                pieDatas.add(new PieDataEntity("张三", 5, Constant.CHARTCOLOR[0]));
                pieDatas.add(new PieDataEntity("李四", 10, Constant.CHARTCOLOR[1]));
                pieDatas.add(new PieDataEntity("王五", 70, Constant.CHARTCOLOR[2]));
                pieDatas.add(new PieDataEntity("赵六", 30, Constant.CHARTCOLOR[3]));
                pieDatas.add(new PieDataEntity("田七", 60, Constant.CHARTCOLOR[4]));
                pieDatas.add(new PieDataEntity("欧阳修", 56, Constant.CHARTCOLOR[5]));
                pieDatas.add(new PieDataEntity("西门庆", 78, Constant.CHARTCOLOR[6]));
                pieDatas.add(new PieDataEntity("南宫雪", 96, Constant.CHARTCOLOR[7]));
                pieDatas.add(new PieDataEntity("张飞", 45, Constant.CHARTCOLOR[8]));
                pieDatas.add(new PieDataEntity("赵云", 25, Constant.CHARTCOLOR[9]));
                break;
            case R.id.tv_42:
                selectIndex = 42;
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
                break;
            case R.id.tv_5:
                selectIndex = 5;
                pieDatas.add(new PieDataEntity("张三", 1, Constant.CHARTCOLOR[0]));
                pieDatas.add(new PieDataEntity("李四", 1, Constant.CHARTCOLOR[1]));
                pieDatas.add(new PieDataEntity("王五", 1, Constant.CHARTCOLOR[2]));
                pieDatas.add(new PieDataEntity("赵六", 1, Constant.CHARTCOLOR[3]));
                pieDatas.add(new PieDataEntity("田七", 1, Constant.CHARTCOLOR[4]));
                pieDatas.add(new PieDataEntity("欧阳修",1, Constant.CHARTCOLOR[5]));
                pieDatas.add(new PieDataEntity("西门庆", 1, Constant.CHARTCOLOR[6]));
                pieDatas.add(new PieDataEntity("南宫雪", 1, Constant.CHARTCOLOR[7]));
                pieDatas.add(new PieDataEntity("张飞", 1, Constant.CHARTCOLOR[8]));
                pieDatas.add(new PieDataEntity("赵云", 1, Constant.CHARTCOLOR[9]));
                pieDatas.add(new PieDataEntity("刘备", 1, Constant.CHARTCOLOR[10]));
                pieDatas.add(new PieDataEntity("王朝", 1, Constant.CHARTCOLOR[11]));
                pieDatas.add(new PieDataEntity("马汉", 1, Constant.CHARTCOLOR[12]));
                pieDatas.add(new PieDataEntity("周杰伦",1, Constant.CHARTCOLOR[13]));
                pieDatas.add(new PieDataEntity("陈奕迅", 1, Constant.CHARTCOLOR[14]));
                pieDatas.add(new PieDataEntity("张建立", 1, Constant.CHARTCOLOR[15]));
                pieDatas.add(new PieDataEntity("郝建", 1, Constant.CHARTCOLOR[16]));
                pieDatas.add(new PieDataEntity("黄宏",1, Constant.CHARTCOLOR[17]));
                pieDatas.add(new PieDataEntity("周华健", 1, Constant.CHARTCOLOR[18]));
                pieDatas.add(new PieDataEntity("刘德华", 1, Constant.CHARTCOLOR[19]));
                pieDatas.add(new PieDataEntity("马德华", 1, Constant.CHARTCOLOR[20]));
                pieDatas.add(new PieDataEntity("六小龄童", 1, Constant.CHARTCOLOR[21]));
                pieDatas.add(new PieDataEntity("成龙", 1, Constant.CHARTCOLOR[22]));
                pieDatas.add(new PieDataEntity("吴京", 1, Constant.CHARTCOLOR[23]));
                pieDatas.add(new PieDataEntity("吴越", 1, Constant.CHARTCOLOR[24]));
                pieDatas.add(new PieDataEntity("李连杰", 1, Constant.CHARTCOLOR[25]));
                pieDatas.add(new PieDataEntity("李世明", 1, Constant.CHARTCOLOR[26]));
                pieDatas.add(new PieDataEntity("王二小", 1, Constant.CHARTCOLOR[27]));
                pieDatas.add(new PieDataEntity("邓超", 1, Constant.CHARTCOLOR[28]));
                pieDatas.add(new PieDataEntity("李小龙", 71, Constant.CHARTCOLOR[29]));
                break;
        }

        setSelected(selectIndex);
        Intent intent = new Intent(this, OutsourceControlMain.class);
        intent.putParcelableArrayListExtra("pieDatas", pieDatas);
        intent.putExtra("selectIndex",selectIndex);
        startActivity(intent);
        finish();

    }
}
