package com.luxoft.bankapp.model;

import java.util.Map;

public class CheckingAccount extends AbstractAccount
{
	private static final long serialVersionUID = -2696395068528085812L;
	private float balance;
	private float overdraft;

	public CheckingAccount() {
		super();
	}
	public CheckingAccount(float balance)
	{
		super(balance);
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

	@Override
	public void parseFeed(Map<String, String> feed) {
		super.parseFeed(feed);
		overdraft = Float.parseFloat(feed.get("overdraft"));
	}
}
