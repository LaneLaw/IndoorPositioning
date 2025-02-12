package com.example.indoorpositioning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.indoorpositioning.placeInfo.Place;

import java.util.ArrayList;

public class MapView extends View implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private Paint paint;
    private float scale = 1f;
    private float dx = 0, dy = 0;
    int currentX;
    int currentY;
    int width;
    int height;
    Drawable drawable;
    int x1,y1,x2,y2;
    int hide = 0;
    ArrayList <Integer> pathInfo = null;
    Place [] p = null;
    Point point = new Point();

    private float mFirstX, mFirstY, mSecondX, mSecondY;
    private int mOldCounts;

    private GestureDetector mGestureDetector;

    public MapView(Context context) {
        super(context);
        paint = new Paint();
        mGestureDetector = new GestureDetector(this);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        mGestureDetector = new GestureDetector(this);
        initView();
    }
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
        drawable = getContext().getResources().getDrawable(R.drawable.f1);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        canvas.save();


        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();//Drawable图片类型转换成BitmapDrawable，返回是Bitmap
        canvas.scale(scale, scale);
        canvas.translate(dx, dy);

        canvas.drawBitmap(bitmap, 0, 250, new Paint());
        if(hide == 1){ canvas.drawCircle(currentX, currentY, 10, paint);}

        if(p == null || pathInfo == null || pathInfo.size() == 0){}else {
            for (int i = 0; i < pathInfo.size() - 1; i++) {
                canvas.drawLine(p[pathInfo.get(i)].getX(), p[pathInfo.get(i)].getY(), p[pathInfo.get(i + 1)].getX(), p[pathInfo.get(i + 1)].getY(), paint);
            }
        }
        canvas.restore();
    }
    protected void setpoint(int x, int y) {
        currentX = x;
        currentY = y;
        invalidate();
    }
    protected void hidePoint(){
        hide = 0;
    }
    protected void viewPoint(){
        hide = 1;
    }

    protected void setDrawable(Drawable x){
        drawable = x;
        invalidate();
    }

    protected void drawPathL1(ArrayList<Integer> x, Place [] y){
        this.pathInfo = x;
        this.p = y;
        invalidate();
    }
    protected void drawPathL2(ArrayList<Integer> x, Place [] y){
        this.pathInfo = x;
        this.p = y;
        invalidate();
    }
    protected void setRoute(int x1, int y1,int x2, int y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        invalidate();
    }
    private Bitmap getBitmap(Bitmap bitmap) {

        int width = bitmap.getWidth();

        int height = bitmap.getHeight();

        int min = Math.min(width, height);

        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, (width - min) / 2, (height - min) / 2, min, min);

        Bitmap bitmap2 = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap2);

        Paint paint = new Paint();

        paint.setShader(new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        int i = min / 2;

        canvas.drawCircle(i, i, i, paint);

        return bitmap2;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOldCounts = 1;
                mFirstX = event.getX();
                mFirstY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE: {
                float fFirstX = event.getX();
                float fFirstY = event.getY();
                int nCounts = event.getPointerCount();
                if (1 == nCounts) {
                    mOldCounts = 1;
                } else if (1 == mOldCounts) {
                    mSecondX = event.getX(event.getPointerId(nCounts - 1));
                    mSecondY = event.getY(event.getPointerId(nCounts - 1));
                    mOldCounts = nCounts;
                } else {
                    float fSecondX = event
                            .getX(event.getPointerId(nCounts - 1));
                    float fSecondY = event
                            .getY(event.getPointerId(nCounts - 1));

                    double nLengthOld = getLength(mFirstX, mFirstY, mSecondX,
                            mSecondY);
                    double nLengthNow = getLength(fFirstX, fFirstY, fSecondX,
                            fSecondY);

                    float d = (float) ((nLengthNow - nLengthOld) / v.getWidth());

                    scale += d;

                    if (scale > 3) {
                        scale = 3f;
                    }
                    if (scale < 0.5) {
                        scale = 0.5f;
                    }
                    mSecondX = fSecondX;
                    mSecondY = fSecondY;
                }
                mFirstX = fFirstX;
                mFirstY = fFirstY;
                break;
            }
        }

        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        this.dx = this.dx - (e1.getX() - e2.getX()) / 20;
        this.dy = this.dy - (e1.getY() - e2.getY()) / 20;
        invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    private double getLength(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
