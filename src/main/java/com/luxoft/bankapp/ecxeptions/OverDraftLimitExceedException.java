package com.luxoft.bankapp.ecxeptions;

import com.luxoft.bankapp.model.Account;


public class OverDraftLimitExceedException extends NotEnoughFundsException {

	private static final long serialVersionUID = -8788487512704578141L;
	/**
	 * 
	 */
	private Account account;

	public OverDraftLimitExceedException(Account account, String msg, float amount) {
		super(msg, amount);
		this.account = account;
	}

	@Override
	public String getMessage() {
		return account.toString() + " we cannot withdraw: " + super.getMessage();
	}

}
