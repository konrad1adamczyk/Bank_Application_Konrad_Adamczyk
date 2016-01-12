package com.luxoft.bankapp.model;

import com.luxoft.bankapp.ecxeptions.BankException;

import java.util.Map;

public interface Account extends Report{
	
	public float getBalance();
	public void setBalance(float balance);
	public void deposit(float ammount);
	public void withdraw(float ammount) throws BankException;
	public float decimalValue();
//	public int getId();
	public String getAccountType();
	public void parseFeed(Map<String,String> feed);

	public String getAccountNumber();
	public String printReport2();
}
