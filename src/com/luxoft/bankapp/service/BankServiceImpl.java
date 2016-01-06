package com.luxoft.bankapp.service;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.util.Validation;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class BankServiceImpl implements BankService
{
	private static String CLIENT_FILE;

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

	private void createFile( Client client) throws IOException{
		CLIENT_FILE = "resources/"+ client.getName() + ".txt";
		File targetFile = new File(CLIENT_FILE);
		File parent = targetFile.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		targetFile.createNewFile();
	}

	@Override
	public void saveClient(Client client) {

		CLIENT_FILE = "resources/"+ client.getName() + ".txt";

		try {
			createFile(client);
		} catch (IOException e) {
			e.printStackTrace();
		}


		try (ObjectOutputStream ous = new ObjectOutputStream( new FileOutputStream(CLIENT_FILE)))
		{
			ous.writeObject(client);


		} catch (FileNotFoundException e) {
			System.out.println("Couldn't Find the File");
//			System.exit(0);
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Set<Client> listOfClientsInTestBank=new TreeSet<Client>();

	@Override
	public Set<Client> loadClients(String folderStr) {
		File folder = new File(folderStr);

		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isFile()) {
				Client client = null;
				try (ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(fileEntry))) {
					client = (Client) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				listOfClientsInTestBank.add(client);
			}
		}
		return listOfClientsInTestBank;

	}


}
