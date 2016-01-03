package com.luxoft.bankapp.service;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.model.*;

import com.luxoft.bankapp.ecxeptions.BankException;

import java.util.Map;

public class BankApplication
{

	public static void main(String[] args) throws BankException, ClientExistsException, ClientNotExistsException {
		System.out.println("hello from bank app \n");

		BankApplication bankApp = new BankApplication();
		Bank pekao = new Bank("PeKaO");

		bankApp.initialize(pekao);

		printBankReport(pekao);
		

		modifyBank(pekao);
		pekao.printReport();
		System.out.println("******************************************");
		bankApp.printBankReport(pekao);
	}

	public void initialize(Bank bank) throws ClientExistsException {
		BankServiceImpl bankService = new BankServiceImpl();

		Client[] clients = new Client[]
				{ new Client("Krakow", Gender.MALE, "Saladra Dawid", 7000), new Client("Wroclaw", Gender.FEMALE, "Chrobak Monika", 4500), new Client("Warszawa", Gender.MALE, "Skurski Piotr", 2600),
				new Client("Krakow", Gender.MALE, "Tympalski Pawel", 5500), new Client("Wroclaw", Gender.FEMALE, "Szpak Aleksandra", 1400), new Client("Gdansk", Gender.MALE, "Raczkowski Przemyslaw", 4000),
				new Client("Krakow", Gender.MALE, "Rosner Pawel", 800), new Client("Wroclaw", Gender.FEMALE, "Banasik Patrycja", 900), new Client("Gdansk", Gender.FEMALE, "Shaleiko Oksana", 1000),
				new Client("Krakow", Gender.MALE, "Adamczyk Konrad", 5000), new Client("Warszawa", Gender.MALE, "Krzeminski Jaroslaw", 700), new Client("Zakopane", Gender.MALE, "Chlebda Lukasz", 200) };

		for (int i = 0; i < clients.length; i++)
		{
			bankService.addClient2(bank, clients[i]);

			AbstractAccount accountChecking = new CheckingAccount(100);
			accountChecking.setAccountNumber();
			bankService.addAccount(clients[i], accountChecking);

			AbstractAccount accountSaving = new SavingAccount(100);
			accountSaving.setAccountNumber();
			bankService.addAccount(clients[i], accountSaving);

		}

	}

	public static void modifyBank(Bank bank) throws BankException, ClientExistsException, ClientNotExistsException {
		BankServiceImpl bankService = new BankServiceImpl();
		Client client = new Client(Gender.FEMALE, "Anna Pierzga", 1200);
		
		bankService.addClient2(bank, client);

		AbstractAccount accountChecking = new CheckingAccount(1200);
		accountChecking.setAccountNumber();
		bankService.addAccount(client, accountChecking);

		AbstractAccount accountSaving = new SavingAccount(1200);
		accountSaving.setAccountNumber();
		bankService.addAccount(client, accountSaving);

		for(Map.Entry<String, Client> client1 : bank.getClientsMap().entrySet())
			for (Account account : client1.getValue().getListOfAccounts()) {
				account.withdraw(50000);
				account.deposit(700);
			}

		bankService.removeClient(bank, bank.getClient("Saladra Dawid"));
		bankService.removeClient(bank, bank.getClient("Chrobak Monika"));
		bankService.removeClient(bank, bank.getClient("Skurski Piotr"));
		bankService.removeClient(bank, bank.getClient("Tympalski Pawel"));
		bankService.removeClient(bank, bank.getClient("Szpak Aleksandra"));
		bankService.removeClient(bank, bank.getClient("Raczkowski Przemyslaw"));

	}


	private static void printBankReport(Bank bank)
	{ 
		bank.printReport();

		BankReport bankReport = new BankReport();
		bankReport.getClientsSorted(bank);
		bankReport.getBankCreditSum(bank);
		bankReport.getNumberOfClients(bank);
		bankReport.getAccountsNumber(bank);
		bankReport.getClientsByCity(bank);


	}

}
