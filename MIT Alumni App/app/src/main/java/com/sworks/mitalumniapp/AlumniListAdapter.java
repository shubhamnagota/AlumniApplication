package com.sworks.mitalumniapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sworks.mitalumniapp.pl.dao.ClientDAO;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dto.ClientDTO;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

import java.util.ArrayList;

/**
 * Created by Shubham Nagota on 09-Feb-16.
 */
public class AlumniListAdapter extends BaseAdapter implements Filterable
{
    String gotEnrollmentNo;
    ArrayList<ClientDTOInterface> clients;
    ArrayList<ClientDTOInterface> mFilterList;
    ValueFilter valueFilter;
    private Context context;

    public AlumniListAdapter(Context context, ArrayList<ClientDTOInterface> clients, String gotEnrollmentNo)
    {
        this.gotEnrollmentNo=gotEnrollmentNo;
        this.clients=clients;
        this.mFilterList=clients;
        this.context=context;
    }

    @Override
    public int getCount()
    {
        return clients.size();
    }

    @Override
    public Object getItem(int position)
    {
        return clients.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return clients.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int p=position;
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView=null;
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.single_row, null);
            TextView entv=(TextView) convertView.findViewById(R.id.single_enrollment);
            TextView nametv=(TextView) convertView.findViewById(R.id.single_name);
            final ImageButton favButton=(ImageButton) convertView.findViewById(R.id.srib);
            final Drawable checked=context.getResources().getDrawable(R.drawable.star_icon_checked);
            final Drawable unchecked=context.getResources().getDrawable(R.drawable.star_icon_unchecked);
            nametv.setText(clients.get(position).getFirstName()+" "+clients.get(position).getLastName());
            entv.setText(clients.get(position).getEnrollmentNumber());
            if (clients.get(position).getIsFavourite()==true)
            {
                favButton.setImageResource(R.drawable.star_icon_checked);
                favButton.setTag(R.drawable.star_icon_checked);
            } else
            {
                favButton.setImageResource(R.drawable.star_icon_unchecked);
                favButton.setTag(R.drawable.star_icon_unchecked);
            }
            favButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ClientDAO clientDAO=new ClientDAO(context);
                    try
                    {
                        if ((Integer) favButton.getTag()==R.drawable.star_icon_checked)
                        {
                            favButton.setImageResource(R.drawable.star_icon_unchecked);
                            favButton.setTag(R.drawable.star_icon_unchecked);
                            clientDAO.removeFavorite(gotEnrollmentNo, clients.get(p).getEnrollmentNumber());
                        } else
                        {
                            favButton.setImageResource(R.drawable.star_icon_checked);
                            favButton.setTag(R.drawable.star_icon_checked);
                            clientDAO.addFavorite(gotEnrollmentNo, clients.get(p).getEnrollmentNumber());
                        }
                    }
                    catch (ClientDAOException clientDAOException)
                    {
                        Log.i("Message", clientDAOException.getMessage());
                    }
                }
            });
        }
        return convertView;
    }

    public Filter getFilter()
    {
        if (valueFilter==null)
        {
            valueFilter=new ValueFilter();
        }
        return valueFilter;
    }

    class ValueFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results=new FilterResults();
            if (constraint!=null && constraint.length()>0)
            {
                ArrayList<ClientDTOInterface> filterList=new ArrayList<ClientDTOInterface>();
                for (int i=0; i<mFilterList.size(); i++)
                {
                    if (((mFilterList.get(i).getFirstName()+" "+mFilterList.get(i).getLastName()).toUpperCase()).contains(constraint.toString().toUpperCase()))
                    {
                        ClientDTOInterface clientDTOInterface=new ClientDTO();
                        clientDTOInterface.setEnrollmentNumber(mFilterList.get(i).getEnrollmentNumber());
                        clientDTOInterface.setFirstName(mFilterList.get(i).getFirstName());
                        clientDTOInterface.setLastName(mFilterList.get(i).getLastName());
                        clientDTOInterface.setIsFavourite(mFilterList.get(i).getIsFavourite());
                        filterList.add(clientDTOInterface);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            } else
            {
                results.count=mFilterList.size();
                results.values=mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.count==0)
            {
                notifyDataSetInvalidated();
            } else
            {
                clients=(ArrayList<ClientDTOInterface>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}



