package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.util.Set;

/**
 * Created by KAdamczyk on 2016-01-14.
 */
public class DBReportCommander implements Command  {

    @Override
    public void execute() throws BankException, DAOException {

        BankDAOImpl bankDAO = new BankDAOImpl();
        BankCommander.currentBank = bankDAO.getBankByName("PKO BP");

        ClientDAOImpl clientDAO = new ClientDAOImpl();

        Set<Client> listOfClients =clientDAO.getAllClients( BankCommander.currentBank);
        BankCommander.currentBank.setListOfClients(listOfClients);

        AccountDAOImpl accountDAO = new AccountDAOImpl();

        for (Client client : listOfClients){
            client.setListOfAccounts(accountDAO.getClientAccounts(client.getId()));
        }

        BankCommander.currentBank.printReport();
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Print information about bank *");
    }
}
