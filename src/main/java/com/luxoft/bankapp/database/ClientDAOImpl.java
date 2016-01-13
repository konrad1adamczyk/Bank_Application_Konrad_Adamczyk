package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.List;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {
    @Override
    public Client findClientByName(Bank bank, String name) throws DAOException {
        return null;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws DAOException {
        return null;
    }

    @Override
    public void save(Client client) throws DAOException {

    }

    @Override
    public void remove(Client client) throws DAOException {

    }
}
