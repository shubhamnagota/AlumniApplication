package com.sworks.mitalumniapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dto.ClientDTO;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

/**
 * Created by Shubham Nagota on 10-Feb-16.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText oldPassword, newPassword, confirmPassword;
    Button cpButton;
    String gotEnrollmentNumber;
    String oldPasswordS, newPasswordS, confirmPasswordS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle basket=getIntent().getExtras();
        gotEnrollmentNumber=basket.getString("key");
        initComponents();
        addListeners();
    }

    private void addListeners()
    {
        cpButton.setOnClickListener(this);
    }

    private void initComponents()
    {
        oldPassword=(EditText) findViewById(R.id.cpaopet);
        newPassword=(EditText) findViewById(R.id.cpanpet);
        confirmPassword=(EditText) findViewById(R.id.cpacpet);
        cpButton=(Button) findViewById(R.id.cpButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        oldPasswordS=oldPassword.getText().toString();
        newPasswordS=newPassword.getText().toString();
        confirmPasswordS=confirmPassword.getText().toString();
        ClientDTOInterface clientDTOInterface=new ClientDTO();
        clientDTOInterface.setEnrollmentNumber(gotEnrollmentNumber);
        clientDTOInterface.setPassword(oldPasswordS);
        ClientDAO clientDAO=new ClientDAO(this);
        try
        {
            clientDAO.authenticate(clientDTOInterface);
        }
        catch (ClientDAOException e)
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setMessage("Invalid Current Password");
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                }
            });
            AlertDialog ad=adb.create();
            adb.show();
            return;
        }
        if (!newPasswordS.equals(confirmPasswordS))
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setMessage("New Password and Confirm Password do not match");
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                }
            });
            AlertDialog ad=adb.create();
            adb.show();
            return;
        }
        ClientDTOInterface clientDTOInterface2=new ClientDTO();
        clientDTOInterface2.setEnrollmentNumber(gotEnrollmentNumber);
        clientDTOInterface2.setPassword(newPasswordS.toString());
        ClientDAO clientDAO2=new ClientDAO(this);
        try
        {
            clientDAO2.changePassword(clientDTOInterface2);
        }
        catch (ClientDAOException e)
        {
            e.printStackTrace();
        }
        Toast.makeText(this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(ChangePasswordActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(ChangePasswordActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(ChangePasswordActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_logout)
        {
            Intent i=new Intent(ChangePasswordActivity.this, MainActivity.class);
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
