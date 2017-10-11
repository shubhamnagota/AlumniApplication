package com.app.alumni.dl.connection;
import com.app.alumni.dl.exceptions.*;
import java.sql.*;
public class DAOConnection
{
private DAOConnection()
{
}
public static Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("org.sqlite.JDBC");
connection=DriverManager.getConnection("jdbc:sqlite:alumni.db");
}catch(ClassNotFoundException classNotFoundException)
{
//logger
throw new DAOException(classNotFoundException.getMessage());
}
catch(SQLException sqlException)
{
//logger 
throw new DAOException(sqlException.getMessage());
}
return connection;
}
}