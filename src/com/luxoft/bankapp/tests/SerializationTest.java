package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import org.junit.Test;


/**
 * Created by Konrad on 2016-01-03.
 */
public class SerializationTest {

    @Test
    public void saveAndLoadClientTest() {

        BankService bankService = new BankServiceImpl();
        Client client = new Client("Konradek Koba", Gender.MALE, 5432f, "konradek@gmail.com", "294-543-234", "Krakow");

        bankService.saveClient(client);
//        Client recoverClient = bankService.loadClient();

//        assertTrue(client.equals(recoverClient));
    }
}



