package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.ecxeptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;
import java.util.Set;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class TransferCommand implements Command {
//    5)    Make the transfer from one client account to another client account of the same bank (TransferCommand) – the command have to transfer money from the
// activeAccount of currentClient to the activeAccount of some another client (system should provide the interface to find the receiving client)

    @Override
    public void execute() {
        {

            if (BankCommander.currentClient != null &&
                    BankCommander.currentClient.getActiveAccount() != null) {
                try {
                    String name = UserInterface.getFullName();
                    Client client = BankCommander.currentBank.getClient(name);
                    Set<Account> accounts = client.getListOfAccounts();
                    Account activeAccount = UserInterface.getActiveAccount(accounts);
                    float transfer = Float.parseFloat(UserInterface.getAmount("transfer"));

                    BankCommander.currentClient.getActiveAccount().withdraw(transfer);

 // zrobić to przez wywyoływanie konta przez odniesienie do numeru konta a nie do indexu na liscie

                    activeAccount.deposit(transfer);
                    System.out.println("Transfer has been made.");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClientNotExistsException cnee) {
                    System.out.println(cnee.printMessage());
                } catch (NotEnoughFundsException nefe) {
                    System.out.println(nefe.printMessage());
                } catch (BankException be) {
                    be.printStackTrace();
                }
            } else {
                System.out.println("No active client or no active account.");
            }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Transfer funds to another account");
    }
}
