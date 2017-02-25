package com.hxjx.appliationplugin.lib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hxjx.appliationplugin.lib.util.DensityUtil;

/**
 * author : Joker
 * create at : 2017/2/24 16:52
 * project : DataHistoryChartPie
 * class name : CustomLayout
 * version : 1.0
 * class describe：
 */
public class CustomLayout extends ViewGroup {
    private static final String TAG = "CustomLayout";

    //标题间距
    private int TITLE_SPACE = 15;
    private int TITLE_MARGIN = 40;
    private int titleHight, titleWidth;

    private PointF pointTitleStart, pointPieStart, pointSbStart;
    private PointF pointTitleEnd, pointPieEnd, pointSbEnd;

    public CustomLayout(Context context) {
        super(context);
        init();
    }
    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }
    public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        TITLE_SPACE = DensityUtil.dip2px(getContext(), TITLE_SPACE);
        TITLE_MARGIN = DensityUtil.dip2px(getContext(), TITLE_MARGIN);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @SuppressLint("NewApi") @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {
        // 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec. getMode(widthMeasureSpec);
        int heightMode = MeasureSpec. getMode(heightMeasureSpec);
        int allWidth = MeasureSpec. getSize(widthMeasureSpec);
        int allHight = MeasureSpec. getSize(heightMeasureSpec);
        int shengyuH = allHight;

        //测量标题
        measureChild(getChildAt(0), widthMeasureSpec, heightMeasureSpec);
        titleWidth = getChildAt(0).getMeasuredWidth();
        titleHight = getChildAt(0).getMeasuredHeight();

        shengyuH -= (titleHight+TITLE_SPACE);

        //测量色标
        measureChild(getChildAt(2), widthMeasureSpec, heightMeasureSpec);
        //获取色标宽
        int sbW = getChildAt(2).getMeasuredWidth();
        int sbH = getChildAt(2).getMeasuredHeight();

        //获取饼状图宽高
        int pieW = allWidth-sbW;
        //饼状图高度不能比宽度大，避免标题被顶上去
        int pieH = Math.min(shengyuH,pieW);

        //测量饼状图
        measureChild(getChildAt(1), pieW, pieH);
        //测量色标
//        measureChild(getChildAt(2), sbW, sbH);

        int titleStartY = allHight/2-(pieH+titleHight+TITLE_SPACE)/2;
        int titleEndY = allHight/2-(pieH+titleHight+TITLE_SPACE)/2+titleHight;

        pointTitleStart = new PointF(TITLE_MARGIN, titleStartY);
        pointTitleEnd = new PointF(TITLE_MARGIN+titleWidth, titleEndY);
        pointPieStart = new PointF(0, titleEndY+TITLE_SPACE);
        pointPieEnd = new PointF(pieW, titleEndY+TITLE_SPACE+pieH);

        pointSbStart = new PointF(pieW, titleEndY+TITLE_SPACE+(pieH/2-sbH/2));
        pointSbEnd = new PointF(allWidth, titleEndY+TITLE_SPACE+pieH - (pieH/2-sbH/2));
        Log.v(TAG, "容器获得建议宽高："+allWidth+"*"+allHight);
        Log.v(TAG, "饼状图宽高："+pieW+"*"+pieH);
        Log.v(TAG, "色标宽高："+sbW+"*"+sbH);

        Log.v(TAG, "标题高度："+titleHight+"  间距："+TITLE_SPACE);
        Log.v(TAG, "标题范围："+pointTitleStart.x+"*"+pointTitleStart.y + pointTitleEnd.x+"*"+pointTitleEnd.y);
        Log.v(TAG, "饼状图范围："+pointPieStart.x+"*"+pointPieStart.y+pointPieEnd.x+"*"+pointPieEnd.y);
        Log.v(TAG, "色标始范围："+pointSbStart.x+"*"+pointSbStart.y+pointSbEnd.x+"*"+pointSbEnd.y);

        setMeasuredDimension(allWidth, allHight);
    }

    /**
     * 为所有的子控件摆放位置.
     */
    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom) {
        View titleTv = getChildAt(0);
        //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
        titleTv.layout((int)pointTitleStart.x, (int)pointTitleStart.y, (int)pointTitleEnd.x, (int)pointTitleEnd.y);
        View pieView = getChildAt(1);
        pieView.layout((int)pointPieStart.x, (int)pointPieStart.y, (int)pointPieEnd.x, (int)pointPieEnd.y);
        View sbView = getChildAt(2);
        sbView.layout((int)pointSbStart.x, (int)pointSbStart.y, (int)pointSbEnd.x, (int)pointSbEnd.y);
    }
}
