package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class BaseDAOImpl {
    Connection conn;
    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            conn = DriverManager.getConnection("jdbc:h2:~/bank",
                    "sa", // login

                    "" // password
            );
            return conn;
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
//        return null;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}