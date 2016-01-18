package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.TestService;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by KAdamczyk on 2016-01-18.
 */
public class TestServiceTest {
    Bank bank1, bank2;

    @Before
    public void initBanks () throws ClientExistsException {
        bank1 = new Bank();
        bank1.setId (1);
        bank1.setBankName("My Bank");

        Client client1 = new Client("Ivan Ivanov", Gender.MALE, 500f, 500f, "ivan@gmail.com", "555333444", "Kiev");

        // Add some fields from Client
        // Marked as @ NoDB, with different values
        // For client and client2

        client1.addAccount(new CheckingAccount());
        bank1.addClient(client1);


        bank2 = new Bank();
        bank2.setId(2);
        bank2.setBankName("My Bank");
        Client client2 = new Client("Ivan Ivanov", Gender.MALE, 500f, 500f, "ivan@gmail.com", "555333444", "Kiev");
        client2.addAccount(new CheckingAccount ());
        bank2.addClient (client2);

    }

    @Test
    public void testEquals(){
        assertTrue(TestService.isEquals(bank1, bank2));
    }

}
