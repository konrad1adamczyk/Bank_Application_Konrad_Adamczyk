package com.luxoft.bankapp.network;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private final int NUMBER_OF_MOCKS_CLIENTS = 5;
//    private final int NUMBER_OF_MOCKS_CLIENTS = 100;
//    private final int NUMBER_OF_MOCKS_CLIENTS = 1000;
    protected int port;
    protected static BankService bankService = new BankServiceImpl();

    @Test
    public void test() throws ClientExistsException, InterruptedException, IOException, ClientNotExistsException {
            port = 2004;
        Bank bank = new Bank("Nowy Bank");
        Set<Client> listOfClientsInTestBank = bankService.loadClients("resources/");
        bank.setListOfClients(listOfClientsInTestBank);
        bank.printReport();

        BankServerThreaded bankServerThreaded = new BankServerThreaded(bank, port);

        Account activeAcccount = new CheckingAccount();

        for (Client client1 :listOfClientsInTestBank){
           String clientName = client1.getName();
            Set<Account> listOfMyAccounts = client1.getListOfAccounts();
            for (Account account : listOfMyAccounts){
                if (account.getAccountType().equals("c")){
                    activeAcccount = client1.setActiveAccount(account);
                }
            }

            float amount1 = activeAcccount.getBalance();
//            assertEquals(amount1,100.0, 0.01);

//            BankServerThreaded bankServerThreaded = new BankServerThreaded(bank, port);
            Thread server = new Thread(bankServerThreaded);
            server.start();

            List<Thread> mocksClients = new ArrayList<>();

            for (int i = 0; i < NUMBER_OF_MOCKS_CLIENTS; i++) {
                mocksClients.add(new Thread(new BankClientMock(port)));
            }

            for (Thread thread : mocksClients) {
                thread.start();
            }

            for (Thread thread : mocksClients) {
                thread.join();
            }
//            server.join();

            float amount2 = activeAcccount.getBalance();

            assertEquals(0.0, amount2, 0.01);
//            assertEquals(amount1-100, amount2);
            System.out.println(amount1);

            System.out.println(amount2);
//            port++;

        }

//        Client client = bankService.getClient(bank, "Adamczyk Konrad");
//        Set<Account> listOfMyAccounts = client.getListOfAccounts();
//        for (Account account : listOfMyAccounts){
//            if (account.getAccountType()=="c"){
//                activeAcccount = client.setActiveAccount(account);
//            }
//        }
//
//
//        System.out.println("************************************************************************************************************");
//        System.out.println(activeAcccount.getAccountNumber());
//        System.out.println("************************************************************************************************************");
//
//        float amount1 = activeAcccount.getBalance();
//
//        BankServerThreaded bankServerThreaded = new BankServerThreaded(bank, port);
//        Thread server = new Thread(bankServerThreaded);
//        server.start();
//
//        List<Thread> mocksClients = new ArrayList<>();
//
//        for (int i = 0; i < NUMBER_OF_MOCKS_CLIENTS; i++) {
//            mocksClients.add(new Thread(new BankClientMock(port)));
//        }
//
//        for (Thread thread : mocksClients) {
//            thread.start();
//        }
//
//        for (Thread thread : mocksClients) {
//            thread.join();
//        }
//
//        float amount2 = activeAcccount.getBalance();
//
//        assertEquals(amount1 - 100, amount2);

    }
}
