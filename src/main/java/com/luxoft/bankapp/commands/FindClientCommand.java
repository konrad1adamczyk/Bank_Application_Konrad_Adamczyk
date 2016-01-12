package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class FindClientCommand implements Command {

//    Find client by his name (FindClientCommand) – comand should provide the interface for searching
// client in the current bank currentBank, and set the found client as the currentClient; all other operations
// should be done with this client; the name of the currently selected client should be shown on the screen;
    @Override
    public void execute() {
        try {
            String name = UserInterface.getFullName();

            BankCommander.currentClient = BankCommander.currentBank.getClient(name);

            System.out.println("Active client: " + name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClientNotExistsException cee) {
            System.out.println(cee.printMessage() + "\n");


        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Find client.");

    }
}
