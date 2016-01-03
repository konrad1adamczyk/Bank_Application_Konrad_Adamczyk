package com.luxoft.bankapp.model;

public class SavingAccount extends AbstractAccount
{
	private static final long serialVersionUID = -9131067946890048314L;
	private float balance;
	private float overdraft;

	public SavingAccount() {
		super();
	}

	public SavingAccount(String accountNumber, float balance)
	{
		super(accountNumber, balance);
	}


	public SavingAccount(float balance)
	{
		super(balance);
	}
	// printReport(), displaying information about this account: the type of
	// account and the balance.

	
	 @Override
	    public String toString() {
	        return "Saving Account have balance: " + getBalance() + " and overdraft: " + getOverdraft();
	    }


	@Override
	public String getAccountType() {
		return "s";
	}
	@Override
	public void setBalance(float balance) throws IllegalArgumentException {
		if (balance < 0) {
			throw new IllegalArgumentException();
		}
		super.setBalance(balance);
	}
}