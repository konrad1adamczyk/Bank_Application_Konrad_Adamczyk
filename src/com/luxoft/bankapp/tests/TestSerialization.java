package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.util.Set;


/**
 * Created by Konrad on 2016-01-03.
 */
public class TestSerialization {
    public static Bank currentBank;

    public static Client currentClient;

    public static void main(String[] args) throws ClientExistsException {

        BankApplication bankApp = new BankApplication();
        currentBank = new Bank("bankTest");
        bankApp.initialize(currentBank);
//        currentBank.printReport();

        Set<Client> listOfClientsInitialized =  currentBank.getListOfClients();
        BankService bankService = new BankServiceImpl();

        listOfClientsInitialized.forEach(person -> bankService.saveClient(person));



//        Client client = new Client("Konradek Ad", Gender.MALE, 5432f, "konradek@gmail.com", "294-543-234", "Krakow");
//        Client client2 = new Client("Ania Ka", Gender.FEMALE, 1234f, "ania@gmail.com", "291-569-567", "Krynica");
//
//        bankService.saveClient(client);
//        bankService.saveClient(client2);

        Set<Client> listOfClientsInTestBank = bankService.loadClients("resources/");

        listOfClientsInTestBank.forEach(person -> person.printReport());





    }
}



