package com.example.wolf.customview01;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.renderscript.Type;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

/**
 * Created by wolf on 2016/9/21.
 */
public class CustomView extends View {
    //文本
    private String mTitleText;
    //字体大小
    private int mTitleSize;
    //文本颜色
    private int mTextColor;
    //画笔
    private Paint mPaint;
    //文本范围
    private Rect mBound;

    public CustomView(Context context) {
        this(context,null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建TypedArray封装所有xml中控件的配置参数
        TypedArray typedArray =  context.obtainStyledAttributes(attrs,R.styleable.CustomView,defStyleAttr,0);
        //提取参数值，并赋值给成员变量
        mTitleText = typedArray.getString(R.styleable.CustomView_titleText);
        mTextColor = typedArray.getColor(R.styleable.CustomView_titleColor, Color.BLACK);
        mTitleSize = typedArray.getDimensionPixelSize(R.styleable.CustomView_titleSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,18,getResources().getDisplayMetrics()));
        //释放TypedArray对象，保证内存不泄露
        typedArray.recycle();
        //初始化画笔参数
        mPaint = new Paint();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTitleSize);
        mBound = new Rect();
        //获取文本大小封装在mBound中
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
        //定义此控件的点击事件效果，点击一次把控件中的四位长度的数字内容随机切换
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = this.randomText();
                //调用此方法会重新调用onDraw();
                postInvalidate();
            }

            //返回一个随机的四位数
            private String randomText() {
                StringBuffer sb = new StringBuffer();
                Random r = new Random();
                for(int i = 0; i < 4; i++){
                    sb.append(r.nextInt(10));
                }
                return sb.toString();
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width = mBound.width()+getPaddingLeft()+getPaddingRight();
        }

        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            height = mBound.height()+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        canvas.drawText(mTitleText,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);
    }
}
