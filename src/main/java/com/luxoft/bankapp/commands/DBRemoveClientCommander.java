package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.ClientDAO;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class DBRemoveClientCommander implements Command {

    @Override
    public void execute() {

        try {
            String name = UserInterface.getFullName();
            Client client = BankCommander.currentBank.getClient(name);

            AccountDAOImpl accountDAO = new AccountDAOImpl();
            accountDAO.removeByClientId(client.getId());

            ClientDAO clientDAO = new ClientDAOImpl();
            clientDAO.remove(client);


        } catch (IOException | DAOException | ClientNotExistsException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void printCommandInfo() {
        System.out.print("Remove Client from Database **************");
    }
}
