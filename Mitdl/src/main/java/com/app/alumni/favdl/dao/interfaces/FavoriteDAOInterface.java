package com.app.alumni.favdl.dao.interfaces;
import com.app.alumni.dl.exceptions.*;
import com.app.alumni.favdl.dto.interfaces.*;
import com.app.alumni.favdl.dto.*;
import java.io.*;
import java.util.*;
public interface FavoriteDAOInterface
{
public void add(FavoriteDTOInterface favoriteDTOInterface) throws DAOException;
public void remove(FavoriteDTOInterface favoriteDTOInterface) throws DAOException;
public ArrayList<String> getAllFavorites(String enrollmentNumber) throws DAOException;
public boolean isFavoriteExists(FavoriteDTOInterface favoriteDTOInterface) throws DAOException;
}