package com.luxoft.bankapp.service;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.util.Validation;

import java.io.*;

public class BankServiceImpl implements BankService
{
	private static final String CLIENT_FILE = "resources/client.txt";

	@Override
	public void addClient2(Bank bank, Client client) throws ClientExistsException {
		bank.addClient(client);

	}

	@Override
	public void removeClient(Bank bank, Client client)
	{
		bank.removeClient(client);

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
		Validation.checkForNull(clientName);

		return bank.getClient(clientName);
	}

	private void createFile() throws IOException{
		File targetFile = new File(CLIENT_FILE);
		File parent = targetFile.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		targetFile.createNewFile();
	}

	@Override
	public void saveClient(Client client) {


		try {
			createFile();
		} catch (IOException e) {
			e.printStackTrace();
		}


		try (ObjectOutputStream ous = new ObjectOutputStream( new FileOutputStream(CLIENT_FILE)))
		{
			ous.writeObject(client);
			String newLine = ";\n";
			ous.writeObject(newLine);

		} catch (FileNotFoundException e) {
			System.out.println("Couldn't Find the File");
//			System.exit(0);
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Client loadClient() {




		Client client = null;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(CLIENT_FILE))) {
			client = (Client) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			System.out.println("Couldn't Find the File");
//			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return client;
	}


}
