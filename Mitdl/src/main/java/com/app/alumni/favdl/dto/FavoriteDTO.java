package com.app.alumni.favdl.dto;
import com.app.alumni.favdl.dto.interfaces.*;
public class FavoriteDTO implements FavoriteDTOInterface
{
	private String enrollmentNumber;
	private String favoriteEnrollmentNumber;
	public void setEnrollmentNumber(String enrollmentNumber)
	{
		this.enrollmentNumber=enrollmentNumber;
	}
	public String getEnrollmentNumber()
	{
		return this.enrollmentNumber;
	}
	public void setFavoriteEnrollmentNumber(String favoriteEnrollmentNumber)
	{
		this.favoriteEnrollmentNumber=favoriteEnrollmentNumber;
	}
	public String getFavoriteEnrollmentNumber()
	{
		return this.favoriteEnrollmentNumber;
	}
}