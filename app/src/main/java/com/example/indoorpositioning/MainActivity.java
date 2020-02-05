package com.example.indoorpositioning;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indoorpositioning.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * sensor
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener{
    //sensor manager defined
    SensorManager sm;
    //defind the active controller
    MyView myView;
    //get some sensors
    Sensor sensor1;
    Sensor sensor2;
    Sensor sensor3;
    float xDir = 0;
    float globalStep;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;



    int detectStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_view);
        myView= (MyView) findViewById(R.id.second_myView);
//        tv1 = (TextView) findViewById(R.id.textView1);
//        tv2 = (TextView) findViewById(R.id.textView2);
//        tv3 = (TextView) findViewById(R.id.textView3);
//        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.result);


        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_toIndoor).setOnClickListener(this);
//        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);  //implement a sensor
//        sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//        sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start:
                new IntentIntegrator(this)
                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// Scan type
                        //.setPrompt("fit the qrcode")//
                        .setCameraId(0)// set camera
                        .setBeepEnabled(true)// add beep sound
                        .initiateScan();// initialize
                break;
            case R.id.button_toIndoor:
                Intent intent = new Intent();
                intent.setClass(com.example.indoorpositioning.MainActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }
    /***
     * register sensor
     */
    @Override
    protected void onResume() {
        super.onResume();
        setSensor();
        SensorManager mSensorManager;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //register listener
//        sensorManager.registerListener(listener, sensor1, 50000);
//        sensorManager.registerListener(listener, sensor2, 20000);
//        sensorManager.registerListener(listener, sensor3, 20000);
}

    public void setSensor() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener((SensorEventListener) this, sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), 1000000);
        sm.registerListener((SensorEventListener) this, sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), 1);
        sm.registerListener((SensorEventListener) this, sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), 1000000);
        sm.registerListener((SensorEventListener) this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), 1000000);
    }
    /**
     * listen the object of sensor
     */


    @Override
    public void onSensorChanged(SensorEvent event) {
 if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
                float step = event.values[0];
//                  tv2.setText("step: " + step);
                  if(globalStep != step){
                      float diff = step - globalStep;
                      if(diff > 50){
                          globalStep = step;
                      }else {
                          double moveX = diff * cos(xDir);
                          double moveY = diff * sin(xDir);
                          myView.initdata((int) moveX, (int) moveY);
                          globalStep = step;
                      }
                  }
        }       else if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
//            tv1.setText("x: " + x + ", y: " + y + ", z: " + z);
            xDir = x;
//            if(event.values[0] == 1.0){
//                detectStep ++;
//                tv4.setText(detectStep);
//            }

        }
 else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            float[] values = event.values;
            if (values.length >= 3) {
                float x = values[0];
                float y = values[1];
                float z = values[2];
//                if (Math.abs(x) > 1) {
//                    myView.initdata((int) x, (int) y);
//                }
//                if (Math.abs(y) > 1) {
//                    myView.initdata((int) x, -(int) y);
//                }
//                tv3.setText("x: " + x + ", y: " + y + ", z: " + z);
        }else if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
//                tv4.setText("x: " + x + ", y: " + y + ", z: " + z);
            }else{}
        }
    }
//    SensorEventListener listener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent event) {
//            // Log.e("TAG", "-------sensor changed-------");
//            //get sensor data
//            if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
//                float[] values = event.values;
//                float max = sensor1.getMaximumRange();//get max value
//                if (values.length >= 3) {
//                    float x = values[0];
//                    float y = values[1];
//                    float z = values[2];
//                    if (Math.abs(x) > max / 39) {
//                        myView.initdata((int) x, (int) y);
//                    }
//                    if (Math.abs(y) > max / 39) {
//                        myView.initdata((int) x, -(int) y);
//                    }
//                    tv1.setText("x: " + x);
//                }
//            }else if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
//
//            }else{
//                float m = event.values[0];
//                tv2.setText("m: " + m);
//            }
//
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int accuracy) {
//            //the state changed
//        }
//    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onPause() {
        sm.unregisterListener(this);
        super.onPause();
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        sm.unregisterListener(this);
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Scan result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                //Fail scan
            } else {
                String result = intentResult.getContents();//Return value
                String [] res = result.split(" ");

                int x = Integer.parseInt(res[0].trim());
                int y = Integer.parseInt(res[1].trim());
                tv5.setText("Result：" + result);
                /* define later */
                myView.setpoint(x, y);
            }
        }
    }

}
