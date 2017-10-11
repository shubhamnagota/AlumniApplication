package com.app.alumni.dl.dao.interfaces;
import com.app.alumni.dl.exceptions.*;
import com.app.alumni.dl.dto.interfaces.*;
import com.app.alumni.dl.dto.*;
import java.io.*;
import java.util.*;
public interface AlumniDAOInterface
{
public void authenticate(AlumniDTOInterface alumniDTOInterface) throws DAOException;
public void updateAlumni(AlumniDTOInterface alumniDTOInterface) throws DAOException;
public void changePassword(AlumniDTOInterface alumniDTOInterface) throws DAOException;
public AlumniDTOInterface getAlumniInfo(String enrollmentNumber) throws DAOException;
public ArrayList<AlumniDTOInterface> getAll() throws DAOException;
}