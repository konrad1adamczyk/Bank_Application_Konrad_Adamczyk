package com.luxoft.bankapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.listeners.ClientRegistrationListener;

public class Bank implements Report
{
	private String bankName;
	private List<Client> listOfClients;
	private List<ClientRegistrationListener> eventListeners;

	public Bank(String bankName)
	{
		this.setBankName(bankName);
		listOfClients = new ArrayList<Client>();

		class PrintClientListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client client) {
				System.out.println(client.getClientSalutation() + " " + client.getName() + " is our new client" );
			}
		}
		class EmailNotificationListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client client) {
				System.out.println("Notification email for client " + client.getName() + " to be sent" );
			}
		}

		class DebugListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client client) {
				System.out.println(client.getName() + " " + new GregorianCalendar().getTime() );
			}
		}

		eventListeners = new ArrayList<ClientRegistrationListener>();

		registerEvent(new PrintClientListener());
		registerEvent(new EmailNotificationListener());
		registerEvent(new DebugListener());

	}

	private void registerEvent(ClientRegistrationListener listner) {
		eventListeners.add(listner);
	}

	public Client getClient(String name) throws ClientNotExistsException {
		Client searchedClient = null;
		for (Client client : listOfClients){
			if(client.getName().equals(name)  )
				searchedClient = client;
		}
		if ( searchedClient == null ) throw new ClientNotExistsException(name);
		return searchedClient;
	}

	public void addClient(Client client) throws ClientExistsException  {
		Client searchedClient = null;
		for (Client client1 : listOfClients){
			if(client1.getName().equals(client.getName())  )
				searchedClient = client;
		}
		if(searchedClient != null)
			throw new ClientExistsException("This client already exists");

		listOfClients.add(client);
		for(ClientRegistrationListener registration : eventListeners){
			registration.onClientAdded(client);
		}

	}

	public void removeClient(Client client)
	{
		listOfClients.remove(client);
	}

	public void removeClientByIndex(int i)
	{
		listOfClients.remove(i);
	}

	public List<Client> getListOfClients()
	{
		return Collections.unmodifiableList(listOfClients);
	}

	@Override
	public void printReport()
	{
		// a method printReport(), which displays information about the bank and
		// all clients by calling client.printReport() for each client.
		listOfClients.forEach(client -> client.printReport());
		System.out.println("----------------------------------------");
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

}