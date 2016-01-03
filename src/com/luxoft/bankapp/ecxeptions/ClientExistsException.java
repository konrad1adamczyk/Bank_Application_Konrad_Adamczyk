package com.luxoft.bankapp.ecxeptions;


public class ClientExistsException extends BankException {
	private static final long serialVersionUID = 8407159239142297435L;

	private String name;

	public ClientExistsException(String name) {
		super(name);
	}


	public String printMessage() {
		return "Client with that name already exist: " + name;
	}

}
