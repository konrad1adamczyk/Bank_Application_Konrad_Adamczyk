package com.luxoft.bankapp.model;

import com.luxoft.bankapp.database.NoDB;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.listeners.ClientRegistrationListener;

import java.util.*;

public class Bank implements Report
{
	private String bankName;
	@NoDB private int id;
	@NoDB private static int counter = 0;

	@NoDB public Set<Client> getListOfClients() {
		return Collections.unmodifiableSet(listOfClients);
	}

	@NoDB public void setListOfClients(Set<Client> listOfClients) {
		this.listOfClients = listOfClients;
	}

	@NoDB private Set<Client> listOfClients=new TreeSet<Client>();
	@NoDB private Map<String, Client> clientsMap= new TreeMap<String, Client>();

	@NoDB Set<ClientRegistrationListener> eventListeners = new HashSet<ClientRegistrationListener>();


	public Bank() {
		this.id = counter++;
		listOfClients = new TreeSet<>();

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

		eventListeners = new HashSet<ClientRegistrationListener>();

		registerEvent(new PrintClientListener());
		registerEvent(new EmailNotificationListener());
		registerEvent(new DebugListener());
	}


	public Bank(String bankName) {
		this();
		this.setBankName(bankName);
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
		if (!listOfClients.isEmpty()) {
			listOfClients.remove(client);
		}
	}


//	public void removeClientByIndex(int i)
//	{
//		listOfClients.remove(i);
//	}

	public Map<String, Client> getClientsMap() {
		return Collections.unmodifiableMap(clientsMap);
	}
	public void setClientsMap(Map<String, Client> clientsMap) {
		this.clientsMap = clientsMap;
	}


	@Override
	public void printReport()
	{ int i=1;
		for (Client client : listOfClients){
			System.out.print("\n"+i+") ");
			client.printReport();
			i++;
		}
		// a method printReport(), which displays information about the bank and
		// all clients by calling client.printReport() for each client.
//		listOfClients.forEach(client -> client.printReport());
		System.out.println("----------------------------------------");
	}

	public void parseFeed(Map<String, String> feed) {
		String name = feed.get("name");

		Client client = clientsMap.get(name);
		if (client == null) {
			client = new Client(name, Float.parseFloat(feed.get("overdraft")));
			listOfClients.add(client);
			clientsMap.put(name, client);
		}

		client.parseFeed(feed);
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getNewId() {
		int newId = 0;
		for (Client client : listOfClients){

			if( client.getId()> newId ){
				newId = client.getId();
			}
		}
		return newId+1;
	}

}
