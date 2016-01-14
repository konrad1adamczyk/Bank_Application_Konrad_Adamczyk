package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.network.BankInfo;

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
        String sql = "SELECT BANK_ID, BANK_NAME FROM BANKS WHERE BANK_NAME="+name+";";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id  = rs.getInt("BANK_ID");
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

    public Bank getBankById (int id) throws DAOException, BankException {
        Bank bank = new Bank("name");
//        String sql = "SELECT BANK_ID, NAME FROM BANKS WHERE name=?;";
//        PreparedStatement stmt;
//        try {
//            openConnection();
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, name);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                int id  = rs.getInt("BANK_ID");
//                bank.setId(id);
//            } else {
//                throw new BankException(name);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DAOException();
//        } catch (BankException e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection();
//        }
        return bank;
    }


    @Override
    public void save(Bank bank) throws DAOException {
        String sql ="INSERT INTO BANKS (NAME) VALUES('" + bank.getBankName() +"');";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            for(Client c : bank.getListOfClients()) {
                ClientDAOImpl clDaoImpl = new ClientDAOImpl();
                clDaoImpl.save(c);
            }
            if(!stmt.execute())
                System.out.println("Bank saved");
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }

    }

    @Override
    public void remove(Bank bank) throws DAOException {
        String sql = "DELETE FROM BANKS WHERE NAME='" + bank.getBankName() + "');";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                System.out.println("Bank deleted");
        } catch(SQLException e) {
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