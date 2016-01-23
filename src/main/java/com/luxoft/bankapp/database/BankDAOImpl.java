package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.network.BankInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class BankDAOImpl extends BaseDaoImpl implements BankDAO  {


    public Bank getBankByName(String name) throws DAOException, BankException {
        Bank bank = new Bank(name);


        String sql = "SELECT * FROM BANKS ;";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id  = rs.getInt("BANK_ID");
                bank.setId(id);
            } else {
                throw new BankException(name);
            }
        } catch (SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
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
        String sql ="INSERT INTO BANKS VALUES("+
                bank.getId() + "," +
                "'" + bank.getBankName() +"');";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                databaseLog.log(Level.INFO, "Bank " + bank.getBankName() + " saved");
//                System.out.println("Bank saved");

            for(Client c : bank.getListOfClients()) {
                ClientDAOImpl clDaoImpl = new ClientDAOImpl();
                clDaoImpl.save(c);
            }

        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }

    }

    @Override
    public void remove(Bank bank) throws DAOException {
        String sql = "DELETE FROM BANKS WHERE BANK_NAME='" + bank.getBankName() + "');";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                databaseLog.log(Level.INFO, "Bank " + bank.getBankName() + " deleted");
//                System.out.println("Bank deleted");
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    @Override
    public BankInfo getBankInfo(Bank bank) {
        return new BankInfo(bank);
    }
}