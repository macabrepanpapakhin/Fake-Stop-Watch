package com.sailaminoak.lookeverything;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageButton flag;
    Button button;
    EditText hour,minute,second;
    LinearLayout flagInformation;
    long seconds=0;
    boolean start=true;
    boolean stop=false;
    long realSeconds=0;
    int counting=0;
    long milliseconds=0;
    int rank=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Measuring Time is incorrect and should not be used as a Real Time measurement!!!.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        hour=findViewById(R.id.hour);
        minute=findViewById(R.id.minute);
        second=findViewById(R.id.second);
        button=findViewById(R.id.button);
        hour.setText("0");
        minute.setText("0");
        second.setText("0");
        flag=findViewById(R.id.flag);
        flagInformation=findViewById(R.id.flagInformation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(start==true && stop==false){


                 helper();
              }else if(start==true && stop){
                  stop=false;
                  helper();
              }
              else{
                  stop=true;
                  start=true;
                  button.setBackgroundColor(getResources().getColor(R.color.purple_700));
                  button.setText("Start");

              }

            }
        });

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=new TextView(MainActivity.this);
                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setTextSize(20);
                textView.setPadding(30,10,0,10);
                textView.setBackgroundColor(getResources().getColor(R.color.background_color));
                textView.setText("~"+rank+"~        "+hour.getText().toString().trim()+" : "+minute.getText().toString().trim()+" : "+second.getText().toString().trim()+" : "+milliseconds);
                flagInformation.addView(textView);
                rank++;
            }
        });


    }
    public void helper(){
        flagInformation.removeAllViews();
        rank=1;
        hour.setText("0");
        minute.setText("0");
        second.setText("0");
        realSeconds=0;
        counting=0;
        seconds=0;
        count();
        start=false;
        button.setText("Stop");
        button.setBackgroundColor(getResources().getColor(R.color.red_background));
    }
    public void count(){
        final Handler handler = new Handler(Looper.getMainLooper());
        if(!stop){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(counting==10){
                        milliseconds=0;
                        counting=0;
                        seconds++;
                        realSeconds++;
                        if(seconds==60){
                            second.setText("0");
                            seconds=0;
                        }
                        else{
                            second.setText(String.valueOf(seconds));
                        }
                        long minutes=realSeconds/60;
                        if(minutes==60){
                            minute.setText("0");
                        }else{
                            minute.setText(String.valueOf(minutes));
                        }
                        long hours=realSeconds/3600;
                        hour.setText(String.valueOf(hours));
                        count();
                    }else {
                        counting++;
                        count();
                    }




                }
            }, 100);
            milliseconds+=100;
        }



    }
}