package com.example.indoorpositioning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    MapView bottomView;
    Button position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        bottomView = (MapView) findViewById(R.id.bottom_view);
        bottomView.setOnTouchListener(bottomView);
        position = (Button) findViewById(R.id.button_position);
        position.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// Scan type
                //.setPrompt("fit the qrcode")//
                .setCameraId(0)// set camera
                .setBeepEnabled(true)// add beep sound
                .initiateScan();// initialize
    }
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

                /* define later */
                bottomView.setpoint(x, y);
            }
        }
    }
}
