package com.sworks.mitalumniapp.pl.dto.interfaces;

/**
 * Created by Sanidhya PC on 07/02/2016.
 */
public interface ClientDTOInterface
{
    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);

    public String getEnrollmentNumber();

    public void setEnrollmentNumber(String enrollmentNumber);

    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    public String getPassword();

    public void setPassword(String password);

    public String getEmailAddress();

    public void setEmailAddress(String emailAddress);

    public String getGender();

    public void setGender(String gender);

    public String getYearOfAdmission();

    public void setYearOfAdmission(String yearOfAdmission);

    public Boolean getIsFavourite();

    public void setIsFavourite(Boolean isFavourite);
}
