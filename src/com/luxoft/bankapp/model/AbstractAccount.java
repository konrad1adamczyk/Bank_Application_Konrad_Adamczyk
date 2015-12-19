package com.luxoft.bankapp.model;

import com.luxoft.bankapp.ecxeptions.BankException;

public abstract class AbstractAccount implements Account
{
	private float overdraft;
	private float balance;
	protected String accountNumber;


	public AbstractAccount(float balance)
	{
		this.balance = balance;
	}

	public AbstractAccount(String accountNumber, float balance)
	{
		this.balance = balance;
		this.accountNumber = accountNumber;
	}

	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(float overdraft) {
		this.overdraft = overdraft;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public String getTwoDigits()
	{
		String twoDigits = String.valueOf((int) (Math.random() * 100));
		return twoDigits;
	}

	public String getFourDigits()
	{
		String fourDigits = String.valueOf((int) (Math.random() * 10000));
		return fourDigits;
	}

	public void setAccountNumber()
	{
		StringBuilder newAccountNumber = new StringBuilder();
		newAccountNumber.append(getTwoDigits()).append(" ").append(getFourDigits()).append(" ").append(getFourDigits()).append(" ").append("0000 0000").append(" ").append(getFourDigits()).append(" ").append(getFourDigits())
				.append(" ");

		String newAccountNumberString = newAccountNumber.toString();
		this.accountNumber = newAccountNumberString;

	}

	public void withdraw(float amount) throws BankException
	{
		if (getBalance() >= amount)
		{
			balance = balance - amount;
		} else
		{
			StringBuilder sb = new StringBuilder();
			sb.append("The balance is ").append(getBalance()).append(" so withdraw of ").append(amount).append(" is anavailable.");
			System.out.println(sb);
		}

	}

	public void deposit(float amount)
	{
		balance = balance + amount;

	}

	public void printReport()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("This ").append(this.toString()).append(" with account number: ").append(getAccountNumber()).append("Has balance of ").append(getBalance()).append(" USD");
		System.out.println(sb);

	}
	@Override
	public float decimalValue() {
		return ((float) (Math.round(balance * 100)) / 100);
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		AbstractAccount other = (AbstractAccount) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}

}