package com.hxjx.appliationplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hxjx.appliationplugin.lib.bean.PieDataEntity;

import java.util.ArrayList;

public class OutsourceControlMain extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_source_control_main);

        Intent data = getIntent();
        ArrayList<PieDataEntity> pieDatas = data.getParcelableArrayListExtra("pieDatas");
        int selectIndex = data.getIntExtra("selectIndex",42);

        Intent intent = new Intent(this, PluginViewStyleWaterBlue.class);
        intent.putParcelableArrayListExtra("pieDatas", pieDatas);
        intent.putExtra("selectIndex", selectIndex);
        startActivity(intent);
        finish();

    }
}
