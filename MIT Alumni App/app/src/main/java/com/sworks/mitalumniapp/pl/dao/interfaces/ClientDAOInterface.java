package com.sworks.mitalumniapp.pl.dao.interfaces;
/**
 * Created by Sanidhya PC on 07/02/2016.
 */

import com.sworks.mitalumniapp.pl.dao.exceptions.ClientDAOException;
import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

import java.util.ArrayList;

public interface ClientDAOInterface
{
    public void authenticate(ClientDTOInterface clientDTOInterface) throws ClientDAOException;

    public void updateAlumni(ClientDTOInterface clientDTOInterface) throws ClientDAOException;

    public void changePassword(ClientDTOInterface clientDTOInterface) throws ClientDAOException;

    public ClientDTOInterface getAlumniInfo(String enrollmentNumber) throws ClientDAOException;

    public ArrayList<ClientDTOInterface> getAll() throws ClientDAOException;

    public void addFavorite(String enrollmentNo, String favEnrollmentNumber) throws ClientDAOException;

    public void removeFavorite(String enrollmentNo, String favEnrollmentNumber) throws ClientDAOException;

    public ArrayList<ClientDTOInterface> getAllFavoritesForEnrollmentNumber(String enrollmentNumber) throws ClientDAOException;

    public boolean checkFavorite(String enrollmentNo, String favEnrollmentNo) throws ClientDAOException;
}
