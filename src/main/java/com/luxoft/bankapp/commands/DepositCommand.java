package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class DepositCommand implements Command {

//    3)    Deposit the client account (DepositCommand)

    @Override
    public void execute() {
        if (BankCommander.currentClient != null &&
                BankCommander.currentClient.getActiveAccount() != null) {
            try {
                float ammount = Float.parseFloat(UserInterface.getAmount("deposit"));

                BankCommander.currentClient.getActiveAccount().deposit(ammount);
                System.out.println("Deposit for " + ammount + " has been made.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No active client or no active account.");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Deposit funds.");
    }
}
