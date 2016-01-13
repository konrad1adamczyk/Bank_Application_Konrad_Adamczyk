package com.luxoft.bankapp.database;

import com.luxoft.bankapp.ecxeptions.DAOException;

import java.sql.Connection;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public interface BaseDAO {
    Connection openConnection() throws DAOException;
    Connection closeConnection();
}