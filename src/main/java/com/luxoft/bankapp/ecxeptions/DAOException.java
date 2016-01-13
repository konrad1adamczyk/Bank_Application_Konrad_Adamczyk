package com.luxoft.bankapp.ecxeptions;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = -7816569997115606242L;

    public DAOException() {}

    public DAOException(String msg) {
        super(msg);
    }

}
