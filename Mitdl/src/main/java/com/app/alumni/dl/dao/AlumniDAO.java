package com.app.alumni.dl.dao;
import com.app.alumni.dl.exceptions.*;
import com.app.alumni.dl.connection.*;
import com.app.alumni.dl.dao.interfaces.*;
import com.app.alumni.dl.dto.interfaces.*;
import com.app.alumni.dl.dto.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class AlumniDAO implements AlumniDAOInterface
{
public void authenticate(AlumniDTOInterface alumniDTOInterface) throws DAOException
{
try
{
	Connection connection;
	connection=DAOConnection.getConnection();
	PreparedStatement preparedStatement;
	preparedStatement=connection.prepareStatement("select enrollmentNumber from alumnidata where enrollmentNumber=?");
	preparedStatement.setString(1,alumniDTOInterface.getEnrollmentNumber());
	ResultSet resultSet;
	resultSet=preparedStatement.executeQuery();
	if(resultSet.next()==false)
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Invalid EnrollmentNumber : "+alumniDTOInterface.getEnrollmentNumber());
	}
	preparedStatement=connection.prepareStatement("select password from alumnidata where enrollmentNumber=?");
	preparedStatement.setString(1,alumniDTOInterface.getEnrollmentNumber());
	resultSet=preparedStatement.executeQuery();
	resultSet.next();
	if(!resultSet.getString("password").equals(alumniDTOInterface.getPassword()))
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Invalid Password");
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();	
}catch(SQLException sqlException)
{
	System.out.println(sqlException.getMessage());
	throw new DAOException("public void authenticate(AlumniDTOInterface alumniDTOInterface) throws DAOException"+sqlException.getMessage());
}
}
public void updateAlumni(AlumniDTOInterface alumniDTOInterface) throws DAOException
{
try
{
	Connection connection;
	connection=DAOConnection.getConnection();
	PreparedStatement preparedStatement;
	preparedStatement=connection.prepareStatement("select enrollmentNumber from alumnidata where enrollmentNumber=?");
	preparedStatement.setString(1,alumniDTOInterface.getEnrollmentNumber());
	ResultSet resultSet;
	resultSet=preparedStatement.executeQuery();
	if(resultSet.next()==false)
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Invalid EnrollmentNumber : "+alumniDTOInterface.getEnrollmentNumber());
	}
	resultSet.close();
	preparedStatement.close();
	preparedStatement=connection.prepareStatement("update alumnidata set phoneNumber=?,emailAddress=? where enrollmentNumber=?");
	preparedStatement.setString(1,alumniDTOInterface.getPhoneNumber());
	preparedStatement.setString(2,alumniDTOInterface.getEmailAddress());
	preparedStatement.setString(3,alumniDTOInterface.getEnrollmentNumber());
	preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
}catch(SQLException sqlException)
{
	System.out.println(sqlException);
	throw new DAOException("public void updateAlumni(AlumniDTOInterface alumniDTOInterface) throws DAOException : "+sqlException.getMessage());
}
}
public void changePassword(AlumniDTOInterface alumniDTOInterface) throws DAOException
{
try
{
	Connection connection;
	connection=DAOConnection.getConnection();
	PreparedStatement preparedStatement;
	preparedStatement=connection.prepareStatement("select enrollmentNumber from alumnidata where enrollmentNumber=?");
	preparedStatement.setString(1,alumniDTOInterface.getEnrollmentNumber());
	ResultSet resultSet;
	resultSet=preparedStatement.executeQuery();
	if(resultSet.next()==false)
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Invalid EnrollmentNumber : "+alumniDTOInterface.getEnrollmentNumber());
	}
	resultSet.close();
	preparedStatement.close();
	preparedStatement=connection.prepareStatement("update alumnidata set password=? where enrollmentNumber=?");
	preparedStatement.setString(1,alumniDTOInterface.getPassword());
	preparedStatement.setString(2,alumniDTOInterface.getEnrollmentNumber());
	preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
}catch(SQLException sqlException)
{
	System.out.println(sqlException);
	throw new DAOException("public void changePassword(AlumniDTOInterface alumniDTOInterface) throws DAOException : "+sqlException.getMessage());
}
}

public AlumniDTOInterface getAlumniInfo(String enrollmentNumber) throws DAOException
{
	AlumniDTOInterface alumniDTOInterface=null;
try
{
	Connection connection;
	connection=DAOConnection.getConnection();
	PreparedStatement preparedStatement;
	preparedStatement=connection.prepareStatement("select * from alumnidata where enrollmentNumber=?");
	preparedStatement.setString(1,enrollmentNumber);
	ResultSet resultSet=preparedStatement.executeQuery();
	if(resultSet.next()==false)
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Invalid EnrollmentNumber : "+enrollmentNumber);
	}
	alumniDTOInterface=new AlumniDTO();
	alumniDTOInterface.setFirstName(resultSet.getString("firstName").trim());
	alumniDTOInterface.setLastName(resultSet.getString("lastName").trim());
	alumniDTOInterface.setEnrollmentNumber(enrollmentNumber);
	alumniDTOInterface.setEmailAddress(resultSet.getString("emailAddress").trim());
	alumniDTOInterface.setPhoneNumber(resultSet.getString("phoneNumber").trim());
	alumniDTOInterface.setGender(resultSet.getString("gender").trim());
	alumniDTOInterface.setPassword(resultSet.getString("password").trim());
	alumniDTOInterface.setYearOfAdmission(resultSet.getString("yearOfAdmission").trim());
	resultSet.close();
	preparedStatement.close();
	connection.close();	
}catch(SQLException sqlException)
{
	System.out.println(sqlException.getMessage());
	throw new DAOException("public void getAlumniInfo(AlumniDTOInterface alumniDTOInterface) throws DAOException"+sqlException.getMessage());
}
return alumniDTOInterface;
}
public ArrayList<AlumniDTOInterface> getAll() throws DAOException
{
	ArrayList<AlumniDTOInterface> alumnis=null;
try
{
	Connection connection;
	connection=DAOConnection.getConnection();
	Statement statement=connection.createStatement();
	ResultSet resultSet;
	resultSet=statement.executeQuery("select * from alumnidata order by firstName");
	if(resultSet.next()==false)
	{
		resultSet.close();
		statement.close();
		connection.close();
		throw new DAOException("Sorry, No alumnis present in the database....");
	}
	alumnis=new ArrayList<AlumniDTOInterface>();
	AlumniDTOInterface alumniDTOInterface;
	do
	{
	alumniDTOInterface=new AlumniDTO();
	alumniDTOInterface.setFirstName(resultSet.getString("firstName").trim());
	alumniDTOInterface.setLastName(resultSet.getString("lastName").trim());
	alumniDTOInterface.setEnrollmentNumber(resultSet.getString("enrollmentNumber").trim());
	alumniDTOInterface.setEmailAddress(resultSet.getString("emailAddress").trim());
	alumniDTOInterface.setPhoneNumber(resultSet.getString("phoneNumber").trim());
	alumniDTOInterface.setGender(resultSet.getString("gender").trim());
	alumniDTOInterface.setPassword(resultSet.getString("password").trim());
	alumniDTOInterface.setYearOfAdmission(resultSet.getString("yearOfAdmission").trim());
	alumnis.add(alumniDTOInterface);
	}while(resultSet.next());
	resultSet.close();
statement.close();
connection.close();
}catch(SQLException sqlException)
	{
		System.out.println(sqlException.getMessage());
		throw new DAOException("public AlumniDTOInterface getAll(AlumniDTOInterface alumniDTOInterface) throws DAOException : "+sqlException.getMessage());
	}
return alumnis;
}
}