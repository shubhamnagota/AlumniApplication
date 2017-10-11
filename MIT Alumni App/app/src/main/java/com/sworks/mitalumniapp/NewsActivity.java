package com.sworks.mitalumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class NewsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News");
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
            Intent i=new Intent(NewsActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(NewsActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(NewsActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return false;
    }
}
