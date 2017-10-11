package com.app.alumni.dl.dto;
import com.app.alumni.dl.dto.interfaces.*;
public class AlumniDTO implements AlumniDTOInterface
{
private String firstName;
private String lastName;
private String enrollmentNumber;
private String phoneNumber;
private String gender;
private String password;
private String emailAddress;
private String yearOfAdmission;
public void setFirstName(String firstName)
{
this.firstName=firstName;
}
public String getFirstName()
{
return this.firstName;
}
public void setLastName(String lastName)
{
this.lastName=lastName;
}
public String getLastName()
{
return this.lastName;
}
public void setEnrollmentNumber(String enrollmentNumber)
{
this.enrollmentNumber=enrollmentNumber;
}
public String getEnrollmentNumber()
{
return this.enrollmentNumber;
}
public void setPhoneNumber(String phoneNumber)
{
this.phoneNumber=phoneNumber;
}
public String getPhoneNumber()
{
return this.phoneNumber;
}
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}
public void setEmailAddress(String emailAddress)
{
this.emailAddress=emailAddress;
}
public String getEmailAddress()
{
return this.emailAddress;
}
public void setGender(String gender)
{
this.gender=gender;
}
public String getGender()
{
return this.gender;
}
public void setYearOfAdmission(String yearOfAdmission)
{
	this.yearOfAdmission=yearOfAdmission;
}
public String getYearOfAdmission()
{
return this.yearOfAdmission;	
}

}