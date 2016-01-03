package com.luxoft.bankapp.model;

public class CheckingAccount extends AbstractAccount
{
	private float balance;
	private float overdraft;

	public CheckingAccount(float balance)
	{
		super(balance);
	}
	public CheckingAccount(float balance, float overdraft)
	{
		super(balance);
		this.overdraft = overdraft;
	}
	public CheckingAccount(String accountNumber, float balance)
	{
		super(accountNumber, balance);
	}

	@Override
    public String toString() {
        return "Checking Account have balance: " + getBalance() + " and overdraft: " + getOverdraft();
    }

	@Override
	public String getAccountType() {
		return "c";
	}


}
