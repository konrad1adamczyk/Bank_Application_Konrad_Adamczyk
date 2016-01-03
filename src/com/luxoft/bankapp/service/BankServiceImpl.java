package com.luxoft.bankapp.service;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import java.io.IOException;

import java.io.*;

public class BankServiceImpl implements BankService
{
	private static final String FILE_OBJECT_DATA = "test.txt";

	@Override
	public void addClient2(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(bank, client);
	}


	@Override
	public void removeClient(Bank bank, Client client)
	{
		if (bank.removeClient(client)) {
			System.out.println("Client removed ");
		} else {
			System.out.println("No client to remove ");
		}

	}

	@Override
	public void addAccount(Client client, Account account)
	{
		client.addAccountToClient(account);

	}

	@Override
	public void setActiveAccount(Client client, Account account)
	{
		client.setActiveAccount(account);

	}

	@Override
	public Client getClient(Bank bank, String clientName) throws ClientNotExistsException {
		return bank.getClientsMap().get(clientName);
	}

	@Override
	public void saveClient(Client client) {
		try {
			FileOutputStream fos = new FileOutputStream(FILE_OBJECT_DATA);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(client);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Client loadClient() {
		Client client = null;
		try {
			FileInputStream fis = new FileInputStream(FILE_OBJECT_DATA);
			ObjectInputStream ois = new ObjectInputStream(fis);
			client=(Client) ois.readObject();
			ois.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return client;
	}

}
