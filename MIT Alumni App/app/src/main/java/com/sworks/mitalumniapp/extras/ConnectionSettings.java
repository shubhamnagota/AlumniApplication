package com.sworks.mitalumniapp.extras;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sanidhya PC on 10/02/2016.
 */
public class ConnectionSettings
{
    public static String ipAddress;
    public static int portNumber;
    SharedPreferences alumniSaved;;
    public static String filename="alumniSavedInformation";
    Context context;
    String mode;
    public ConnectionSettings(Context context,String mode)
    {
        this.context=context;
        this.mode=mode;
        if(mode.equals("READ"))
        {
            if(ipAddress==null)
            {
                SharedPreferences sp=context.getSharedPreferences(filename,0);
                ipAddress=sp.getString("ipAddress","");
                portNumber=Integer.parseInt(sp.getString("portNumber",""));
            }

        }


    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress=ipAddress;
    }

    public int getPortNumber()
    {
        return portNumber;
    }

    public void setPortNumber(int portNumber)
    {
        this.portNumber=portNumber;
    }
}
