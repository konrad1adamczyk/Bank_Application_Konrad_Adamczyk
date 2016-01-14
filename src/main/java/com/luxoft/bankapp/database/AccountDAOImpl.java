package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO {
    @Override
    public void save(Account account) throws DAOException {
        String accountType = "";
        float overdraft = 0f;
        if(account instanceof CheckingAccount) {
            accountType = "c";
            overdraft = ((CheckingAccount)account).getOverdraft();
        }
        else if(account instanceof SavingAccount) {
            accountType = "s";
            overdraft = 0f;
        }
        String sql = "UPDATE ACCOUNTS SET " +
                "CLIENT_ID=" + account.getClientId() + "," +
                "TYPE_OF_ACCOUNT=" + "'" + accountType + "'," +
                "BALANCE=" + account.getBalance() + "," +
                "OVERDRAFT=" + overdraft + "'," +
                "ACCOUNT_NUMBER='" + account.getAccountNumber() +
                "WHERE ACCOUNT_ID=" + account.getId() + ";";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                System.out.println("Account saved");
            else
                System.err.println("Problems during save");
        } catch(SQLException e) {

            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void add(Account account) throws DAOException {
        String sql = "";

        sql = "INSERT INTO ACCOUNTS VALUES (" +
                account.getId() + "," +
                account.getClientId() + "," +
                "'" + account.getAccountType() + "'," +
                + account.getBalance() + "," +
                + account.getOverdraft() + "," +
                "'" + account.getAccountNumber() + ");";

        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                System.out.println("Account added");
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public void removeByClientId(int idClient) throws DAOException {
        String sql = "DELETE FROM ACCOUNTS WHERE CLIENT_ID=" + idClient + ";";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                System.out.println("Accounts removed");
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override

    public Set<Account> getClientAccounts(int idClient) throws DAOException {
        String sql = "SELECT * FROM ACCOUNTS WHERE CLIENT_ID=" + idClient + ";";
        PreparedStatement stmt;
        Set<Account> accountsList = new HashSet<Account>();
//        List<Account> accountsList = new ArrayList<Account>();
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int accountId = rs.getInt("ACCOUNT_ID");
                int clientId = rs.getInt("CLIENT_ID");
                String typeOfAccount =  rs.getString("TYPE_OF_ACCOUNT");
                float balance = rs.getFloat("BALANCE");
                float overdraft = rs.getFloat("OVERDRAFT");
                String accountNumber = rs.getString("ACCOUNT_NUMBER");

                if(typeOfAccount.toLowerCase().equals("s")) {
                    SavingAccount account = new SavingAccount();
                    account.setId(accountId);
                    account.setClientId(clientId);
                    account.setBalance(balance);
//                    account.setOverdraft(overdraft);
                    account.setAccountNumber(accountNumber);
                    accountsList.add(account);
                }
                else if(typeOfAccount.toLowerCase().equals("c")) {
                    CheckingAccount account = new CheckingAccount(overdraft);
                    account.setId(accountId);
                    account.setClientId(clientId);
                    account.setBalance(balance);
                    account.setOverdraft(overdraft);
                    account.setAccountNumber(accountNumber);
                    accountsList.add(account);
                }
                else {
                    System.err.println("wrong account type " + typeOfAccount);
                }
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
        return accountsList;
    }
}
