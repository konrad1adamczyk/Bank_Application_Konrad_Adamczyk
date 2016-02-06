package com.luxoft.bankapp.database;


import com.luxoft.bankapp.ecxeptions.DAOException;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class BaseDaoImpl implements BaseDAO {
    Connection conn;
    Logger databaseLog = Logger.getLogger("database." + this.getClass().getName());
    Logger exceptionLog = Logger.getLogger("exceptions." + this.getClass().getName());



    @Override              // stara wersja połączenia z h2
    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            conn = DriverManager.getConnection("jdbc:h2:~/bankapp",
                    "sa", // login

                    "" // password
            );
            databaseLog.log(Level.SEVERE, "Connected with database");
            return conn;
        } catch(ClassNotFoundException | SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
            throw new DAOException();
        }
    }

    @Override
    public Connection closeConnection() {
        try {
            conn.close();
            databaseLog.log(Level.SEVERE, "Connection with database is closed");
        } catch(SQLException e) {
            exceptionLog.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}