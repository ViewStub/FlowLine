package com.viewstub.flowline;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Lan Long on 2017/10/12.
 * email: 5789492@qq.com
 */

public class FlowLineView extends View {


    public FlowLineView(Context context) {
        super(context);
        init();
    }

    public FlowLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Path mPath = new Path();
    private Path mInnerPath = new Path();
    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;
    private Paint mPaint = new Paint();
    private Paint mBitmapPaint = new Paint();
    private final float roundWidth = 20;
    int rA = 1; //暂时不能变

    private void init() {

        CornerPathEffect cpe = new CornerPathEffect(20);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(false);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(cpe);
//        mPaint.setPathEffect(new DashPathEffect(new float[]{10,10},0));
//        mPaint.setPathEffect(new DiscretePathEffect(3.0f, 5.0f));
//        Path path = new Path();
//        path.addCircle(0, 0, 3, Path.Direction.CCW);
//        PathEffect pathEffect = new PathDashPathEffect(path, 12, 0, PathDashPathEffect.Style.ROTATE);
//        mPaint.setPathEffect(pathEffect);

        mBitmapPaint.setStrokeWidth(3);
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setStyle(Paint.Style.STROKE);
        mBitmapPaint.setPathEffect(cpe);

        makePolygon(new RectF(50, 50, 450, 450), mPath,6);
        makePolygon(new RectF(75, 75, 425, 425), mInnerPath,6);
        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        mBitmapCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(500, 500);
    }

    private float startLinePoint = 0f;
    private float endLinePoint = 0.5f;
    private float offsetLinePoint = 0f;

    int[] colorArray;
    float[] pathArray;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.BLACK);

        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
        canvas.drawPath(mInnerPath, mPaint);


//        canvas.drawCircle(250,250,200,mPaint);
//        canvas.drawCircle(250, 250, 1.7320508076f * 100, mPaint);

    }

//    //手动计算(无法满足任意旋转)
//    float Gen3 = 1.7320508076f * roundWidth;

//    private void makeRoundPolygon(RectF rect, Path path) {
//        float r = (rect.right - rect.left) / 2;
//        float mX = (rect.right + rect.left) / 2;
//        float my = (rect.top + rect.bottom) / 2;
//        for (int i = 0; i < 6; i++) {
//            float alpha = Double.valueOf(((2f / 6) * i - 0.5f*rA) * Math.PI).floatValue();//-0.5f 逆时针旋转90°
//            float nextX = mX + Double.valueOf(r * Math.cos(alpha)).floatValue();
//            float nextY = my + Double.valueOf(r * Math.sin(alpha)).floatValue();
//            //TODO 非标准角度 ERROR
//            int XR = (i  + rA -1) % 6;
//            switch (XR) {
//                case 0:
//                    path.moveTo(nextX - Gen3, nextY + roundWidth);
//                    path.quadTo(nextX, nextY, nextX + Gen3, nextY + roundWidth);
//                    break;
//                case 1:
//                    path.lineTo(nextX - Gen3, nextY - roundWidth);
//                    path.quadTo(nextX, nextY, nextX, nextY + roundWidth * 2);
//                    break;
//                case 2:
//                    path.lineTo(nextX, nextY - roundWidth * 2);
//                    path.quadTo(nextX, nextY, nextX - Gen3, nextY + roundWidth);
//                    break;
//                case 3:
//                    path.lineTo(nextX + Gen3, nextY - roundWidth);
//                    path.quadTo(nextX, nextY, nextX - Gen3, nextY - roundWidth);
//                    break;
//                case 4:
//                    path.lineTo(nextX + Gen3, nextY + roundWidth);
//                    path.quadTo(nextX, nextY, nextX, nextY - roundWidth * 2);
//                    break;
//                case 5:
//                    path.lineTo(nextX, nextY + roundWidth * 2);
//                    path.quadTo(nextX, nextY, nextX + Gen3, nextY - roundWidth);
//                    break;
//            }
//        }
//        path.close();
//    }

    private void makePolygon(RectF rect, Path path,int number) {
        float r = (rect.right - rect.left) / 2;
        float mX = (rect.right + rect.left) / 2;
        float my = (rect.top + rect.bottom) / 2;
        for (int i = 0; i < number; i++) {
            float alpha = Double.valueOf(((2f / number) * i - 0.5f*rA) * Math.PI).floatValue();
            float nextX = mX + Double.valueOf(r * Math.cos(alpha)).floatValue();
            float nextY = my + Double.valueOf(r * Math.sin(alpha)).floatValue();
            if (i == 0) {
                path.moveTo(nextX, nextY);
            } else {
                path.lineTo(nextX, nextY);
//                path.moveTo(nextX, nextY);
            }
        }
        path.close();
    }

    public void start() {
        ValueAnimator mAnimator = ValueAnimator.ofInt(360, 0);
        mAnimator.setDuration(10 * 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new TimeInterpolator() {

            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                startLinePoint = value / 360f;
                endLinePoint = startLinePoint + 0.5f;
                if (startLinePoint > 0.5f) {
                    offsetLinePoint = startLinePoint - 0.5f;
                    int splitColor = Color.argb((int) (255 * (offsetLinePoint / 0.5f)), 255, 0, 0);
                    colorArray =
                            new int[]{splitColor, 0x00FF0000, 0, 0, 0xFFFF0000, splitColor};
                    pathArray =
                            new float[]{0f, offsetLinePoint, offsetLinePoint, startLinePoint, startLinePoint, 1f};
                } else {
                    colorArray =
                            new int[]{0, 0, 0xFFFF0000, 0x00FF0000, 0, 0};
                    pathArray =
                            new float[]{0f, startLinePoint, startLinePoint, endLinePoint, endLinePoint, 1f};
                }

                SweepGradient mShader = new SweepGradient(250, 250, colorArray, pathArray);
                mBitmapPaint.setShader(mShader);
                mBitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                mBitmapCanvas.drawPath(mPath, mBitmapPaint);
                postInvalidate();
            }
        });
        mAnimator.start();
    }

}