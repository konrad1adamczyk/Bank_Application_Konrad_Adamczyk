package com.luxoft.bankapp.model;

import com.luxoft.bankapp.ecxeptions.BankException;

import java.util.*;

public class Client implements Report, Comparable<Client>
{
	private Gender gender;
	private float balance;
	private String name;
	private String email;
	private String phone;
	private List<Account> listOfAccounts = new ArrayList<Account>();
	private Account activeAccount;
	private float initialOverdraft;

	// Add the possibility to keep a gender of the bank customer. Define the
	// relevant fields and Client class constructor.


//	Client client = new Client(name,gender,initialOverdraft,email,phone);

	public Client(String name)
	{
		this.name = name;
		this.listOfAccounts = new ArrayList<Account>(2);
	}

	public Client(String name, float initialOverdraft)
	{
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.listOfAccounts = new ArrayList<Account>(2);
	}

	public Client(Gender gender,String name, float initialOverdraft)
	{
		this.gender = gender;
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.listOfAccounts = new ArrayList<Account>(2);
	}

	public Client(String name, Gender gender,float initialOverdraft,
				  String email, String phone) {
		this(gender,name,  initialOverdraft);
		this.email = email;
		this.phone = phone;
	}

	public String getName()
	{
		return name;
	}


	public float getInitialOverdraft()
	{
		return initialOverdraft;
	}

	public void setInitialOverdraft(float initialOverdraft)
	{
		this.initialOverdraft = initialOverdraft;
	}

	public void setActiveAccount(Account activeAccount)
	{
		this.activeAccount = activeAccount;
	}

	public Account getActiveAccount() {
		return activeAccount;
	}



//	************************************************************
	public void addAccountToClient(Account account){
		if (account instanceof CheckingAccount) {
			CheckingAccount checkingAccount = (CheckingAccount) account;
			checkingAccount.setOverdraft(initialOverdraft);
			listOfAccounts.add(checkingAccount);
		} else {
			listOfAccounts.add(account);
		}
		activeAccount = account;
	}
	public float getBalanceOfActiveAccount() {return activeAccount.getBalance();}

	public void deposit(float amount) {
		if(activeAccount != null) activeAccount.deposit(amount);
	}

	public void withdraw(float amount) throws BankException {
		if(activeAccount != null) activeAccount.withdraw(amount);
	}

	public List<Account> getListOfAccounts()
	{
		return Collections.unmodifiableList(listOfAccounts);
	}

	public String getClientSalutation()
	{
		return gender.getClientSalutation();
	}

	@Override
	public void printReport()
	{
		// the customer should override method printReport (), which has to
		// display information about the customer and all of its accounts
		 System.out.print("\n" + getClientSalutation() + " " + name + "\n" );
		 listOfAccounts.forEach(account -> account.printReport());

	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int compareTo(Client client) {
		if (this == client)
			return 0;
		return this.name.compareTo(client.name);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClientSalutation());

		for (Account account: listOfAccounts)
			sb.append(account);

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}