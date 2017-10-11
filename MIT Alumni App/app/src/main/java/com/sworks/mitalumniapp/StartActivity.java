package com.sworks.mitalumniapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity
{
    public static String filename="alumniSavedInformation";

    @Override
    protected void onStop()
    {
        super.onStop();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences sp=getSharedPreferences(filename,0);
        if(sp.getBoolean("first_time",true)){
            SharedPreferences.Editor spe=sp.edit();
            spe.putString("ipAddress", "192.168.173.1");
            spe.putString("portNumber", "5000");
            spe.putBoolean("first_time",false);
            spe.commit();
        }
        Thread t=new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                    Intent i=new Intent(StartActivity.this, MainActivity.class);
                    startActivity(i);
                }
                catch (Exception e)
                {
                }
            }
        };
        t.start();
    }
}
