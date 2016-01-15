package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;
import java.util.Set;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class DBSelectBankCommande implements Command  {

    @Override
    public void execute() throws BankException, DAOException {
        try {
            String bankName = UserInterface.getBankName();

            BankDAOImpl bankDAO = new BankDAOImpl();
            BankCommander.currentBank = bankDAO.getBankByName(bankName);

            System.out.println("Active bank: " + bankName);


            ClientDAOImpl clientDAO = new ClientDAOImpl();

            Set<Client> listOfClients =clientDAO.getAllClients(BankCommander.currentBank);
            BankCommander.currentBank.setListOfClients(listOfClients);

            AccountDAOImpl accountDAO = new AccountDAOImpl();

            for (Client client : listOfClients){
                client.setListOfAccounts(accountDAO.getClientAccounts(client.getId()));
            }

            BankCommander.currentBank.printReport();
        } catch (IOException e) {
            e.printStackTrace();

        }




    }

    @Override
    public void printCommandInfo() {
        System.out.print("Select your Bank *");
    }
}
