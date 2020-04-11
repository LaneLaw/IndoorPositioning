package com.example.indoorpositioning;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
    String [] data1;
    ArrayAdapter <String> adapter1;
    AutoCompleteTextView act;
    int start;
    int end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.map_view);
        act = (AutoCompleteTextView)findViewById(R.id.act_main_cat1);
        data1 = new String[]{"Victoria", "sammy", "DIESEL","Elevator"};
        adapter1 = new ArrayAdapter<String>( this,R.layout.act,data1);
        act.setAdapter(adapter1);
        bottomView = (MapView) findViewById(R.id.bottom_view);
        bottomView.setOnTouchListener(bottomView);
        position = (Button) findViewById(R.id.button_position);
        position.setOnClickListener(this);
//        place = (EditText) findViewById(R.id.place_name);
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
                String placeInfo = act.getText().toString();
                String dest = placeInfo;
                ArrayList <Integer> tempArray = new ArrayList<>();
                PlaceArray match = new PlaceArray();
                Place [] x = match.getPlaceArray();
                for(int m=0; m<x.length; m++){
                    if(dest.equals(x[m].getName())){
                        end = m;
                        System.out.println("end: " + end);
                    }
                }
                if (start == end){
                    new AlertDialog.Builder(this)
                            .setTitle("Here!")
                            .setMessage("You are already at the destination.")
                            .setPositiveButton("OK", null).show();
                    break;
                }
                int [][] W = new int[x.length][x.length];
                for(int i =0; i< x.length; i++) {
                    for (int j=0; j<x.length;j++){
                        W[i][j] = x[i].getDis(j);
                    }
                }
                for(int n=0; n<x.length; n++){
                    int temp1 = W[0][n];
                    W[0][n] = W[start][n];
                    W[start][n] = temp1;
                    int temp2 = W[x.length-1][n];
                    W[x.length-1][n] = W[end][n];
                    W[end][n] = temp2;

//                    Place tempPlace1 = x[start];
//                    x[start] = x[0];
//                    x[0] = tempPlace1;
//                    Place tempPlace2 = x[end];
//                    x[end] = x[0];
//                    x[0] = tempPlace2;
                    x[0].setId(start);
                    x[start].setId(0);
                    x[end].setId(x.length-1);
                    x[x.length-1].setId(end);

                    Place change1 = x[0];
                    x[0] = x[start];
                    x[start] = change1;
                    Place change2= x[x.length-1];
                    x[x.length-1] = x[end];
                    x[end] = change2;
                }
                for(int n=0; n<x.length; n++){
                    int temp1 = W[n][0];
                    W[n][0] = W[n][start];
                    W[n][start] = temp1;
                    int temp2 = W[n][x.length-1];
                    W[n][x.length-1] = W[n][end];
                    W[n][end] = temp2;
                }
                tempArray = Algorithm.dijkstra(W, x.length, 0);
                bottomView.drawPath(tempArray,x);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Route").setMessage("Distance: ").create();
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                window.setWindowAnimations(R.style.mystyle);
                dialog.show();
//              bottomView.setDrawable(getResources().getDrawable(R.drawable.f2));
                break;
            case R.id.button_findxy:
                String placexy = xy.getText().toString();
                String [] temp = placexy.split(" ");
                int x1 = Integer.parseInt(temp[0].trim());
                int y1 = Integer.parseInt(temp[1].trim());
                bottomView.setpoint(x1, y1);
                break;
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
                double x1 = x;
                double y1 = y;
                int mark = -1;
                double min = 999999;
                PlaceArray s = new PlaceArray();
                Place [] tem = s.getPlaceArray();
                for(int i=0;i<tem.length;i++){
                    double tempX = tem[i].getX();
                    double tempY = tem[i].getY();
                    double dis = Math.sqrt(Math.pow(tempX - x1, 2) + Math.pow(tempY-y1, 2));
                    if(dis < min){
                        min = dis;
                        mark = i;
                    }
                }
                start = mark;
                bottomView.setpoint(tem[mark].getX(), tem[mark].getY());
                /* define later */

            }
        }
    }
}
