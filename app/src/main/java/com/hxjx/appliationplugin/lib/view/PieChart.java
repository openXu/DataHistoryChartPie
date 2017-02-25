package com.hxjx.appliationplugin.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hxjx.appliationplugin.lib.bean.PieDataEntity;
import com.hxjx.appliationplugin.lib.util.CalculateUtil;
import com.hxjx.appliationplugin.lib.util.Constant;
import com.hxjx.appliationplugin.lib.util.DensityUtil;
import com.hxjx.appliationplugin.lib.util.GlFontUtil;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL;

public class PieChart extends View {

    private String TAG = "PieChart";
    /**视图的宽和高*/
    private int mTotalWidth, mTotalHeight;
    /**绘制区域的半径*/
    private float mRadius;
    private float outLineW = 10;        //外侧线长
    private float textOutSpac = 2;     //外侧字与线得距离
    private float textInnerSpac = 15;   //内侧字与边得距离
    private float maxTextL;             //最长得字长度
    private float textLead, textH;             //最长得字长度


    private Paint mPaint, mLinePaint,mTextPaint;

    /**扇形的绘制区域*/
    private RectF mRectF;

    private ArrayList<PieDataEntity> mDataList;
    /**所有的数据加起来的总值*/
    private float mTotalValue;
    /**起始角度的集合*/
    private float[] angles;

    /**点击监听*/
    private OnItemPieClickListener mOnItemPieClickListener;

    public void setOnItemPieClickListener(OnItemPieClickListener onItemPieClickListener) {
        mOnItemPieClickListener = onItemPieClickListener;
    }

