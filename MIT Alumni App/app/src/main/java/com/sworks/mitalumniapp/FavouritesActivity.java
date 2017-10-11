package com.sworks.mitalumniapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity
{
    ArrayList<ClientDTOInterface> favorites;
    ClientDAO clientDAO;
    EditText searchEditText;
    String gotEnrollmentNumber;
    AlumniListAdapter alumniListAdapter;

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
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle base=getIntent().getExtras();
        gotEnrollmentNumber=base.getString("key");
        searchEditText=(EditText) findViewById(R.id.favouritesearch);
        addListeners();
    }
    public void addListeners()
    {
        searchEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                alumniListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    protected void onResume()
    {
        super.onResume();
        clientDAO=new ClientDAO(this);
        try
        {
            favorites=clientDAO.getAllFavoritesForEnrollmentNumber(gotEnrollmentNumber);
        }
        catch (ClientDAOException clientDAOException)
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setMessage(""+clientDAOException.getMessage());
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    finish();
                }
            });
            AlertDialog ad=adb.create();
            adb.show();
            return;
        }

      for(int i=0;i<favorites.size();i++)
        {
            favorites.get(i).setIsFavourite(true);
        }
        alumniListAdapter=new AlumniListAdapter(this, favorites, gotEnrollmentNumber);
        ListView alumniList=(ListView) findViewById(R.id.favList);
        alumniList.setAdapter(alumniListAdapter);
        alumniList.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                          {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                              {
                                                  String clickedEnrollmentNumber=favorites.get(position).getEnrollmentNumber();
                                                  Bundle basket=new Bundle();
                                                  basket.putString("key", gotEnrollmentNumber);
                                                  basket.putString("clicked", clickedEnrollmentNumber);
                                                  Toast.makeText(FavouritesActivity.this, "Item: "+position+" Clicked", Toast.LENGTH_SHORT).show();
                                                  Intent goTo=new Intent(FavouritesActivity.this, AlumniClickedActivity.class);
                                                  goTo.putExtras(basket);
                                                  startActivity(goTo);
                                              }
                                          });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(FavouritesActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(FavouritesActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(FavouritesActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return false;
    }
}
