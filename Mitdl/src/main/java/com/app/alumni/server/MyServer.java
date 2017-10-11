package com.app.alumni.server;
import java.io.*;
import java.net.*;
import com.app.alumni.dl.dao.interfaces.*;
import com.app.alumni.dl.dto.interfaces.*;
import com.app.alumni.dl.dto.*;
import com.app.alumni.dl.dao.*;
import com.app.alumni.dl.exceptions.*;
import java.util.*;
import com.app.alumni.favdl.dao.*;
import com.app.alumni.favdl.dao.interfaces.*;
import com.app.alumni.favdl.dto.interfaces.*;
import com.app.alumni.favdl.dto.*;
public class MyServer
{
String seperator="999999999";
Socket ck;
ServerSocket serverSocket;
int portNumber;
InputStream is;
InputStreamReader isr;
OutputStream os;
OutputStreamWriter osw;
StringBuffer sb;
String request=null,response=null;
String mode;
int x;

public MyServer(int portNumber)
{
this.portNumber=portNumber;
try
{
serverSocket=new ServerSocket(this.portNumber);
startListening();
}
catch(Exception e)
{
System.out.println(e);
}
}

public void startListening()
{
try
{
while(true)
{
System.out.println("Server is listening on port : "+this.portNumber);
ck=serverSocket.accept();
System.out.println("Request arrived ");
is=ck.getInputStream();
isr=new InputStreamReader(is);
sb=new StringBuffer();
x=isr.read();
sb.append((char)x);
x=isr.read();
sb.append((char)x);
mode=sb.toString();
x=isr.read();
sb=new StringBuffer();
while(true)
{
x=isr.read();
if(x=='|' || x==-1)
{
break;
}
sb.append((char)x);
}
request=sb.toString();
System.out.println("Request : "+request+" and Mode : "+mode);
if(mode.equals("AA"))
{
response=authenticate(request);
}
if(mode.equals("UA"))
{
response=updateAlumni(request);
}
if(mode.equals("CP"))
{
response=changePassword(request);
}
if(mode.equals("AI"))
{
response=getAlumniInfo(request);
}
if(mode.equals("GA"))
{
response=getAll(request);
}
if(mode.equals("AF"))
{
response=addFavorite(request);
}
if(mode.equals("RF"))
{
response=removeFavorite(request);
}
if(mode.equals("GF"))
{
response=getAllFavorites(request);
}
if(mode.equals("CF"))
{
response=isFavoriteExists(request);
}
os=ck.getOutputStream();
osw=new OutputStreamWriter(os);
osw.write(response);
System.out.println("-------------------------------------------------------------");
osw.flush();
System.out.println("Response sent: "+response);
System.out.println("----------------------------------------------------------");
ck.close();
}
}
catch(Exception e)
{  
System.out.println(e);
}
}
public String addFavorite(String request)
{
	response ="NE|";
	FavoriteDTOInterface favoriteDTOInterface=new FavoriteDTO();
	FavoriteDAO favoriteDAO=new FavoriteDAO();
	int c1;
	String enrollmentNumber;
	String favoriteEnrollmentNumber;
	c1=request.indexOf("#");
	enrollmentNumber=request.substring(0,c1);
	favoriteEnrollmentNumber=request.substring(c1+1);
	favoriteDTOInterface.setEnrollmentNumber(enrollmentNumber);
	favoriteDTOInterface.setFavoriteEnrollmentNumber(favoriteEnrollmentNumber);
	try
	{
	favoriteDAO.add(favoriteDTOInterface);
	}catch(DAOException daoException)
	{
		response=""+daoException.getMessage()+"|";
	}
	return response;
	
}
public String removeFavorite(String request)
{
	response ="NE|";
	FavoriteDTOInterface favoriteDTOInterface=new FavoriteDTO();
	FavoriteDAO favoriteDAO=new FavoriteDAO();
	int c1;
	String enrollmentNumber;
	String favoriteEnrollmentNumber;
	c1=request.indexOf("#");
	enrollmentNumber=request.substring(0,c1);
	favoriteEnrollmentNumber=request.substring(c1+1);
	favoriteDTOInterface.setEnrollmentNumber(enrollmentNumber);
	favoriteDTOInterface.setFavoriteEnrollmentNumber(favoriteEnrollmentNumber);
	try
	{
	favoriteDAO.remove(favoriteDTOInterface);
	}catch(DAOException daoException)
	{
		response=""+daoException.getMessage()+"|";
	}
	return response;
	
}
public String getAllFavorites(String request)
{
	response ="NE#";
	FavoriteDTOInterface favoriteDTOInterface;
	ArrayList<String> favorites=null;
	FavoriteDAO favoriteDAO=new FavoriteDAO();
	String enrollmentNumber=request;
	try
	{
		favorites=favoriteDAO.getAllFavorites(enrollmentNumber);
		int x=0;
		AlumniDAO alumniDAO=new AlumniDAO();
		AlumniDTOInterface alumniDTOInterface;
		while(x<favorites.size())
		{
			alumniDTOInterface=alumniDAO.getAlumniInfo(favorites.get(x));
			response=response+alumniDTOInterface.getEnrollmentNumber()+"#"+alumniDTOInterface.getEmailAddress()+"#"+alumniDTOInterface.getFirstName()+"#"+alumniDTOInterface.getLastName()+"#"+alumniDTOInterface.getGender()+"#"+alumniDTOInterface.getPhoneNumber()+"#"+alumniDTOInterface.getYearOfAdmission()+"#"+seperator;
			x++;
		}
		response=response+"|";
	}catch(DAOException daoException)
	{
		response=""+daoException.getMessage()+"|";
	}
	return response;
}
public String authenticate(String request)
{
int c1;
String enrollmentNo,password,response;
response="NE|";
c1=request.indexOf("#");
enrollmentNo=request.substring(0,c1);
password=request.substring(c1+1);
AlumniDTOInterface alumniDTOInterface=new AlumniDTO();
alumniDTOInterface.setEnrollmentNumber(enrollmentNo);
alumniDTOInterface.setPassword(password);
AlumniDAOInterface alumniDAOInterface=new AlumniDAO();
try
{
alumniDAOInterface.authenticate(alumniDTOInterface);
}
catch(DAOException daoException)
{
response=""+daoException.getMessage()+"|";
}
return response;
}

public String updateAlumni(String request)
{
int c1,c2;
String enrollmentNo,email,phoneNo;
response="NE|";
c1=request.indexOf("#");
c2=request.indexOf("#",c1+1);
enrollmentNo=request.substring(0,c1);
email=request.substring(c1+1,c2);
phoneNo=request.substring(c2+1);
AlumniDTOInterface alumniDTOInterface=new AlumniDTO();
alumniDTOInterface.setEnrollmentNumber(enrollmentNo);
alumniDTOInterface.setEmailAddress(email);
alumniDTOInterface.setPhoneNumber(phoneNo);
AlumniDAOInterface alumniDAOInterface=new AlumniDAO();
try
{
alumniDAOInterface.updateAlumni(alumniDTOInterface);
}
catch(DAOException daoException)
{
response=""+daoException.getMessage()+"|";
}
return response;
}

public String getAlumniInfo(String request)
{
String response=null;
String enrollmentNo=request;
AlumniDTOInterface alumniDTOInterface;
AlumniDAOInterface alumniDAOInterface=new AlumniDAO();
try
{
alumniDTOInterface=alumniDAOInterface.getAlumniInfo(enrollmentNo);
response="NE#"+alumniDTOInterface.getEnrollmentNumber()+"#"+alumniDTOInterface.getEmailAddress()+"#"+alumniDTOInterface.getFirstName()+"#"+alumniDTOInterface.getLastName()+"#"+alumniDTOInterface.getGender()+"#"+alumniDTOInterface.getPhoneNumber()+"#"+alumniDTOInterface.getYearOfAdmission()+"|";
}
catch(DAOException daoException)
{
response=""+daoException.getMessage()+"|";
}
return response;
}

public String changePassword(String request)
{
String response="NE|";
String enrollmentNo,password;
int c;
c=request.indexOf("#");
enrollmentNo=request.substring(0,c);
password=request.substring(c+1);
AlumniDTOInterface alumniDTOInterface=new AlumniDTO();
alumniDTOInterface.setEnrollmentNumber(enrollmentNo);
alumniDTOInterface.setPassword(password);
AlumniDAOInterface alumniDAOInterface=new AlumniDAO();
try
{
alumniDAOInterface.changePassword(alumniDTOInterface);
}
catch(DAOException daoException)
{
response=""+daoException.getMessage()+"|";
}
return response;
}

public String getAll(String request)
{
String response=null;
ArrayList<AlumniDTOInterface> al=new ArrayList<AlumniDTOInterface>();
AlumniDAOInterface alumniDAOInterface=new AlumniDAO();
AlumniDTOInterface alumniDTOInterface;
try
{
al=alumniDAOInterface.getAll();
response="NE#";
for(int x=0;x<al.size();x++)
{
alumniDTOInterface=al.get(x);
response=response+alumniDTOInterface.getEnrollmentNumber()+"#"+alumniDTOInterface.getEmailAddress()+"#"+alumniDTOInterface.getFirstName()+"#"+alumniDTOInterface.getLastName()+"#"+alumniDTOInterface.getGender()+"#"+alumniDTOInterface.getPhoneNumber()+"#"+alumniDTOInterface.getYearOfAdmission()+"#"+seperator;
}
response=response+"|";
}
catch(DAOException daoException)
{
response=""+daoException.getMessage()+"|";
}
return response;
}

public String isFavoriteExists(String request)
{
	response ="NE|";
	FavoriteDTOInterface favoriteDTOInterface=new FavoriteDTO();
	FavoriteDAO favoriteDAO=new FavoriteDAO();
	int c1;
	boolean result=false;
	String enrollmentNumber;
	String favoriteEnrollmentNumber;
	c1=request.indexOf("#");
	enrollmentNumber=request.substring(0,c1);
	favoriteEnrollmentNumber=request.substring(c1+1);
	favoriteDTOInterface.setEnrollmentNumber(enrollmentNumber);
	favoriteDTOInterface.setFavoriteEnrollmentNumber(favoriteEnrollmentNumber);
	try
	{
		result=favoriteDAO.isFavoriteExists(favoriteDTOInterface);
		if(result==true)
		{
			response="NE|";
		}
		else
		{
			throw new DAOException("The person is not in Favorites");
		}
	}catch(DAOException daoException)
	{
		response=""+daoException.getMessage()+"|";
	}
	return response;
	
	
} 

}

