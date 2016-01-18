package com.luxoft.bankapp.ecxeptions;

/**
 * Created by Konrad on 2016-01-18.
 */
public class BankNotFoundException extends Exception {

    private static final long serialVersionUID = 8384611217627447802L;

    public BankNotFoundException(String msg) {
        super(msg);
    }

}
