package com.hxjx.appliationplugin.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hxjx.appliationplugin.lib.bean.PieDataEntity;
import com.hxjx.appliationplugin.lib.util.DensityUtil;
import com.hxjx.appliationplugin.lib.util.GlFontUtil;

import java.util.ArrayList;

public class SbChart extends View {

    private String TAG = "SbChart";

    private Paint mPaint, mTextPaint;

    private final int MAX_HEIGHT = 800;

    private float maxTextSize = 14;
    private float minTextSize = 3;
    private float maxMargin = 5;
    private float minMargin = 1;
    private float maxRect = 20;
    private float minRect = 3;

    private float textSize;
    private float margin;
    private float rect;
    private int lineNum;
    private float panding;
    private float lineMargin;
    private final float lineMarginInit = DensityUtil.dip2px(getContext(), 15);
    private final float pandingInit = DensityUtil.dip2px(getContext(), 20);

    private int oneLineCount = 15;
    private float oneHeight;
    private float textTop;

    private PointF pointStart;

    private ArrayList<PieDataEntity> mDataList;
    public SbChart(Context context) {
        super(context);
        init(context);
    }

    public SbChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SbChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        maxMargin = DensityUtil.dip2px(context, maxMargin);
        minMargin = DensityUtil.dip2px(context, minMargin);
        maxRect = DensityUtil.dip2px(context, maxRect);
        minRect = DensityUtil.dip2px(context, minRect);
        textSize = maxTextSize;
        margin = maxRect;
        rect = maxRect;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(textSize);
    }

    public void setDataList(ArrayList<PieDataEntity> dataList){
        this.mDataList = dataList;
        lineNum = dataList.size()>oneLineCount?2:1;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int allWidth = MeasureSpec. getSize(widthMeasureSpec);
        int allHight = MeasureSpec. getSize(heightMeasureSpec);
        if(allWidth<=0 || allHight<=0)
            return;
        Log.i(TAG, "色标建议宽高："+allWidth+"*"+allHight);
        int min = allHight>allWidth?allWidth:allHight;
        float scale = (min*1.0f)/(MAX_HEIGHT*1.0f);
        Log.i(TAG, "最大高度为："+MAX_HEIGHT+" 当前宽高最小值："+min+"  缩放值："+scale);
        textSize = minTextSize+(maxTextSize-minTextSize)*scale;
        margin = minMargin+(maxMargin-minMargin)*scale;
        rect = minRect+(maxRect-minRect)*scale;
        panding = pandingInit*scale;
        lineMargin = lineMarginInit*scale;
        Log.i(TAG, "缩放后字体："+textSize+" 间距："+margin+"  方块："+rect);
        if(mDataList==null || mDataList.size()<=0){
            setMeasuredDimension(10, 10);
            return;
        }
        mTextPaint.setTextSize(textSize);

        float maxW1 = 0;
        float maxW2 = 0;
        for(int i = 0; i <mDataList.size() ;i++){
            PieDataEntity entry = mDataList.get(i);
            float l = GlFontUtil.getFontlength(mTextPaint, entry.getName());
            if(i<oneLineCount){
                //第一列
                maxW1 = maxW1>l?maxW1:l;
            }else{
                //第二列
                maxW2 = maxW2>l?maxW2:l;
            }
        }
        Log.e(TAG, "lineMargin="+lineMargin+"  rect="+rect+"   margin="+margin+"  maxW1="+maxW1);
        float width = panding*2+
                rect + margin + maxW1 +
                (lineNum==2?(lineMargin+rect + margin + maxW2):0);
        float h = GlFontUtil.getFontHeight(mTextPaint);
        oneHeight = h>rect?h:rect;
        textTop = (rect-h)/2.0f;
        textTop = textTop<0?(textTop*-1):textTop;
        float height = (oneHeight + margin)*(lineNum==1?mDataList.size():oneLineCount)-margin;
        pointStart = new PointF(panding+rect + margin + maxW1+ lineMargin, 0);
        Log.i(TAG, "色标宽高："+width+"*"+height);
        setMeasuredDimension((int)width, (int)height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mDataList==null || mDataList.size()<=0)
            return;

        for(int i = 0; i <mDataList.size() ;i++){
            PieDataEntity entry = mDataList.get(i);
            float tl = GlFontUtil.getFontlength(mTextPaint, entry.getName());
            mPaint.setColor(entry.getColor());
            if(i<oneLineCount){
                //第一列
                canvas.drawRect(panding, (oneHeight+margin)*i, panding+rect,
                        (oneHeight+margin)*i+rect, mPaint);
                //文字绘制baseLine为表格高度+间距+基准
                canvas.drawText(entry.getName(),panding + rect + margin,
                        (oneHeight+margin)*i+textTop+GlFontUtil.getFontLeading(mTextPaint),mTextPaint);
            }else{
                //第二列
                canvas.drawRect(pointStart.x, (oneHeight+margin)*(i-15), pointStart.x+rect,
                        (oneHeight+margin)*(i-15)+rect, mPaint);
                canvas.drawText(entry.getName(),pointStart.x+rect + margin,
                        (oneHeight+margin)*(i-15)+textTop+GlFontUtil.getFontLeading(mTextPaint) ,mTextPaint);
            }
        }

    }


}
