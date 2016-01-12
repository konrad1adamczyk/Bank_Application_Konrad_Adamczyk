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

					{ new Client("Saladra Dawid", Gender.MALE, 7000, "david@gmail.com", "294-543-234","Krakow" ),
						new Client("Chrobak Monika", Gender.FEMALE, 4500, "monika@gmail.com", "125-785-456", "Wroclaw" ),
						new Client("Skurski Piotr", Gender.MALE, 2600, "piotr@gmail.com", "125-345-456", "Warszawa"),
						new Client("Tympalski Pawel", Gender.MALE, 5500, "pawel@gmail.com", "125-144-666", "Krakow"),
						new Client("Szpak Aleksandra", Gender.FEMALE, 1400, "alexandra@gmail.com", "132-245-643", "Wroclaw"),
						new Client("Raczkowski Przemyslaw", Gender.MALE, 4000, "pryemyslaw@gmail.com", "125-723-235", "Gdansk"),
						new Client("Rosner Pawel", Gender.MALE, 800, "rosner@gmail.com", "253-715-476", "Krakow"),
						new Client("Banasik Patrycja", Gender.FEMALE, 900, "patrycja@gmail.com", "125-341-634", "Wroclaw"),
						new Client("Shaleiko Oksana", Gender.FEMALE, 1000, "oksana@gmail.com", "253-253-116", "Gdansk"),
						new Client("Adamczyk Konrad", Gender.MALE, 5000, "konrad1adamczyk@gmail.com", "609-000-114", "Krakow"),
						new Client("Krzeminski Jaroslaw", Gender.MALE, 700, "jaro@gmail.com", "232-466-116", "Warszawa"),
						new Client("Chlebda Lukasz", Gender.MALE, 200, "lukasz@gmail.com", "125-453-343", "Zakopane") };

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
