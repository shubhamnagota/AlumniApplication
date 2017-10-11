package com.sworks.mitalumniapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    Button loginButton, newsButton, aboutButton;
    Intent i;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        addListeners();
    }

    public void initComponents()
    {
        loginButton=(Button) findViewById(R.id.lab1);
        newsButton=(Button) findViewById(R.id.mab2);
        aboutButton=(Button) findViewById(R.id.mab3);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public void addListeners()
    {
        loginButton.setOnClickListener(this);
        newsButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.lab1)
        {
            i=new Intent(MainActivity.this, LoginActivity.class);
        }
        if (v.getId()==R.id.mab2)
        {
            i=new Intent(MainActivity.this, NewsActivity.class);
        }
        if (v.getId()==R.id.mab3)
        {
            i=new Intent(MainActivity.this, AboutMITActivity.class);
        }
        startActivity(i);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(MainActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        return false;
    }
}
