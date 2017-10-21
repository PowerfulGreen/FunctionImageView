package com.greenapp.func;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Administrator on 2017/10/14 0014.
 */

public class FunctionView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private float OX;
    private float OY;
    private float one_x;
    private float one_y;
    private int CountOfX;
    private int CountOfY;
    private float last_touch_x, last_touch_y;
    private float x1,x2;
    private FunctionFormula formula;

    public FunctionView(Context context) {
        super(context);
    }

    public FunctionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FunctionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(int countOfX,int countOfY,FunctionFormula formula){
        CountOfX=countOfX;
        CountOfY=countOfY;
        this.formula=formula;
        getHolder().addCallback(this);
        setOnTouchListener(this);
    }

    public void changeFunction(FunctionFormula formula){
        this.formula=formula;
        Canvas c=getHolder().lockCanvas();
        c=drawXYO(c,OX,OY);
        c=drawFunction(c,x1,x2);
        getHolder().unlockCanvasAndPost(c);
    }

    private void init() {
        OX = getWidth() / 2;
        OY = getHeight() / 2;
        one_x = getWidth() / (2 * CountOfX);
        one_y = getHeight() / (2 * CountOfY);
        last_touch_x = OX;
        last_touch_y = OY;
        x1=0-CountOfX;
        x2=CountOfX;
    }

    private Canvas drawFunction(Canvas c, float x1, float x2) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);

        float last_x=toImageX(x1);
        float last_y=toImageY(formula.getFunctionValue(x1));
        for (float i = x1; i < x2; i += 0.5) {
            float x = toImageX(i);
            float y = toImageY(formula.getFunctionValue(i));
            c.drawLine(last_x, last_y, x, y, paint);
            last_x = x;
            last_y = y;
        }

        return c;
    }

    private Canvas drawXYO(Canvas c, float OX, float OY) {
        Paint paint = new Paint();
        paint.setStrokeWidth(5);

        //清空
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        c.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        c.drawColor(Color.WHITE);

        //绘制原点
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        c.drawText("O", OX + 20, OY + 40, paint);
        c.drawPoint(OX, OY, paint);

        //绘制Y轴
        paint.setColor(Color.RED);
        c.drawLine(OX, 0, OX, getHeight(), paint);

        //绘制X轴
        paint.setColor(Color.GREEN);
        c.drawLine(0, OY, getWidth(), OY, paint);
        return c;
    }

    private float toImageX(float x) {
        return OX + x * one_x;
    }

    private float toImageY(float y) {
        return OY - y * one_y;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init();
        Canvas c = holder.lockCanvas();
        c = drawXYO(c, OX, OY);
        c = drawFunction(c, -CountOfX, CountOfX);
        holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                last_touch_x=event.getX();
                last_touch_y=event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                OX=OX+event.getX()-last_touch_x;
                OY=OY-last_touch_y+event.getY();
                float moveValue=(last_touch_x-event.getX())/one_x;
                x1=x1+moveValue;
                x2=x2+moveValue;
                Canvas c=drawXYO(getHolder().lockCanvas(),OX, OY);
                c=drawFunction(c,x1,x2);
                getHolder().unlockCanvasAndPost(c);
                last_touch_x=event.getX();
                last_touch_y=event.getY();
                break;
        }
        return true;
    }
}
