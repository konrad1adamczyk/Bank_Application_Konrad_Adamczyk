package com.luxoft.bankapp.network;

/**
 * Created by KAdamczyk on 2016-01-14.
 */

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankReport;

import java.io.Serializable;



public class BankInfo implements Serializable {

    private static final long serialVersionUID = 6261508377320038034L;

    private final Bank bank;

    public BankInfo(Bank bank) {
        this.bank = bank;
    }

    public void getStatistics() {

        BankReport.getFullReport(bank);
    }


}
