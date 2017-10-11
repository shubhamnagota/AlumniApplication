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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dao.interfaces.ClientDAOInterface;
import com.sworks.mitalumniapp.pl.dto.ClientDTO;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

public class AlumniClickedActivity extends AppCompatActivity
{
    TextView catven, catvem, acan, catvpn, catvgn, catvay, catvaf;
    ImageButton caib;
    ImageView profileImage;
    String gotEnrollmentNumber;
    String clickedEnrollmentNumber;
    String tag="message";
    ClientDAOInterface clientDAOInterface;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni_clicked);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle basket=getIntent().getExtras();
        clickedEnrollmentNumber=basket.getString("clicked");
        gotEnrollmentNumber=basket.getString("key");
        initComponents();
        addListeners();
    }

    public void initComponents()
    {
        catven=(TextView) findViewById(R.id.catven);
        catvem=(TextView) findViewById(R.id.catvem);
       acan=(TextView) findViewById(R.id.acan);
        catvpn=(TextView) findViewById(R.id.catvpn);
        catvgn=(TextView) findViewById(R.id.catvgn);
        catvay=(TextView) findViewById(R.id.catvay);
        caib=(ImageButton) findViewById(R.id.favButton);
        profileImage=(ImageView) findViewById(R.id.clickedImage);
    }

    protected void onResume()
    {
        super.onResume();
        clientDAOInterface=new ClientDAO(AlumniClickedActivity.this);
        ClientDTOInterface clientDTOInterface=new ClientDTO();
        boolean result=false;
        try
        {
            result=clientDAOInterface.checkFavorite(gotEnrollmentNumber, clickedEnrollmentNumber);
            clientDAOInterface=new ClientDAO(AlumniClickedActivity.this);
            clientDTOInterface=clientDAOInterface.getAlumniInfo(clickedEnrollmentNumber);
            acan.setText(clientDTOInterface.getFirstName()+" "+clientDTOInterface.getLastName());
            catven.setText(clientDTOInterface.getEnrollmentNumber());
            catvpn.setText(clientDTOInterface.getPhoneNumber());
            catvem.setText(clientDTOInterface.getEmailAddress());
            catvgn.setText(clientDTOInterface.getGender());
            catvay.setText(clientDTOInterface.getYearOfAdmission());
            if (result==true)
            {
                caib.setImageResource(R.drawable.star_icon_checked);
                caib.setTag(R.drawable.star_icon_checked);
            } else
            {
                caib.setImageResource(R.drawable.star_icon_unchecked);
                caib.setTag(R.drawable.star_icon_unchecked);
            }
        }
        catch (ClientDAOException e)
        {
            e.printStackTrace();
        }
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(AlumniClickedActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(AlumniClickedActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(AlumniClickedActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_logout)
        {
            Intent i=new Intent(AlumniClickedActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        return false;
    }

    private void addListeners()
    {
        caib.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clientDAOInterface=new ClientDAO(AlumniClickedActivity.this);
                try
                {
                    if ((Integer) caib.getTag()==R.drawable.star_icon_unchecked)
                    {
                        caib.setImageResource(R.drawable.star_icon_checked);
                        caib.setTag(R.drawable.star_icon_checked);
                        clientDAOInterface.addFavorite(gotEnrollmentNumber, clickedEnrollmentNumber);
                    } else
                    {
                        clientDAOInterface=new ClientDAO(AlumniClickedActivity.this);
                        caib.setImageResource(R.drawable.star_icon_unchecked);
                        caib.setTag(R.drawable.star_icon_unchecked);
                        clientDAOInterface.removeFavorite(gotEnrollmentNumber, clickedEnrollmentNumber);
                    }
                }
                catch (ClientDAOException clientDAOException)
                {
                    Log.i("Message", clientDAOException.getMessage());
                }
            }
        });
    }
}
