package com.example.indoorpositioning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;


 public class MainActivity extends Activity implements OnGestureListener, View.OnClickListener {
    private ViewFlipper flipper;
    private GestureDetector detector;
    private Button start;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view);

        detector = new GestureDetector(this);
        flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper1);

        flipper.addView(addImageView(R.drawable.home1));
        flipper.addView(addImageView(R.drawable.f2));
        flipper.addView(addImageView(R.drawable.ic_launcher_background));
        flipper.addView(addImageView(R.drawable.ic_launcher_foreground));
        flipper.addView(addImageView(R.drawable.timg));
        start = (Button) findViewById(R.id.button_start);
        start.setOnClickListener(this);
    }

    private View addImageView(int id) {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        return iv;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (e1.getX() - e2.getX() > 120) {
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper.showNext();
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            this.flipper.showPrevious();
            return true;
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
       return false;
    }

     @Override
     public void onClick(View v) {
         Intent intent = new Intent(MainActivity.this, MapActivity.class);
         startActivity(intent);
     }
 }