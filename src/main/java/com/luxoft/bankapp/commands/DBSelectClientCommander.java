package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class DBSelectClientCommander implements Command  {

    @Override
    public void execute() {
        try {
            String name = UserInterface.getFullName();

            ClientDAOImpl clientDAO = new ClientDAOImpl();
            clientDAO.findClientByName(BankCommander.currentBank, name);

            BankCommander.currentClient = clientDAO.findClientByName(BankCommander.currentBank, name);
            AccountDAOImpl accountDAO = new AccountDAOImpl();

            BankCommander.currentClient.setListOfAccounts(accountDAO.getClientAccounts(BankCommander.currentClient.getId()));

            System.out.println("Active client: " + name);

            BankCommander.currentClient.printReport();
        } catch (IOException | DAOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.print("Select Client *");
    }
}
