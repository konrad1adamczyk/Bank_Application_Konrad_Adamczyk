package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by Konrad on 2015-12-19.
 */
public class OpenAccountCommand implements Command {
    @Override
    public void execute() {
        if (BankCommander.currentClient != null) {
            try {
                int accountType = Integer.parseInt(UserInterface.getAccountType());
                float balance = Float.parseFloat(UserInterface.getAmount("set initial balance"));

                Account account = null;
                switch (accountType) {
                    case 0: account = new SavingAccount(balance); break;
                    case 1: account = new CheckingAccount(balance); break;
                }

                BankCommander.currentClient.addAccountToClient(account);
                System.out.println("Account has been added.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No active client.");
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.print("Create new account.");
    }
}
