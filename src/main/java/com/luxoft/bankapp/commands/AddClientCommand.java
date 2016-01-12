package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankCommander;

import java.io.IOException;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class AddClientCommand implements Command {

    @Override
    public void execute() {

        try {
            String city = UserInterface.getCity();
            String name = UserInterface.getFullName();
            String email = UserInterface.getEmail();
            String phone = UserInterface.getPhone();
            Gender gender = UserInterface.getGender();
            float initialOverdraft = Float.parseFloat(UserInterface.getOverdraft());

            Client client = new Client(name,gender, initialOverdraft,email,phone,city);
            BankCommander.currentBank.addClient(client);
            BankCommander.currentClient = client;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClientExistsException cee) {
            System.out.println(cee.printMessage());
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Add new client.");
    }
}
