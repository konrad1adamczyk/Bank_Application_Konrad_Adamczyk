package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Account;

import java.util.List;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    @Override
    public void save(Account account) throws DAOException {

    }

    @Override
    public void add(Account account) throws DAOException {

    }

    @Override
    public void removeByClientId(int idClient) throws DAOException {

    }

    @Override
    public List<Account> getClientAccounts(int idClient) throws DAOException {
        return null;
    }
}
