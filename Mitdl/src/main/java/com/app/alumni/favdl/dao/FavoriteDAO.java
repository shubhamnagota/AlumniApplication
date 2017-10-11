package com.app.alumni.favdl.dao;
import com.app.alumni.dl.exceptions.*;
import com.app.alumni.dl.connection.*;
import com.app.alumni.favdl.dao.interfaces.*;
import com.app.alumni.favdl.dto.interfaces.*;
import com.app.alumni.favdl.dto.*;
import java.io.*;
import java.util.*;
import java.sql.*;
public class FavoriteDAO implements FavoriteDAOInterface
{
public void add(FavoriteDTOInterface favoriteDTOInterface) throws DAOException
{
	try
	{
	Connection connection;
	connection=DAOConnection.getConnection();
	PreparedStatement preparedStatement;
	preparedStatement=connection.prepareStatement("select enrollmentNumber from alumnidata where enrollmentNumber=?");
	preparedStatement.setString(1,favoriteDTOInterface.getEnrollmentNumber());
	ResultSet resultSet;
	resultSet=preparedStatement.executeQuery();
	if(resultSet.next()==false)
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Invalid EnrollmentNumber : "+favoriteDTOInterface.getEnrollmentNumber());
	}
	preparedStatement=connection.prepareStatement("insert into favoritealumnidata values(?,?)");
	preparedStatement.setString(1,favoriteDTOInterface.getEnrollmentNumber());
	preparedStatement.setString(2,favoriteDTOInterface.getFavoriteEnrollmentNumber());
	preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();	
	}catch(SQLException sqlException)
	{
		System.out.println(sqlException.getMessage());
		throw new DAOException("public void add(FavDTOInterface favDTOInterface) throws DAOException"+sqlException.getMessage());
	}
}
public void remove(FavoriteDTOInterface favoriteDTOInterface) throws DAOException
{
	try
	{
		String enrollmentNumber=favoriteDTOInterface.getEnrollmentNumber();
		String favoriteEnrollmentNumber=favoriteDTOInterface.getFavoriteEnrollmentNumber();
		Connection connection;
		connection=DAOConnection.getConnection();
		PreparedStatement preparedStatement;
		preparedStatement=connection.prepareStatement("select favoriteEnrollmentNumber from favoritealumnidata where enrollmentNumber=? and favoriteEnrollmentNumber=?");
		preparedStatement.setString(1,enrollmentNumber);
		preparedStatement.setString(2,favoriteEnrollmentNumber);
		ResultSet resultSet;
		resultSet=preparedStatement.executeQuery();
		if(resultSet.next()==false)
		{
			resultSet.close();
			preparedStatement.close();
			connection.close();
			throw new DAOException("Invalid Favorite EnrollmentNumber : "+favoriteDTOInterface.getFavoriteEnrollmentNumber());
		}
		resultSet.close();
		preparedStatement.close();
		preparedStatement=connection.prepareStatement("delete from favoritealumnidata where enrollmentNumber=? and favoriteEnrollmentNumber=?");
		preparedStatement.setString(1,favoriteDTOInterface.getEnrollmentNumber());
		preparedStatement.setString(2,favoriteDTOInterface.getFavoriteEnrollmentNumber());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		connection.close();	
	}catch(SQLException sqlException)
	{
		System.out.println(sqlException.getMessage());
		throw new DAOException("public void remove(FavDTOInterface favDTOInterface) throws DAOException"+sqlException.getMessage());
	}
}
public ArrayList<String> getAllFavorites(String enrollmentNumber) throws DAOException
{
	ArrayList<String> favorites=null;
	FavoriteDTOInterface favoriteDTOInterface;
	try
	{
		Connection connection;
		connection=DAOConnection.getConnection();
		PreparedStatement preparedStatement;
		preparedStatement=connection.prepareStatement("select favoriteEnrollmentNumber from favoritealumnidata where enrollmentNumber=?");
		preparedStatement.setString(1,enrollmentNumber);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()==false)
	{
		resultSet.close();
		preparedStatement.close();
		connection.close();
		throw new DAOException("Sorry, You don't have any favourites...");
	}
	favorites=new ArrayList<String>();
	do
	{
		favorites.add(resultSet.getString("favoriteEnrollmentNumber").trim());
	}while(resultSet.next());
		resultSet.close();
		preparedStatement.close();
		connection.close();
	}catch(SQLException sqlException)
	{
		System.out.println(sqlException.getMessage());
		throw new DAOException("public getAll(Favoritessssssssssss) throws DAOException : "+sqlException.getMessage());
	}
		return favorites;
	}
	
	public boolean isFavoriteExists(FavoriteDTOInterface favoriteDTOInterface) throws DAOException	
	{
		try
		{
			String enrollmentNumbermentNumber=favoriteDTOInterface.getEnrollmentNumber();
			String favoriteEnrollmentNumbermentNumber=favoriteDTOInterface.getFavoriteEnrollmentNumber();
			Connection connection;
			connection=DAOConnection.getConnection();
			PreparedStatement preparedStatement;
			preparedStatement=connection.prepareStatement("select favoriteEnrollmentNumber from favoritealumnidata where enrollmentNumber=? and favoriteEnrollmentNumber=?");
			preparedStatement.setString(1,enrollmentNumbermentNumber);
			preparedStatement.setString(2,favoriteEnrollmentNumbermentNumber);
			ResultSet resultSet;
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()==false)
			{
				resultSet.close();
				preparedStatement.close();
				connection.close();
				throw new DAOException("Invalid Fav EnrollmentNumber : "+favoriteDTOInterface.getFavoriteEnrollmentNumber());
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();	
			return true;
		}catch(SQLException sqlException)
		{
		System.out.println(sqlException.getMessage());
		throw new DAOException("public void isFavoriteExists(FavDTOInterface favDTOInterface) throws DAOException"+sqlException.getMessage());
		}
	}

	
}


