package com.sworks.mitalumniapp.pl.dto;

import com.sworks.mitalumniapp.pl.dto.interfaces.ClientDTOInterface;

/**
 * Created by Sanidhya PC on 07/02/2016.
 */
public class ClientDTO implements ClientDTOInterface
{
    String yearOfAdmission;
    private String firstName;
    private String lastName;
    private String enrollmentNumber;
    private String phoneNumber;
    private String gender;
    private String password;
    private String emailAddress;
    private Boolean isFavourite;

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName=firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName=lastName;
    }

    public String getEnrollmentNumber()
    {
        return this.enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber)
    {
        this.enrollmentNumber=enrollmentNumber;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber=phoneNumber;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password=password;
    }

    public String getEmailAddress()
    {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress=emailAddress;
    }

    public String getGender()
    {
        return this.gender;
    }

    public void setGender(String gender)
    {
        this.gender=gender;
    }

    @Override
    public String getYearOfAdmission()
    {
        return this.yearOfAdmission;
    }

    @Override
    public void setYearOfAdmission(String yearOfAdmission)
    {
        this.yearOfAdmission=yearOfAdmission;
    }

    public Boolean getIsFavourite()
    {
        return isFavourite;
    }

    public void setIsFavourite(Boolean isFavourite)
    {
        this.isFavourite=isFavourite;
    }
}
