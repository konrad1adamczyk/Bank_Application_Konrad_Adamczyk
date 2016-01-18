package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by KAdamczyk on 2016-01-18.
 */
class TestServiceTest {
    Bank bank1, bank2;

    @Before
    public void initBanks () throws ClientExistsException {
        bank1 = new Bank("My Bank");
        bank1.setId (1);
//        bank1.setName ("My Bank");
        Client client = new Client();
        client.setName ("Ivan Ivanov");
        client.setCity ("Kiev");
        // Add some fields from Client
        // Marked as @ NoDB, with different values
        // For client and client2
        client.addAccount (new CheckingAccount());
        bank1.addClient (client);
        bank2 = new BankMock ();
        bank2.setId (2);
        bank2.setName ("My Bank");
        Client client2 = new Client ();
        client2.setName ("Ivan Ivanov");
        client2.setCity ("Kiev");
        client2.addAccount (new CheckingAccount ());
        bank2.addClient (client2);
    }

    @Test
    public testEquals ()
    assertTrue(TestService.isEquals(bank1, bank2));
}
