package com.sworks.mitalumniapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sworks.mitalumniapp.extras.ConnectionSettings;

public class ConnectionSettingsActivity extends AppCompatActivity implements OnClickListener
{
    Button saveButton;
    EditText ipAddressEditText, portNumberEditText;
    CheckBox defaultCheckBox;
    String ipAddress;
    String portNumber;
    public static String filename="alumniSavedInformation";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_settings);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Connection Settings");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
        addListeners();
    }

    public void initComponents()
    {
        ipAddressEditText=(EditText) findViewById(R.id.csaet1);
        portNumberEditText=(EditText) findViewById(R.id.csaet2);
        saveButton=(Button) findViewById(R.id.csab1);
        defaultCheckBox=(CheckBox) findViewById(R.id.csacb1);
        SharedPreferences sp=getSharedPreferences(filename,0);
        ipAddressEditText.setHint(sp.getString("ipAddress",""));
        portNumberEditText.setHint(sp.getString("portNumber",""));
    }


    protected void Resume()
    {
        super.onResume();

    }

    public void addListeners()
    {
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        ipAddress=ipAddressEditText.getText().toString();
        portNumber=portNumberEditText.getText().toString();
        ConnectionSettings cs=new ConnectionSettings(this,"WRITE");
        cs.setIpAddress(ipAddress);
        cs.setPortNumber(Integer.parseInt(portNumber));
        if (defaultCheckBox.isChecked())
        {
            SharedPreferences sp=getSharedPreferences(filename, 0);
            SharedPreferences.Editor spe=sp.edit();
            spe.putString("ipAddress", ipAddress);
            spe.putString("portNumber", portNumber);
            spe.commit();

        }
        Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return false;
    }
}
