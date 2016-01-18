package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by KAdamczyk on 2016-01-18.
 */
public class BankDaoTest {
    private static Bank bank;

    @Before
    public static void initBank () throws ClientExistsException {
        bank = new Bank ("My Bank");

        Client client = new Client();
        client.setName ("Ivan Ivanov");
        client.setCity ("Kiev");
        client.addAccount (new CheckingAccount());
        bank.addClient(client);
    }

    @Test
    public void testInsert () {
        BankDAOImpl.save (bank);

        Bank bank2 = BankDAOImpl.loadBank ();

        assertTrue (TestService.compare (bank, bank2));
    }


    @ Test
    public void testUpdate () {
        BankDAOImpl.save(bank);

        // Make changes to Bank
        Client client2 = new Client ();
        client2.setName ("Ivan Petrov");
        client2.setCity ("New York");
        client2.addAccount (new SavingAccount());
        bank.addClient (new Client ());
        BankDAOImpl.save (bank);

        Bank bank2 = BankDAOImpl.loadBank ();

        assertTrue (TestService.compare (bank, bank2));
    }

}