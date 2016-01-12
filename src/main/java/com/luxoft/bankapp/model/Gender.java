package com.luxoft.bankapp.model;

import java.io.Serializable;

public enum Gender implements Serializable
{
	MALE("Mr"), FEMALE("Ms");
	
	private String sex;
	
	Gender(String sex)
	{
		this.sex = sex;
	}
	
	public String getClientSalutation()
	{
		return sex;
	}

	public static Gender parseGender(String sex) {
		if (sex.equals("m"))
			return Gender.MALE;
		else if (sex.equals("f") )
			return Gender.FEMALE;
		else
			return null;
	}
	
}
