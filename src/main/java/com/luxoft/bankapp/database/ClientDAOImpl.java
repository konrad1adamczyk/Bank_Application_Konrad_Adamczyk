package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.ecxeptions.EmailException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO {
    @Override
    public Client findClientByName(Bank bank, String name) throws DAOException {
        String sql = "SELECT * FROM CLIENTS WHERE NAME='" + name + "';";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("CLIENT_ID");
                String clientName =  rs.getString("NAME");
                Gender gender = Gender.parseGender(rs.getString("GENDER")) ;
                float initialOverdraft = rs.getFloat("INITIALOVERDRAFT");
                float debt = rs.getFloat("DEBT");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE_NUMBER");
                String city = rs.getString("City");
                int bankId = rs.getInt("BANK_ID");

                return new Client(id, clientName, gender,initialOverdraft, debt, email, phone, city, bankId);
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws DAOException {
        String sql = "SELECT * FROM CLIENTS WHERE BANK_ID=" + bank.getId() + ";";
        PreparedStatement stmt;
        List<Client> clientsList = new ArrayList<Client>();
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("CLIENT_ID");
                String clientName =  rs.getString("NAME");
                Gender gender = Gender.parseGender(rs.getString("GENDER")) ;
                float initialOverdraft = rs.getFloat("INITIALOVERDRAFT");
                float debt = rs.getFloat("DEBT");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE_NUMBER");
                String city = rs.getString("City");
                int bankId = rs.getInt("BANK_ID");

                Client client =  new Client(id, clientName, gender,initialOverdraft, debt, email, phone, city, bankId);

                clientsList.add(client);
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
        return clientsList;
    }

    @Override
    public void save(Client client) throws DAOException {
        String sql = "";
        if(clientExistsInDB(client)){
            sql = "UPDATE CLIENTS SET " +
                    "NAME='" + client.getName() + "'," +
                    "GENDER='" + client.getGender().toSqlString() + "'," +
                    "INITIALOVERDRAFT=" + client.getInitialOverdraft() +
                    "DEBT='" + client.getDebt() + "'," +
                    "EMAIL='" + client.getEmail() + "'," +
                    "PHONE_NUMBER='" + client.getPhone() + "'," +
                    "City='" + client.getCity() + "'," +
                    "BANK_ID='" + client.getBankId() + "'," +
                    "WHERE ID=" + client.getId() + ";";
        }
        else {
            sql = "INSERT INTO CLIENTS VALUES(" +
                    client.getId() + "," +
                    "'" + client.getName() + "'," +
                    "'" + client.getGender().toSqlString() + "'," +
                    + client.getInitialOverdraft() + "," +
                    + client.getDebt() + "," +
                    "'" + client.getEmail() + "'," +
                    "'" + client.getPhone() + "'," +
                    "'" + client.getPhone() + "'," +
                    "'" + client.getPhone() + "'," +

                    client.getInitialOverdraft() + "," +
                    client.getBankId() + ");";
        }
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                System.out.println("Client saved");
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }

    }

    @Override
    public void remove(Client client) throws DAOException {

    }

    private boolean clientExistsInDB(Client client) throws DAOException {
        String sql = "SELECT * FROM CLIENTS WHERE ID=" + client.getId() + ";";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch(SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }
}
