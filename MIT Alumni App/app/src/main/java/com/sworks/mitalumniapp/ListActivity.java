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
import android.widget.ListView;
import android.widget.Toast;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dao.interfaces.ClientDAOInterface;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity
{
    ClientDTOInterface clientDTOInterface;
    AlumniListAdapter alumniListAdapter;
    ArrayList<ClientDTOInterface> clients;
    ClientDAOInterface clientDAOInterface;
    String gotEnrollmentNumber;
    EditText searchEditText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.user_menu, menu);
        return true;
    }

    protected void onResume()
    {
        super.onResume();
        addListeners();
        clientDAOInterface=new ClientDAO(this);
        try
        {
            clients=clientDAOInterface.getAll();
        }
        catch (ClientDAOException e)
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Error");
            adb.setMessage(""+e.getMessage());
            adb.setNeutralButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                }
            });
            AlertDialog ad=adb.create();
            ad.show();
            return;
        }
        int i=0;
        while (i<clients.size())
        {
            clientDTOInterface=clients.get(i);
            if (clientDTOInterface.getEnrollmentNumber().equals(gotEnrollmentNumber))
            {
                clients.remove(clientDTOInterface);
                break;
            }
            i++;
        }
        for (i=0; i<clients.size(); i++)
        {
            ClientDAOInterface clientDAOInterface=new ClientDAO(this);
            try
            {
                clients.get(i).setIsFavourite(clientDAOInterface.checkFavorite(gotEnrollmentNumber, clients.get(i).getEnrollmentNumber()));
            }
            catch (ClientDAOException e)
            {
                e.printStackTrace();
            }
        }
        alumniListAdapter=new AlumniListAdapter(this, clients, gotEnrollmentNumber);
        ListView alumniList=(ListView) findViewById(R.id.alumniList);
        alumniList.setAdapter(alumniListAdapter);
        alumniList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String clickedEnrollmentNumber=clients.get(position).getEnrollmentNumber();
                Bundle basket=new Bundle();
                basket.putString("key", gotEnrollmentNumber);
                basket.putString("clicked", clickedEnrollmentNumber);
                Toast.makeText(ListActivity.this, "Item: "+position+" Clicked", Toast.LENGTH_SHORT).show();
                Intent goTo=new Intent(ListActivity.this, AlumniClickedActivity.class);
                goTo.putExtras(basket);
                startActivity(goTo);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle base=getIntent().getExtras();
        gotEnrollmentNumber=base.getString("key");
        searchEditText=(EditText) findViewById(R.id.laSearch);
    }

    void addListeners()
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

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()==R.id.menuitem_aboutus)
        {
            Intent i=new Intent(ListActivity.this, AboutUsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_aboutmit)
        {
            Intent i=new Intent(ListActivity.this, AboutMITActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_settings)
        {
            Intent i=new Intent(ListActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        if (item.getItemId()==R.id.menuitem_logout)
        {
            Intent i=new Intent(ListActivity.this, MainActivity.class);
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
