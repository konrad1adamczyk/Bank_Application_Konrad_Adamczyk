package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public interface AccountDAO {
    public void save(Account account) throws DAOException;
    public void add(Account account) throws DAOException;
    public void removeByClientId(int idClient) throws DAOException;
    public List<Account> getClientAccounts(int idClient) throws DAOException;
}
