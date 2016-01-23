package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class ClientDAOImpl extends BaseDaoImpl implements ClientDAO {
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
                float initialOverdraft = rs.getFloat("Initial_Overdraft");
                float debt = rs.getFloat("DEBT");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE_NUMBER");
                String city = rs.getString("City");
                int bankId = rs.getInt("BANK_ID");

                return new Client(id, clientName, gender,initialOverdraft, debt, email, phone, city, bankId);
            }
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public Set<Client> getAllClients(Bank bank) throws DAOException {

        String sql = "SELECT * FROM CLIENTS WHERE BANK_ID=" + bank.getId() + ";";
        PreparedStatement stmt;
        Set<Client> clientsList = new TreeSet<Client>();
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("CLIENT_ID");
                String clientName =  rs.getString("NAME");
                Gender gender = Gender.parseGender(rs.getString("GENDER")) ;
                float initialOverdraft = rs.getFloat("Initial_Overdraft");
                float debt = rs.getFloat("DEBT");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE_NUMBER");
                String city = rs.getString("City");
                int bankId = rs.getInt("BANK_ID");

                Client client =  new Client(id, clientName, gender, initialOverdraft, debt, email, phone, city, bankId);

                clientsList.add(client);
            }
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
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
//                    "CLIENT_ID='" + client.getId() + "'," +
                    "NAME='" + client.getName() + "'," +
                    "GENDER='" + client.getGender().toSqlString() + "'," +
                    "Initial_Overdraft=" + client.getInitialOverdraft() +","+
                    "DEBT='" + client.getDebt() + "'," +
                    "EMAIL='" + client.getEmail() + "'," +
                    "PHONE_NUMBER='" + client.getPhone() + "'," +
                    "City='" + client.getCity() + "'," +
                    "BANK_ID='" + client.getBankId() + "'" +
                    "WHERE CLIENT_ID=" + client.getId() + ";";
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
                    "'" + client.getCity() + "'," +
                    client.getBankId() + ");";
        }
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                databaseLog.log(Level.INFO, "Client: " + client.getName() + " saved");
//                System.out.println("Client saved");
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }

    }

    @Override
    public void remove(Client client) throws DAOException {
        String sql = "DELETE FROM CLIENTS WHERE CLIENT_ID=" + client.getId() + ";";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            if(!stmt.execute())
                databaseLog.log(Level.INFO, "Client: " + client.getName() + " removed");
//                System.out.println("Client removed");
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private boolean clientExistsInDB(Client client) throws DAOException {
        String sql = "SELECT * FROM CLIENTS WHERE CLIENT_ID=" + client.getId() + ";";
        PreparedStatement stmt;
        try {
            openConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            throw new DAOException(e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }
}
