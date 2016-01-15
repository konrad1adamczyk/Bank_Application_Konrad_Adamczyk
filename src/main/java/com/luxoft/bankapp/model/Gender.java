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
		if (sex.equalsIgnoreCase("m"))
			return Gender.MALE;
		else if (sex.equalsIgnoreCase("f") )
			return Gender.FEMALE;
		else
			return null;
	}

	public String toSqlString() {
		if(sex.equals("Mr"))
			return "M";
		else if(sex.equals("Ms"))
			return "F";
		else
			return "";
	}
	
}
