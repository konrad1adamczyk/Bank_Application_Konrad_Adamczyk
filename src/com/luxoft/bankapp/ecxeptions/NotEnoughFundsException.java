package com.luxoft.bankapp.ecxeptions;

public class NotEnoughFundsException extends BankException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8634665546378752616L;

	private float amount;

	public NotEnoughFundsException(String msg){
		super(msg);
	}

	public NotEnoughFundsException(String msg, float amount) {
		super(msg);
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

	public String printMessage() {
		return super.getMessage() + " we cannot withdraw:  " + amount;
	}
}
