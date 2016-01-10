package com.luxoft.bankapp.network;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Konrad on 2016-01-10.
 */
public class BankServerThreadedTest {
//    Client client = findClientByName("John Smith");
//    double amount = client.getBalance ();
//    BankClientMock clientMock = new BankClientMock(client);
//    for (int i = 0; i <1000; i ++) {
//        clientMock.start ();
//    }
//    for (int i = 0; i <1000; i ++) {
//        clientMock.join ();
//    }
//    double amount2 = client.getBalance();
//    assertEquals(amount-1000, amount2);
private final int NUMBER_OF_MOCKS_CLIENTS = 1000;

    @Test
    public void test() throws ClientExistsException, InterruptedException, IOException {
        
        BankApplication bankApplication = new BankApplication();
        bankApplication.initialize();

        Bank bank = bankApplication.getBank();

        BankServiceImpl bankService = new BankServiceImpl();
        Client client = bankService.getClient(bank, "Anna Kowalska");
        Account account = client.getActiveAccount();
        float amount1 = account.getBalance();

        BankServerThreaded bankServerThreaded = new BankServerThreaded(bank, 2004);
        Thread server = new Thread(bankServerThreaded);
        server.start();

        List<Thread> mocksClients = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_MOCKS_CLIENTS; i++) {
            mocksClients.add(new Thread(new BankClientMock()));
        }

        for (Thread thread : mocksClients) {
            thread.start();
        }

        for (Thread thread : mocksClients) {
            thread.join();
        }

        float amount2 = account.getBalance();

        assertEquals(amount1 - 1000, amount2);

    }


}
