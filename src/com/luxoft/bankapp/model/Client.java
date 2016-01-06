package com.luxoft.bankapp.model;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.FeedException;


import java.io.Serializable;
import java.util.*;

public class Client implements Report, Comparable<Client> , Serializable
{
	private static final long serialVersionUID = -314495632608649981L;
	private String city;
	private Gender gender;
	private String name;
	private String email;
	private String phone;

	private Set<Account> listOfAccounts = new HashSet<Account>();
//	private List<Account> listOfAccounts = new ArrayList<Account>();
	private Account activeAccount;
	private float initialOverdraft;



	public Client(String name)
	{
		this.name = name;
		this.listOfAccounts = new HashSet<Account>(2);
	}

	public Client(String name, float initialOverdraft)
	{
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.listOfAccounts = new HashSet<Account>(2);
	}

	public Client(String city, Gender gender,String name, float initialOverdraft)
	{
		this.city = city;
		this.gender = gender;
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.listOfAccounts = new HashSet<Account>(2);
	}
	public Client(Gender gender,String name, float initialOverdraft)
	{
		this.gender = gender;
		this.name = name;
		this.initialOverdraft = initialOverdraft;
		this.listOfAccounts = new HashSet<Account>(2);
	}


	public Client(String name, Gender gender,float initialOverdraft,String email, String phone, String city) {
		this(gender,name,  initialOverdraft);
		this.email = email;
		this.phone = phone;
		this.city = city;
		this.listOfAccounts = new HashSet<Account>(2);
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

	//***************************************
	public Set<Account> getListOfAccounts()
	{
		return Collections.unmodifiableSet(listOfAccounts);
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
		 System.out.print("\n" + getClientSalutation() + " " + name + ", City: " + getCity() + ", E-mail: " + getEmail()
				 + ", Phone: " + getPhone()+ ", Initial Overdraft: " + getInitialOverdraft()+"\n" );
		 listOfAccounts.forEach(account -> account.printReport());
	}

	public String printReport2()
	{
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("\n").append(getClientSalutation()).append(" ").append(name).append(", City: ").append(getCity()).append(", E-mail: ").append(getEmail());

		listOfAccounts.forEach(account -> stringBuilder.append(account.printReport2()).append("\n"));

		return stringBuilder.toString();
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
		return this.name.compareTo(client.getName());
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

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}





	public void parseFeed(Map<String, String> feed) {
		String accountType = feed.get("accounttype");
		Account account = getAccount(accountType);
		gender = Gender.parseGender(feed.get("gender"));

		account.parseFeed(feed);
	}

	private Account getAccount(String accountType) throws FeedException {
		for (Account account: listOfAccounts) {
			if (account.getAccountType().equals(accountType)) {
				return account;
			}
		}
		return createAccount(accountType);
	}

	private Account createAccount(String accountType) throws FeedException {
		Account account = null;
		if ("s".equals(accountType)) {
			account = new SavingAccount();
		} else if ("c".equals(accountType)) {
			account = new CheckingAccount();
		} else {
			throw new FeedException("Account type not found " + accountType);
		}
		listOfAccounts.add(account);

		return account;
	}

}
