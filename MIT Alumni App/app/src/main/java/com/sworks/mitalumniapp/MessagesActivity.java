package com.sworks.mitalumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MessagesActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(MessagesActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(MessagesActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(MessagesActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_logout)
        {
            Intent i=new Intent(MessagesActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return false;
    }
}
