package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.database.ClientDAO;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.DAOException;
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
            int userId = BankCommander.currentBank.getNewId();
            String name = UserInterface.getFullName();

            Gender gender = UserInterface.getGender();
            String city = UserInterface.getCity();

            String email = UserInterface.getEmail();
            String phone = UserInterface.getPhone();
            float initialOverdraft = Float.parseFloat(UserInterface.getOverdraft());
            float debt = Float.parseFloat(UserInterface.getDebt());
            int bankId = BankCommander.currentBank.getId();

            Client client = new Client(userId, name,gender, initialOverdraft, debt,email,phone,city, bankId);


            BankCommander.currentBank.addClient(client);
            BankCommander.currentClient = client;

            ClientDAO clientDAO = new ClientDAOImpl();
            clientDAO.save(client);
            client.printReport();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClientExistsException cee) {
            System.out.println(cee.printMessage());
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.print("Add new client to database.");
    }
}
