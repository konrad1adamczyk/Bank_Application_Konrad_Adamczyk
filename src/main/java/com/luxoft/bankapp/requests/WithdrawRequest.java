package com.luxoft.bankapp.requests;

/**
 * Created by Konrad on 2016-01-04.
 */
public class WithdrawRequest implements Request {

    private float withdrawAmount;

    public float getWithdrawAmount() {
        return withdrawAmount;
    }


    public void setWithdrawAmount(float withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    @Override
    public void printRequestInfo() {
        System.out.println("Withdraw");
    }
}
