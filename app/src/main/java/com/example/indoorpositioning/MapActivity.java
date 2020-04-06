package com.example.indoorpositioning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.indoorpositioning.placeInfo.Algorithm;
import com.example.indoorpositioning.placeInfo.Place;
import com.example.indoorpositioning.placeInfo.PlaceArray;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    MapView bottomView;
    Button position;
    Button search;
    Button find;
    EditText place;
    EditText xy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        bottomView = (MapView) findViewById(R.id.bottom_view);
        bottomView.setOnTouchListener(bottomView);
        position = (Button) findViewById(R.id.button_position);
        position.setOnClickListener(this);
        place = (EditText) findViewById(R.id.place_name);
        search = (Button) findViewById(R.id.button_findplace);
        search.setOnClickListener(this);
        find = (Button) findViewById(R.id.button_findxy);
        find.setOnClickListener(this);
        xy = (EditText) findViewById(R.id.place_xy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_position:
            new IntentIntegrator(this)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// Scan type
                    //.setPrompt("fit the qrcode")//
                    .setCameraId(0)// set camera
                    .setBeepEnabled(true)// add beep sound
                    .initiateScan();// initialize
                break;
            case R.id.button_findplace:
                String placeInfo = place.getText().toString();
                ArrayList <Integer> tempArray = new ArrayList<>();
                PlaceArray match = new PlaceArray();
                Place [] x = match.getPlaceArray();
                int [][] W = new int[x.length][x.length];
                for(int i =0; i< x.length; i++) {
                    for (int j=0; j<x.length;j++){
                        W[i][j] = x[i].getDis(j);
                    }
                }
                tempArray = Algorithm.dijkstra(W, Integer.parseInt(placeInfo), 0);

                bottomView.setRoute(x[tempArray.get(0)].getX(),x[tempArray.get(0)].getY(),x[tempArray.get(1)].getX(),x[tempArray.get(1)].getY());

//              bottomView.setDrawable(getResources().getDrawable(R.drawable.f2));
                break;
            case R.id.button_findxy:
                String placexy = xy.getText().toString();
                String [] temp = placexy.split(" ");
                int x1 = Integer.parseInt(temp[0].trim());
                int y1 = Integer.parseInt(temp[1].trim());
                bottomView.setpoint(x1, y1);
        }
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
