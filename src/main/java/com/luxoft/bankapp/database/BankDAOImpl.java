package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class BankDAOImpl extends BaseDAOImpl implements BankDAO  {
    Connection conn;

    public Bank getBankByName(String name) throws DAOException, BankException {
        Bank bank = new Bank(name);
        String sql = "SELECT ID, NAME FROM BANK WHERE name=?";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id  = rs.getInt("ID");
                bank.setId(id);
            } else {
                throw new BankException(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } catch (BankException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return bank;
    }

    @Override
    public void save(Bank bank) throws DAOException {

    }

    @Override
    public void remove(Bank bank) throws DAOException {

    }
}