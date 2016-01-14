package com.luxoft.bankapp.commands;

/**
 * Created by KAdamczyk on 2016-01-13.
 */
public class DBRemoveClientCommander implements Command {

    @Override
    public void execute() {

    }

    @Override
    public void printCommandInfo() {
        System.out.print("Remove Client from Database**************");
    }
}
