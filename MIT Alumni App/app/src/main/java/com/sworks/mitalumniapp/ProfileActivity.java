package com.sworks.mitalumniapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dto.ClientDTO;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

public class ProfileActivity extends AppCompatActivity implements OnClickListener
{
    TextView enrollmentNoTextView, genderTextView, emailTextView, phoneNoTextView, admittedInTextView, nameTextView;
    EditText phoneNoEditText, emailEditText;
    Button editProfileButton, changePasswordButton, editPhotoButton;
    String gotEnrollmentNumber;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle gotBasket=getIntent().getExtras();
        gotEnrollmentNumber=gotBasket.getString("key");
        initComponents();
        addListeners();
    }

    public void initComponents()
    {
        enrollmentNoTextView=(TextView) findViewById(R.id.patven);
        genderTextView=(TextView) findViewById(R.id.patvgn);
        emailTextView=(TextView) findViewById(R.id.patvem);
        nameTextView=(TextView) findViewById(R.id.patvn);
        phoneNoTextView=(TextView) findViewById(R.id.patvpn);
        admittedInTextView=(TextView) findViewById(R.id.patvy);
        phoneNoEditText=(EditText) findViewById(R.id.paetpn);
        emailEditText=(EditText) findViewById(R.id.paetem);
        editProfileButton=(Button) findViewById(R.id.pab1);
        changePasswordButton=(Button) findViewById(R.id.pab2);
        editPhotoButton=(Button) findViewById(R.id.pab3);
        imageView=(ImageView) findViewById(R.id.laiv1);
        emailTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    protected void onResume()
    {
        super.onResume();
        phoneNoEditText.setVisibility(View.INVISIBLE);
        emailEditText.setVisibility(View.INVISIBLE);
        phoneNoTextView.setVisibility(View.VISIBLE);
        emailTextView.setVisibility(View.VISIBLE);
        ClientDAO clientDAO=new ClientDAO(this);
        ClientDTOInterface clientDTOInterface=null;
        try
        {
            clientDTOInterface=clientDAO.getAlumniInfo(gotEnrollmentNumber);
        }
        catch (ClientDAOException e)
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setMessage(""+e.getMessage());
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog ad=adb.create();
            adb.show();
            return;
        }
        nameTextView.setText(clientDTOInterface.getFirstName()+" "+clientDTOInterface.getLastName());
        enrollmentNoTextView.setText(clientDTOInterface.getEnrollmentNumber());
        phoneNoTextView.setText(clientDTOInterface.getPhoneNumber());
        emailTextView.setText(clientDTOInterface.getEmailAddress());
        genderTextView.setText(clientDTOInterface.getGender());
        admittedInTextView.setText(clientDTOInterface.getYearOfAdmission());
    }

    public void addListeners()
    {
        editProfileButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        editPhotoButton.setOnClickListener(this);
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
            Intent i=new Intent(ProfileActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(ProfileActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_logout)
        {
            Intent i=new Intent(ProfileActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId()==R.id.pab1)
        {
            if (editProfileButton.getText().toString().equals("Edit Profile"))
            {
                phoneNoTextView.setVisibility(View.INVISIBLE);
                emailTextView.setVisibility(View.INVISIBLE);
                phoneNoEditText.setVisibility(View.VISIBLE);
                emailEditText.setVisibility(View.VISIBLE);
                phoneNoEditText.setText(phoneNoTextView.getText().toString());
                emailEditText.setText(emailTextView.getText().toString());
                editProfileButton.setText("Save");
                changePasswordButton.setText("Cancel");
            } else
            {
                phoneNoTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                phoneNoEditText.setVisibility(View.INVISIBLE);
                emailEditText.setVisibility(View.INVISIBLE);
                phoneNoTextView.setText(phoneNoEditText.getText().toString());
                emailTextView.setText(emailEditText.getText().toString());
                editProfileButton.setText("Edit Profile");
                changePasswordButton.setText("Change Password");
                String email=emailEditText.getText().toString();
                String phoneNo=phoneNoEditText.getText().toString();
                ClientDAO clientDAO=new ClientDAO(this);
                ClientDTOInterface clientDTOInterface=new ClientDTO();
                clientDTOInterface.setEnrollmentNumber(gotEnrollmentNumber);
                clientDTOInterface.setPhoneNumber(phoneNo);
                clientDTOInterface.setEmailAddress(email);
                try
                {
                    clientDAO.updateAlumni(clientDTOInterface);
                }
                catch (ClientDAOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if (v.getId()==R.id.pab2)
        {
            if (changePasswordButton.getText().toString().equals("Change Password"))
            {
                Intent i=new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                Bundle basket=new Bundle();
                basket.putString("key", gotEnrollmentNumber);
                i.putExtras(basket);
                startActivity(i);
            } else
            {
                phoneNoTextView.setVisibility(View.VISIBLE);
                emailTextView.setVisibility(View.VISIBLE);
                phoneNoEditText.setVisibility(View.INVISIBLE);
                emailEditText.setVisibility(View.INVISIBLE);
                editProfileButton.setText("Edit Profile");
                changePasswordButton.setText("Change Password");
            }
        }
        if (v.getId()==R.id.pab3)
        {
        }
    }
}
