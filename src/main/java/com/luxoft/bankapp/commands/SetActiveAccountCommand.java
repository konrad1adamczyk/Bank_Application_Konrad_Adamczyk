package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Konrad on 2015-12-19.
 */
public class SetActiveAccountCommand implements Command {
    @Override
    public void execute() {
        if (BankCommander.currentClient != null &&
                BankCommander.currentClient.getActiveAccount() != null) {
            try {
                Set<Account> accounts = BankCommander.currentClient.getListOfAccounts();
                Account activeAccount = UserInterface.getActiveAccount(accounts);

                BankCommander.currentClient.setActiveAccount(activeAccount);
                System.out.println("Choosen account: ");
                activeAccount.printReport();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No active client or no accounts.");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Set active account.");
    }
}
