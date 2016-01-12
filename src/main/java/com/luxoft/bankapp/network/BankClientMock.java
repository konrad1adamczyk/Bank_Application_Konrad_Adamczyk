package com.luxoft.bankapp.network;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.requests.*;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Set;


/**
 * Created by Konrad on 2016-01-10.
 */
public class BankClientMock extends BankClient implements Runnable {

    private final String username = "Adamczyk Konrad";
    private final String accountNumber = "11 317 7694 0000 0000 8266 1516";
    protected static BankService bankService = new BankServiceImpl();

    //    I withdraw only 1 $ becouse I have created 1000 the same clients,
//    so to finish my test positively I have to withfraw 1$ thousand times.
    private final float amount = 20;

    public BankClientMock(int port) {
        super(port);
    }

    @Override
    public void run() {

        Bank bank = new Bank("Nowy Bank");
        Set<Client> listOfClientsInTestBank = bankService.loadClients("resources/");
        bank.setListOfClients(listOfClientsInTestBank);
        Account activeAccount = new CheckingAccount();


        for (Client client :listOfClientsInTestBank){
            Set<Account> listOfMyAccounts = client.getListOfAccounts();
            for (Account account : listOfMyAccounts) {
                if (account.getAccountType() == "c") {
                    activeAccount = client.setActiveAccount(account);
                }
            }

            try {
                requestSocket = new Socket(SERVER, port);
                System.out.println("Connected to localhost in port " + port);
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                out.flush();

                in = new ObjectInputStream(requestSocket.getInputStream());
                message = (String) in.readObject();

                sendRequest(new LogInRequest(client.getName()));
                message = (String) in.readObject();

                sendRequest(new GetAccountsRequest());
                message = (String) in.readObject();

                System.out.println(activeAccount.getAccountNumber());

                ChangeActiveAccountRequest changeActiveAccountRequest = new ChangeActiveAccountRequest();
                changeActiveAccountRequest.setAccountNumber(activeAccount.getAccountNumber());
                sendRequest(changeActiveAccountRequest);
                message = (String) in.readObject();

//            DepositRequest depositRequest = new DepositRequest();
//            depositRequest.setDepositAmount(900);
//            sendRequest(depositRequest );
//            message = (String) in.readObject();

                WithdrawRequest withdrawRequest = new WithdrawRequest();
                withdrawRequest.setWithdrawAmount(amount);
                sendRequest(withdrawRequest);
                message = (String) in.readObject();

                sendRequest(new LogOutRequest());
                message = (String) in.readObject();

            } catch (UnknownHostException unknownHost) {
                System.err.println("You are trying to connect to an unknown host!");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    requestSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
