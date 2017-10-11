package com.sworks.mitalumniapp.pl.dao;

import android.content.Context;
import android.util.Log;

import com.sworks.mitalumniapp.extras.ConnectionSettings;
import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dao.interfaces.ClientDAOInterface;
import com.sworks.mitalumniapp.pl.dto.ClientDTO;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientDAO extends Thread implements ClientDAOInterface
{
    public static final String tag="Message";
    public static String filename="alumniSavedInformation";
    String request=null, response=null;
    String ipAddress;
    int portNumber;
    Context context;

    public ClientDAO(Context context)
    {
        this.context=context;
        ConnectionSettings cs=new ConnectionSettings(context, "READ");
        ipAddress=cs.getIpAddress();
        portNumber=cs.getPortNumber();
    }

    public void run()
    {
        try
        {
            OutputStream os;
            OutputStreamWriter osw;
            InputStream is;
            InputStreamReader isr;
            StringBuffer sb;
            int x;
            Socket socket=new Socket(ipAddress, portNumber);
            os=socket.getOutputStream();
            osw=new OutputStreamWriter(os);
            osw.write(request);
            osw.flush();
            is=socket.getInputStream();
            isr=new InputStreamReader(is);
            sb=new StringBuffer();
            while (true)
            {
                x=isr.read();
                if (x=='|' || x==-1)
                {
                    break;
                }
                sb.append((char) x);
            }
            response=sb.toString();
            socket.close();
        }
        catch (SocketException e)
        {
            Log.i(tag, "Exception at authenticate:"+e);
        }
        catch (IOException ioe)
        {
            Log.i(tag, "Exception at authenticate:"+ioe);
        }
    }

    @Override
    public void authenticate(ClientDTOInterface clientDTOInterface) throws ClientDAOException
    {
        response=null;
        String enrollmentNo=clientDTOInterface.getEnrollmentNumber();
        String password=clientDTOInterface.getPassword();
        request="AA#"+enrollmentNo+"#"+password+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        if (!(response.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
    }

    @Override
    public void updateAlumni(ClientDTOInterface clientDTOInterface) throws ClientDAOException
    {
        response=null;
        String enrollmentNo=clientDTOInterface.getEnrollmentNumber();
        String email=clientDTOInterface.getEmailAddress();
        String phoneNo=clientDTOInterface.getPhoneNumber();
        request="UA#"+enrollmentNo+"#"+email+"#"+phoneNo+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        if (!(response.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
    }

    @Override
    public void changePassword(ClientDTOInterface clientDTOInterface) throws ClientDAOException
    {
        response=null;
        String enrollmentNo=clientDTOInterface.getEnrollmentNumber();
        String password=clientDTOInterface.getPassword();
        request="CP#"+enrollmentNo+"#"+password+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        if (!(response.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
    }

    @Override
    public ClientDTOInterface getAlumniInfo(String enrollmentNumber) throws ClientDAOException
    {
        response=null;
        request="AI#"+enrollmentNumber+"|";
            start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        String temp=response.substring(0, 2);
        if (!(temp.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
        String email, firstName, lastName, gender, phoneNo, yearOfAdmission, profilePicture;
        int c1, c2, c3, c4, c5, c6;
        response=response.substring(3);
        c1=response.indexOf("#");
        c2=response.indexOf("#", c1+1);
        c3=response.indexOf("#", c2+1);
        c4=response.indexOf("#", c3+1);
        c5=response.indexOf("#", c4+1);
        c6=response.indexOf("#", c5+1);
        enrollmentNumber=response.substring(0, c1);
        email=response.substring(c1+1, c2);
        firstName=response.substring(c2+1, c3);
        lastName=response.substring(c3+1, c4);
        gender=response.substring(c4+1, c5);
        phoneNo=response.substring(c5+1, c6);
        yearOfAdmission=response.substring(c6+1);
        ClientDTOInterface clientDTOInterface=new ClientDTO();
        clientDTOInterface.setEnrollmentNumber(enrollmentNumber);
        clientDTOInterface.setEmailAddress(email);
        clientDTOInterface.setFirstName(firstName);
        clientDTOInterface.setLastName(lastName);
        clientDTOInterface.setGender(gender);
        clientDTOInterface.setPhoneNumber(phoneNo);
        clientDTOInterface.setYearOfAdmission(yearOfAdmission);
        return clientDTOInterface;
    }

    @Override
    public ArrayList<ClientDTOInterface> getAll() throws ClientDAOException
    {
        response=null;
        request="GA#|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        String temp=response.substring(0, 2);
        if (!(temp.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
        ArrayList<ClientDTOInterface> clients=new ArrayList<ClientDTOInterface>();
        response=response.substring(3);
        String seperator="999999999";
        String[] allClients=response.split(seperator);
        for (String oneClient : allClients)
        {
            String[] data=oneClient.split("#");
            ClientDTOInterface clientDTOInterface=new ClientDTO();
            clientDTOInterface.setEnrollmentNumber(data[0]);
            clientDTOInterface.setEmailAddress(data[1]);
            clientDTOInterface.setFirstName(data[2]);
            clientDTOInterface.setLastName(data[3]);
            clientDTOInterface.setGender(data[4]);
            clientDTOInterface.setPhoneNumber(data[5]);
            clientDTOInterface.setYearOfAdmission(data[6]);
            clients.add(clientDTOInterface);
        }
        return clients;
    }

    public void addFavorite(String enrollmentNo, String favEnrollmentNumber) throws ClientDAOException
    {
        response=null;
        request="AF#"+enrollmentNo+"#"+favEnrollmentNumber+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        if (!(response.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
    }

    @Override
    public void removeFavorite(String enrollmentNo, String favEnrollmentNumber) throws ClientDAOException
    {
        response=null;
        request="RF#"+enrollmentNo+"#"+favEnrollmentNumber+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        if (!(response.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
    }

    @Override
    public ArrayList<ClientDTOInterface> getAllFavoritesForEnrollmentNumber(String enrollmentNumber) throws ClientDAOException
    {
        response=null;
        request="GF#"+enrollmentNumber+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        String temp=response.substring(0, 2);
        if (!(temp.equals("NE")))
        {
            throw new ClientDAOException(response);
        }
        ArrayList<ClientDTOInterface> clients=new ArrayList<ClientDTOInterface>();
        response=response.substring(3);
        String seperator="999999999";
        String[] allFavorites=response.split(seperator);
        for (String oneFavorite : allFavorites)
        {
            String[] data=oneFavorite.split("#");
            ClientDTOInterface clientDTOInterface=new ClientDTO();
            clientDTOInterface.setEnrollmentNumber(data[0]);
            clientDTOInterface.setEmailAddress(data[1]);
            clientDTOInterface.setFirstName(data[2]);
            clientDTOInterface.setLastName(data[3]);
            clientDTOInterface.setGender(data[4]);
            clientDTOInterface.setPhoneNumber(data[5]);
            clientDTOInterface.setYearOfAdmission(data[6]);
            clients.add(clientDTOInterface);
        }
        return clients;
    }

    public boolean checkFavorite(String enrollmentNo, String favEnrollmentNo) throws ClientDAOException
    {
        response=null;
        request="CF#"+enrollmentNo+"#"+favEnrollmentNo+"|";
        start();
        while (response==null)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                Log.i(tag, ""+e);
            }
        }
        if (!(response.equals("NE")))
        {
            return false;
        }
        return true;
    }


 /*   public class LoaderClass extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
           return null;
        }
    }
    */
}
