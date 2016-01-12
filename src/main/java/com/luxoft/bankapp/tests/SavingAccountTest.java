package com.luxoft.bankapp.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.luxoft.bankapp.ecxeptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.SavingAccount;
import com.luxoft.bankapp.ecxeptions.BankException;



public class SavingAccountTest {
    private SavingAccount savingAccount;

    @Before
    public void setUp() {
        savingAccount = new SavingAccount(1000f);
    }

    @Test
    public void setBalanceTest() {
        float balance = 1000f;
        savingAccount.setBalance(balance);

        assertEquals(savingAccount.getBalance(), balance, 0.1f);
    }


    @Test
    public void withdrawTest() {
        float balance = 1000f;
        float withdraw = 1000f;
        savingAccount.setBalance(balance);
        try {
            savingAccount.withdraw(withdraw);
        } catch (NotEnoughFundsException ex) {
            System.out.println(ex.printMessage());
        } catch (BankException e) {
            e.printStackTrace();
        }
        assertEquals(withdraw, balance, 0.1f);
    }
}