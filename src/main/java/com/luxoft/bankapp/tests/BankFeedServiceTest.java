package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.service.BankApplication;
import com.luxoft.bankapp.service.BankFeedService;

/**
 * Created by KAdamczyk on 2016-01-04.
 */
public class BankFeedServiceTest {

    private static final String CLIENT_FILE = "resource/";

    public static void main(String[] args) throws ClientExistsException {
        Bank testBank = new Bank("testBank");
        testBank.printReport();


        BankFeedService bfs = new BankFeedService();

        bfs.setBank(testBank);

        bfs.loadFeed(CLIENT_FILE);


        testBank.printReport();
    }

}
