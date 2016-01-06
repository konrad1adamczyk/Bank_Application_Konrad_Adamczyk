package com.luxoft.bankapp.requests;

/**
 * Created by Konrad on 2016-01-04.
 */
public class ChangeActiveAccountRequest implements Request {

    private String accountNumber;

    @Override
    public void printRequestInfo() {
        System.out.println("Change active account");
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
}