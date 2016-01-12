package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.service.BankCommander;

import static com.luxoft.bankapp.service.BankCommander.*;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class GetAccountsCommand implements  Command {

//    2)    Get the list of the client accounts and the balance on each account (GetAccountsCommand)

    @Override
    public void execute() {
        if (currentClient != null) {
            if (currentClient.getActiveAccount() != null){
                currentClient.printReport();
            } else {
                System.out.println("No accounts.");
            }
        } else {
            System.out.println("No active client.");
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.print("Print client accounts.");
    }
}
