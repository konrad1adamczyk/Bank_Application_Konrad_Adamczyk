package com.luxoft.bankapp.model;

import java.util.*;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.listeners.ClientRegistrationListener;

public class Bank implements Report {
	private String bankName;

	public Set<Client> getListOfClients() {
		return Collections.unmodifiableSet(listOfClients);
	}

	private Set<Client> listOfClients = new TreeSet<Client>();
	private Map<String, Client> clientsMap = new TreeMap<String, Client>();

	List<ClientRegistrationListener> eventListeners = new ArrayList<ClientRegistrationListener>();


	public Bank(String bankName) {
		this.setBankName(bankName);
		listOfClients = new TreeSet<Client>();

		class PrintClientListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client client) {
				System.out.println(client.getClientSalutation() + " " + client.getName() + " is our new client");
			}
		}
		class EmailNotificationListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client client) {
				System.out.println("Notification email for client " + client.getName() + " to be sent");
			}
		}

		class DebugListener implements ClientRegistrationListener {

			@Override
			public void onClientAdded(Client client) {
				System.out.println(client.getName() + " " + new GregorianCalendar().getTime());
			}
		}

		registerEvent(new PrintClientListener());
		registerEvent(new EmailNotificationListener());
		registerEvent(new DebugListener());

	}


	private void registerEvent(ClientRegistrationListener listner) {
		eventListeners.add(listner);
	}

	public List<ClientRegistrationListener> getListeners() {
		return eventListeners;
	}

	public Client getClient(String name) throws ClientNotExistsException {
		Client searchedClient = null;
		for (Client client : listOfClients) {
			if (client.getName().equals(name))
				searchedClient = client;
		}
		if (searchedClient == null) {
//			throw new ClientNotExistsException(name);
			return searchedClient;
		}
		return null;
	}

	public void addClient(Bank bank, Client client) throws ClientExistsException {
//		Client searchedClient = null;
//		for (Client client1 : listOfClients){
//			if(client1.getName().equals(client.getName())  )
//				searchedClient = client;
//		}
//		if(searchedClient != null)
//			throw new ClientExistsException("This client already exists");
//
//		listOfClients.add(client);
//		for(ClientRegistrationListener registration : eventListeners){
//			registration.onClientAdded(client);
//		}

		if (bank.listOfClients.add(client)) {
			for (int j = 0; j < getListeners().size(); j++) {
				bank.getListeners().get(j).onClientAdded(client);
			}
		} else {
			System.out.println("Client already exists in data base ");
		}
	}

	public boolean removeClient(Client client) {
		if (!listOfClients.isEmpty()) {
			return	listOfClients.remove(client);
		}
		else return false;
	}


	public Map<String, Client> getClientsMap() {
		return Collections.unmodifiableMap(clientsMap);
	}

	public void setClientsMap(Map<String, Client> clientsMap) {
		this.clientsMap = clientsMap;
	}


	@Override
	public void printReport() {
		listOfClients.forEach(client -> client.printReport());
		System.out.println("----------------------------------------");
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void parseFeed(Map<String, String> feedMap) {
		String name = feedMap.get("name");
		String email = feedMap.get("email");
		String phone = feedMap.get("phone");
		String city = feedMap.get("city");

		Client client=null;

		if (feedMap.get("gender").equals("m")) {

			client = new Client(name, Gender.MALE, Float.parseFloat(feedMap.get("overdraft")), email, phone, city);
		} else if (feedMap.get("gender").equals("f")) {
			client = new Client(name, Gender.FEMALE, Float.parseFloat(feedMap.get("overdraft")), email, phone, city);
		}

		if (clientsMap.get(name)==null) {
			client = new Client(name);
			clientsMap.put(name, client);
		}

		client.parseFeed(feedMap);
	}


}