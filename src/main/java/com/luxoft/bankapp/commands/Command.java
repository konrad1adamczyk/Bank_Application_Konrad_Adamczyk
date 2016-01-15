package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.DAOException;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public interface Command {
    void execute() throws BankException, DAOException;
    void printCommandInfo();
}
