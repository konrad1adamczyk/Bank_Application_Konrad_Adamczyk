package com.luxoft.bankapp.commands;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public interface Command {
    void execute();
    void printCommandInfo();
}
