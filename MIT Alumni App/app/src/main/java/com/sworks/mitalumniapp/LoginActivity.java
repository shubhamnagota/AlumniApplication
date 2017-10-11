package com.sworks.mitalumniapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dao.interfaces.ClientDAOInterface;
import com.sworks.mitalumniapp.pl.dto.ClientDTO;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

public class LoginActivity extends AppCompatActivity implements OnClickListener
{
    public static final String tag="Message";
    public static String filename="alumniSavedInformation";
    Button loginButton;
    Intent i;
    EditText enrollmentNoEditText, passwordEditText;
    TextView enrollmentNoCheckTextView, passwordCheckTextView;
    CheckBox rememberMeCheckbox;
    SharedPreferences alumniSaved;;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initComponents();
        addListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    protected void onResume()
    {
        super.onResume();
     progressBar.setVisibility(View.GONE);


        enrollmentNoCheckTextView.setText("");
        passwordCheckTextView.setText("");
        enrollmentNoEditText.setText("");
        passwordEditText.setText("");
        alumniSaved=getSharedPreferences(filename, 0);
        enrollmentNoEditText.setText(alumniSaved.getString("enrollmentNumber", ""));
        passwordEditText.setText(alumniSaved.getString("password", ""));
    }

    public void initComponents()
    {
        loginButton=(Button) findViewById(R.id.lab1);
        enrollmentNoEditText=(EditText) findViewById(R.id.laet1);
        passwordEditText=(EditText) findViewById(R.id.laet2);
        enrollmentNoCheckTextView=(TextView) findViewById(R.id.latv4);
        passwordCheckTextView=(TextView) findViewById(R.id.latv5);
        enrollmentNoCheckTextView.setText("");
        passwordCheckTextView.setText("");
        enrollmentNoEditText.setText("");
        passwordEditText.setText("");
        rememberMeCheckbox=(CheckBox) findViewById(R.id.lacb1);
        progressBar=(ProgressBar)findViewById(R.id.lapb1);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(LoginActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(LoginActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(LoginActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return false;
    }

    public void addListeners()
    {
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String enrollmentNo=enrollmentNoEditText.getText().toString().toUpperCase();
        String password=passwordEditText.getText().toString();
        enrollmentNoCheckTextView.setText("");
        passwordCheckTextView.setText("");
        if (enrollmentNo.length()==0)
        {
            enrollmentNoCheckTextView.setText("*required");
            enrollmentNoCheckTextView.setTextColor(Color.RED);
            return;
        }
        if (password.length()==0)
        {
            passwordCheckTextView.setText("*required");
            passwordCheckTextView.setTextColor(Color.RED);
            return;
        }
        try
        {

            if (rememberMeCheckbox.isChecked())
            {
                alumniSaved=getSharedPreferences(filename, 0);
                SharedPreferences.Editor alumniEditor=alumniSaved.edit();
                String enNo=enrollmentNoEditText.getText().toString().toUpperCase();
                String pass=passwordEditText.getText().toString();
                alumniEditor.putString("enrollmentNumber", enNo);
                alumniEditor.putString("password", pass);
                alumniEditor.apply();
                Toast.makeText(this, "Details Saved", Toast.LENGTH_SHORT).show();
            } else
            {
                alumniSaved=getSharedPreferences(filename, 0);
                SharedPreferences.Editor alumniEditor=alumniSaved.edit();
                alumniEditor.putString("enrollmentNumber", "");
                alumniEditor.putString("password", "");
                alumniEditor.apply();
            }


            ClientDTOInterface clientDTOInterface=new ClientDTO();
            clientDTOInterface.setEnrollmentNumber(enrollmentNo);
            clientDTOInterface.setPassword(password);
            ClientDAOInterface clientDAOInterface=new ClientDAO(this);
            clientDAOInterface.authenticate(clientDTOInterface);
            i=new Intent(LoginActivity.this, UserActivity.class);
            Bundle basket=new Bundle();
            basket.putString("key", enrollmentNo);
            i.putExtras(basket);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        catch (final ClientDAOException clientDAOException)
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setMessage(""+clientDAOException.getMessage());
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            AlertDialog ad=adb.create();
            adb.show();


        }
    }
}

