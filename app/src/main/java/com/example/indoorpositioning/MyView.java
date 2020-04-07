package com.example.indoorpositioning;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

//Circle

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        initView();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    Paint paint = new Paint();
    Point point = new Point();
    int width;
    int height;

    int currentX;
    int currentY;

    //initial data
    void initView() {
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setAlpha(128);
        paint.setStrokeWidth(10);
        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;
        currentX = width / 2;
        currentY = height / 2;
        point.set(width / 2, height / 2);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}


