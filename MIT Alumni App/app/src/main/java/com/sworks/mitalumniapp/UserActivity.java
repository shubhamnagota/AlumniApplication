package com.sworks.mitalumniapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserActivity extends AppCompatActivity implements OnClickListener
{
    Button viewAllButton, favouritesButton, messagesButton, profileButton;
    Intent i;
    String gotEnrollmentNumber;
    String tag="message";
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Bundle gotBasket=getIntent().getExtras();
        gotEnrollmentNumber=gotBasket.getString("key").toUpperCase();
        initComponents();
        addListeners();
    }

    public void initComponents()
    {
        profileButton=(Button) findViewById(R.id.uab1);
        viewAllButton=(Button) findViewById(R.id.uab2);
        favouritesButton=(Button) findViewById(R.id.uab3);
        messagesButton=(Button) findViewById(R.id.uab4);
    }

    public void addListeners()
    {
        profileButton.setOnClickListener(this);
        viewAllButton.setOnClickListener(this);
        favouritesButton.setOnClickListener(this);
        messagesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.uab1)
        {
            i=new Intent(UserActivity.this, ProfileActivity.class);
            Bundle basket=new Bundle();
            basket.putString("key", gotEnrollmentNumber);
            i.putExtras(basket);
            startActivity(i);
        }
        if (v.getId()==R.id.uab2)
        {
            i=new Intent(UserActivity.this, ListActivity.class);
            Bundle basket = new Bundle();
            basket.putString("key", gotEnrollmentNumber);
            i.putExtras(basket);
            startActivity(i);
        }
        if (v.getId()==R.id.uab3)
        {
            i=new Intent(UserActivity.this, FavouritesActivity.class);
            Bundle basket = new Bundle();
            basket.putString("key", gotEnrollmentNumber);
            i.putExtras(basket);
            startActivity(i);
        }
        if (v.getId()==R.id.uab4)
        {
            i=new Intent(UserActivity.this, MessagesActivity.class);
        }
        startActivity(i);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle("Exit");
        adb.setMessage("Are you sure you want to exit?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
finish();
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        AlertDialog ad=adb.create();
        adb.show();
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(UserActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(UserActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(UserActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_logout)
        {
            Intent i=new Intent(UserActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        return false;
    }
}
