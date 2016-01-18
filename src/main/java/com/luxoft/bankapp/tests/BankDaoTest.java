package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.*;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.TestService;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by KAdamczyk on 2016-01-18.
 */
public class BankDaoTest {
    Bank bank;
    private final String bankName = "Testowy";

    @Before
    public void initBank() throws BankException, DAOException {
        bank = new Bank(bankName);
        bank.setId(200);

        Client client = new Client(50,"Franek Dolas", Gender.MALE, 500f, 500f, "franek@gmail.com", "555333444", "Nowy York",200);


        client.addAccount(new SavingAccount());
        bank.addClient(client);

    }

    @Test
    public void test() throws DAOException, BankNotFoundException, BankException {
        BankDAOImpl bankDao = new BankDAOImpl();
        ClientDAOImpl clientDao = new ClientDAOImpl();
        AccountDAOImpl accountDao = new AccountDAOImpl();
        bankDao.save(bank);

        Bank bank2 = bankDao.getBankByName(bankName);
        for (Client client : clientDao.getAllClients(bank2)) {
            for (Account account : accountDao.getClientAccounts(client.getId())) {
                System.out.println(client);
                client.addAccount(account);
            }
            bank2.addClient(client);
        }

        assertTrue(TestService.isEquals(bank, bank2));
    }
}

//TestService, TestServiceTest, BankDaoTest