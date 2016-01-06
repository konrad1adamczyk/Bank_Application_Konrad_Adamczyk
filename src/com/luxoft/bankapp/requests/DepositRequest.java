package com.luxoft.bankapp.requests;

/**
 * Created by Konrad on 2016-01-04.
 */
public class DepositRequest implements Request {

    private float depositAmount;

    public float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(float depositAmount) {
        this.depositAmount = depositAmount;
    }

    @Override
    public void printRequestInfo() {
        System.out.println("Deposit");
    }

}