    public interface OnItemPieClickListener {
        void onClick(int position);
    }
    public PieChart(Context context) {
        super(context);
        init(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRectF = new RectF();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.BLACK);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(13);

        outLineW = DensityUtil.dip2px(context, outLineW);
        textOutSpac = DensityUtil.dip2px(context, textOutSpac);
        textInnerSpac = DensityUtil.dip2px(context, textInnerSpac);
        maxTextL = GlFontUtil.getFontlength(mTextPaint, "00.0%");
        textLead = GlFontUtil.getFontLeading(mTextPaint);
        textH = GlFontUtil.getFontHeight(mTextPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w - getPaddingLeft() - getPaddingRight();
        mTotalHeight = h - getPaddingTop() - getPaddingBottom();

        //外围空间，标记占比
        float outSpec = outLineW*2.0f+textOutSpac+maxTextL;

        mRadius = (Math.min(mTotalWidth,mTotalHeight))/2 - outSpec;
        Log.i(TAG, "饼状图宽高："+mTotalWidth+" * "+mTotalHeight+" ，半径："+mRadius);
        mRectF.left = -mRadius;
        mRectF.top = -mRadius;
        mRectF.right = mRadius;
        mRectF.bottom = mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置原点位置
        canvas.translate(mTotalWidth/2,(mTotalHeight)/2);

        if(Constant.debug){
            //测试边界
            mPaint.setColor(Color.YELLOW);
            canvas.drawRect(-mRadius-outLineW*2-textOutSpac-maxTextL,
                    -mRadius-outLineW*2-textOutSpac-maxTextL,
                    mRadius+outLineW*2+textOutSpac+maxTextL,
                    mRadius+outLineW*2+textOutSpac+maxTextL, mPaint);
            //外侧字边界
            mPaint.setColor(Color.GRAY);
            canvas.drawRect(-mRadius-outLineW*2-textOutSpac,
                    -mRadius-outLineW*2-textOutSpac,
                    mRadius+outLineW*2+textOutSpac,
                    mRadius+outLineW*2+textOutSpac, mPaint);
            //外侧长线
            mPaint.setColor(Color.GREEN);
            canvas.drawRect(-mRadius-outLineW*2,
                    -mRadius-outLineW*2,
                    mRadius+outLineW*2,
                    mRadius+outLineW*2, mPaint);
            //外侧短线
            mPaint.setColor(Color.RED);
            canvas.drawRect(-mRadius-outLineW,
                    -mRadius-outLineW,
                    mRadius+outLineW,
                    mRadius+outLineW, mPaint);
            //外侧字
            mPaint.setColor(Color.DKGRAY);
            canvas.drawRect(-mRadius-textOutSpac-maxTextL,
                    -mRadius-textOutSpac-maxTextL,
                    mRadius+textOutSpac+maxTextL,
                    mRadius+textOutSpac+maxTextL, mPaint);
            //外侧字
            mPaint.setColor(Color.CYAN);
            canvas.drawRect(-mRadius-textOutSpac,
                    -mRadius-textOutSpac,
                    mRadius+textOutSpac,
                    mRadius+textOutSpac, mPaint);
            //外侧
            mPaint.setColor(Color.WHITE);
            canvas.drawRect(-mRadius, -mRadius, mRadius, mRadius, mPaint);
        }


        //绘制饼图的每块区域
        drawPiePath(canvas);
    }

    /**
     * 绘制饼图的每块区域 和文本
     * @param canvas
     */
    private void drawPiePath(Canvas canvas) {
        Log.i(TAG, "宽高："+getWidth()+" * "+getHeight());
        //没有数据的情况
        if(mDataList==null ||mDataList.size()<=0 ||
                (mDataList.size()==1 && mDataList.get(0).getValue()<=0)||
                mTotalValue <=0){

            mLinePaint.setStyle(Paint.Style.STROKE);
            //绘制最外层黑圈
            canvas.drawArc(mRectF,0,360,true,mLinePaint);
            //绘制线
            canvas.drawLine(0,0,mRadius,0,mLinePaint);
            //绘制0%
            //文字绘制baseLine为表格高度+间距+基准
            canvas.drawText("0%",mRadius+textOutSpac,textLead- textH/2,mTextPaint);
            return;
        }

        /*一条数据沾满全部*/
        if(mDataList.size()==1 && mDataList.get(0).getValue()>0){
//            mPaint.setColor(Color.argb(255, 0, 112, 192));
            mPaint.setColor(mDataList.get(0).getColor());
            canvas.drawArc(mRectF,0,360,true,mPaint);

            //文字绘制baseLine为表格高度+间距+基准
            canvas.drawText("100%",mRadius-textInnerSpac-mTextPaint.measureText("100%")/2,
                    textLead- textH/2,mTextPaint);
            return;
        }

        //起始地角度
        float startAngle = 0;
        float textH = GlFontUtil.getFontHeight(mTextPaint);  //文字高度
        float lineH = GlFontUtil.getFontLeading(mTextPaint);
        PointF loatoutP = null;   //上一个数据是否标在外 面
        boolean firstIsOut = false;
        float lastValueB = 0;
        for(int i = 0;i<mDataList.size();i++){
            float sweepAngle = mDataList.get(i).getValue()/mTotalValue*360;//每个扇形的角度
            float valueB = mDataList.get(i).getValue()/mTotalValue*1.0f;
            mPaint.setColor(mDataList.get(i).getColor());
            //*******直接绘制扇形***********
            canvas.drawArc(mRectF,startAngle,sweepAngle,true,mPaint);

            //提供精确的小数位四舍五入处理。
            float res = mDataList.get(i).getValue() / mTotalValue * 100;
            double resToRound = CalculateUtil.round(res,1);
            float textW = GlFontUtil.getFontlength(mTextPaint, resToRound+"%");
            //判断字写在里面还是外面
            float pxs = (float) ((mRadius-textInnerSpac)* Math.cos(Math.toRadians(startAngle)));
            float pys = (float) ((mRadius-textInnerSpac)* Math.sin(Math.toRadians(startAngle)));
            float pxt = (float) ((mRadius-textInnerSpac)* Math.cos(Math.toRadians(startAngle+sweepAngle)));
            float pyt = (float) ((mRadius-textInnerSpac)* Math.sin(Math.toRadians(startAngle+sweepAngle)));
            double dis = Math.sqrt((pyt-pys)*(pyt-pys) + (pxt-pxs)*(pxt-pxs));
            float centerX, centerY;
            if(dis > textW){
                loatoutP = null;
                //写在里面
                centerX = (float) ((mRadius-textInnerSpac)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                centerY = (float) ((mRadius-textInnerSpac)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                centerX = centerX-textW/2;
                centerY = centerY-textH/2 + lineH;
                angles[i] = startAngle;
                startAngle += sweepAngle;

                if(startAngle % 360.0 < 30.0){
//                    Log.e(TAG, resToRound+"%"+"内左");
                    canvas.drawText(resToRound+"%",centerX - textInnerSpac/3, centerY, mTextPaint);
                } else if(startAngle % 360.0 < 150.0){
//                    Log.e(TAG, resToRound+"%"+"下正常");
                    canvas.drawText(resToRound+"%",centerX, centerY, mTextPaint);
                }else if(startAngle % 360.0 <= 210.0){
//                    Log.e(TAG, resToRound+"%"+"内右");
                    canvas.drawText(resToRound+"%",centerX + textInnerSpac/3, centerY, mTextPaint);
                }else if(startAngle % 360.0 <= 330.0){
//                    Log.e(TAG, resToRound+"%"+"上正常");
                    canvas.drawText(resToRound+"%",centerX, centerY, mTextPaint);
                }else{
//                    Log.e(TAG, resToRound+"%"+"内左");
                    canvas.drawText(resToRound+"%",centerX - textInnerSpac/3, centerY, mTextPaint);
                }

            }else{
                if(i == 0){
                    firstIsOut = true;
                }

                if((i == mDataList.size()-1 && firstIsOut)){
                    //最后一个避免遮盖
                    centerX = (float) ((mRadius+textOutSpac)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                    centerY = (float) ((mRadius+textOutSpac)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));

                    centerX = centerX-textW/2;
                    centerY = centerY-textH/2 + lineH;

                    angles[i] = startAngle;
                    startAngle += sweepAngle;

                    canvas.drawText(resToRound+"%",centerX, centerY, mTextPaint);

                }else{

                    //确定直线的起始和结束的点的位置
                    pxs = (float) (mRadius* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                    pys = (float) (mRadius* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));

                    if(loatoutP !=null){
                        if(lastValueB!=0 && lastValueB<1){
                            //上一个也是在外面 ，并且很小
                            if(valueB <1){
                                //这个也小
                                pxt = (float) ((mRadius+outLineW*2)* Math.cos(Math.toRadians(startAngle+sweepAngle)));
                                pyt = (float) ((mRadius+outLineW*2)* Math.sin(Math.toRadians(startAngle+sweepAngle)));
                                centerX = (float) ((mRadius+outLineW*2+textOutSpac+maxTextL/2)* Math.cos(Math.toRadians(startAngle+sweepAngle)));
                                centerY = (float) ((mRadius+outLineW*2+textOutSpac+maxTextL/2)* Math.sin(Math.toRadians(startAngle+sweepAngle)));
                            }else{
                                //这个大一点
                                pxt = (float) ((mRadius+outLineW)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                                pyt = (float) ((mRadius+outLineW)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                                centerX = (float) ((mRadius+outLineW+textOutSpac+maxTextL/2)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                                centerY = (float) ((mRadius+outLineW+textOutSpac+maxTextL/2)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                            }
                        }else{
                            //上一个比较大
                            pxt = (float) ((mRadius+outLineW)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                            pyt = (float) ((mRadius+outLineW)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                            centerX = (float) ((mRadius+outLineW+textOutSpac+maxTextL/2)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                            centerY = (float) ((mRadius+outLineW+textOutSpac+maxTextL/2)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                        }
                    }else{
                        pxt = (float) ((mRadius+outLineW)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                        pyt = (float) ((mRadius+outLineW)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                        centerX = (float) ((mRadius+outLineW+textOutSpac+maxTextL/2)* Math.cos(Math.toRadians(startAngle+sweepAngle/2)));
                        centerY = (float) ((mRadius+outLineW+textOutSpac+maxTextL/2)* Math.sin(Math.toRadians(startAngle+sweepAngle/2)));
                    }

//                Log.w(TAG, "中点："+centerX+"*"+centerY);
                    centerX = centerX-textW/2;
                    centerY = centerY-textH/2 + lineH;

                    angles[i] = startAngle;
                    startAngle += sweepAngle;

                    //绘制线和文本
                    canvas.drawLine(pxs,pys,pxt,pyt,mLinePaint);
//                Log.w(TAG, "角度："+startAngle+"   startAngle % 360.0="+startAngle % 360.0);
                    if(startAngle % 360.0 < 70.0){
//                    Log.e(TAG, resToRound+"%"+"正常");
                        canvas.drawText(resToRound+"%",centerX, centerY, mTextPaint);
                    } else if(startAngle % 360.0 < 110.0){
//                    Log.e(TAG, resToRound+"%"+"下");
                        canvas.drawText(resToRound+"%",centerX, centerY-textOutSpac /2, mTextPaint);
                    }else if(startAngle % 360.0 <= 250.0){
//                    Log.e(TAG, resToRound+"%"+"正常");
                        canvas.drawText(resToRound+"%",centerX, centerY, mTextPaint);
                    }else if(startAngle % 360.0 <= 290.0){
//                    Log.e(TAG, resToRound+"%"+"上");
                        canvas.drawText(resToRound+"%",centerX, centerY+textOutSpac /2, mTextPaint);
                    }else{
//                    Log.e(TAG, resToRound+"%"+"正常");
                        canvas.drawText(resToRound+"%",centerX, centerY, mTextPaint);
                    }

                    loatoutP = new PointF(centerX,centerY);
                }
            }
            lastValueB = valueB;
        }
    }

    public void setDataList(ArrayList<PieDataEntity> dataList){
        this.mDataList = dataList;
        mTotalValue = 0;
        for(PieDataEntity pieData : mDataList){
            mTotalValue += pieData.getValue();
        }
        angles = new float[mDataList.size()];
        invalidate();
    }


    public ArrayList<PieDataEntity> getDataList(){
        return this.mDataList;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mOnItemPieClickListener!=null){
            mOnItemPieClickListener.onClick(1);
        }
        /*switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX()-(mTotalWidth/2);
                float y = event.getY()-(mTotalHeight/2);
                float touchAngle = 0;
                if (x<0&&y<0){  //2 象限
                    touchAngle += 180;
                }else if (y<0&&x>0){  //1象限
                    touchAngle += 360;
                }else if (y>0&&x<0){  //3象限
                    touchAngle += 180;
                }
                //Math.atan(y/x) 返回正数值表示相对于 x 轴的逆时针转角，返回负数值则表示顺时针转角。
                //返回值乘以 180/π，将弧度转换为角度。
                touchAngle += Math.toDegrees(Math.atan(y/x));
                if (touchAngle<0){
                    touchAngle = touchAngle+360;
                }
                float touchRadius = (float) Math.sqrt(y*y+x*x);
                if (touchRadius < mRadius){
                    position = -Arrays.binarySearch(angles,(touchAngle))-1;
                    invalidate();
                    if(mOnItemPieClickListener!=null){
                        mOnItemPieClickListener.onClick(position-1);
                    }
                }
                break;
        }*/
        return super.onTouchEvent(event);
    }
}
