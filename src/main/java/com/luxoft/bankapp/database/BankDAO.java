package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public interface BankDAO {
    /**
     * Finds Bank by its name.
     * Do not load the list of the clients.
     *
     * @ Param name
     * @ Return
     */
    Bank getBankByName(String name) throws DAOException, BankException;
    void save(Bank bank) throws DAOException;
    void remove(Bank bank) throws DAOException;

}
