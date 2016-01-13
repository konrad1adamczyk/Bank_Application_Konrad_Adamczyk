package com.luxoft.bankapp.ecxeptions;

/**
 * Created by Konrad on 2016-01-13.
 */
public class EmailException extends BankException {

    private static final long serialVersionUID = 7802387269237681563L;

    public EmailException(String msg) {
        super(msg);
    }

}
