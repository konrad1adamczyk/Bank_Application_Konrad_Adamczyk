package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class WithdrawCommand implements Command {

//    4)    Withdraw funds from the client account (WithdrawCommand)

    @Override
    public void execute() {

        if (BankCommander.currentClient != null &&
                BankCommander.currentClient.getActiveAccount() != null) {
            try {
                float ammount = Float.parseFloat(UserInterface.getAmount("withdraw"));

                BankCommander.currentClient.getActiveAccount().withdraw(ammount);
                System.out.println("Withdraw for " + ammount + " has been made.");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotEnoughFundsException nefe) {
                System.out.println(nefe.printMessage());
            } catch (BankException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No active client or no active account.");
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.print("Withdraw funds.");
    }
}
