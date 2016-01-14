package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.util.List;
import java.util.Set;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public interface ClientDAO {

    /**
     * Return client by its name, initialize client accounts.
     * //    * @param bank
     * //    * @param name
     *
     * @return
     */
    Client findClientByName(Bank bank, String name) throws DAOException;

    /**
     * Returns the list of all clients of the Bank
     * And their accounts
     * //     *@param bankId
     *
     * @return
     */
    Set<Client> getAllClients(Bank bank) throws DAOException;

    /**
     * Method should insert new Client (if id == null)
     * Or update client in database
     * //    * @param client
     */
    void save(Client client) throws DAOException;

    /**
     * Method removes client from Database
     * //    * @param client
     */
    void remove(Client client) throws DAOException;
}
