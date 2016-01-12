package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Konrad on 2015-12-20.
 */
public class BankServiceTest {
    private Bank bank;
    private BankService bankService;

    @Before
    public void setUp() {
        bank = new Bank("bankTest");
        bankService = new BankServiceImpl();
    }

    @Test
    public void addClientTest() {
        Client client1 = new Client(Gender.FEMALE, "Basia Maj", 1000f);
        Client client2 = new Client(Gender.FEMALE, "Zdzisia Kwiecien", 1000f);
        Client client3 = new Client(Gender.FEMALE, "Basia Maj", 1000f);
        try {
            bankService.addClient2(bank, client1);
            bankService.addClient2(bank, client2);
            bankService.addClient2(bank, client3);
        } catch (ClientExistsException ex) {
            System.out.println(ex.printMessage());
        }
        assertTrue(client1.equals(client3));
    }

}