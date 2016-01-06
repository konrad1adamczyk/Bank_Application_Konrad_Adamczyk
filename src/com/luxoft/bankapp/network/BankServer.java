package com.luxoft.bankapp.network;

import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.ecxeptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.requests.*;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;


/**
 * Created by KAdamczyk on 2016-01-04.
 */
public class BankServer {

    //    protected int serverPort;
    protected Account activeAccount;
    protected Bank activeBank;
    protected static BankService bankService = new BankServiceImpl();   // nie potrzebny static****************************
    protected Client loggedClient;

    protected ServerSocket providerSocket;
    protected Socket connection = null;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    protected Request request;

    private int serverPort = 2800 ;

    public BankServer(Bank bank) {
        activeBank = bank;
    }

    public Bank getActiveBank() { return activeBank ;}

    public String loginService(Request request) {
        try {
            loggedClient = bankService.getClient(activeBank, ((LogInRequest) request).getLogin());
            StringBuilder sb = new StringBuilder();
            sb.append(loggedClient.getName()).append(" you have successfully logged in to the system " +activeBank.getBankName());
            return sb.toString();

        } catch (ClientNotExistsException e) {
            return e.printMessage();
        }
    }

    public String getAccountService() {

        String infoAboutClient = loggedClient.printReport2();

        return infoAboutClient;
    }

    public static Account getActiveAccount(Set<Account> accounts ,String accountNumber) throws IOException {

        for (Iterator<Account> it = accounts.iterator(); it.hasNext(); ) {
            Account ac = it.next();
            if (ac.getAccountNumber().trim().equals(accountNumber.trim())){
                return ac;
            } else {
                System.out.print("Un correct account number****! ");
            }
            ac.printReport();
        }

        return null;
    }


    public String changeActiveAccountService(Request request) {
        if (loggedClient != null && loggedClient.getActiveAccount() != null) {
            try {
                System.out.println("jestem tuttaj ");
                Set<Account> accounts = loggedClient.getListOfAccounts();
                activeAccount = getActiveAccount(accounts,((ChangeActiveAccountRequest) request).getAccountNumber() );

                loggedClient.setActiveAccount(activeAccount);
                System.out.println("Choosen account: ");
                activeAccount.printReport();

                return "Active account set";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("czy moze tuttaj :>");
            return "No active client or no accounts.";
        }
        return null;

    }


    public String withdrawService(Request request) {
        try {
            loggedClient.getActiveAccount().withdraw(((WithdrawRequest) request).getWithdrawAmount());
            return "Withdraw completed";
        } catch (NotEnoughFundsException e) {
            return "You have not enough funds on your account";
        } catch (BankException e) {
            return e.toString();
        }
    }

    public String depositService(Request request) {
        loggedClient.getActiveAccount().deposit(((DepositRequest) request).getDepositAmount());
        return "You have deposited " + ((DepositRequest) request).getDepositAmount();
    }

    public String serviceRequest(Request request) throws IOException {
        if (request.getClass() == LogInRequest.class) {
            return loginService(request);
        } else if (request.getClass() == GetAccountsRequest.class) {
            return getAccountService();
        } else if (request.getClass() == ChangeActiveAccountRequest.class) {
            return changeActiveAccountService(request);
        } else if (request.getClass() == DepositRequest.class) {
            return depositService(request);
        } else if (request.getClass() == WithdrawRequest.class) {
            return withdrawService(request);
        } else if (request.getClass() == LogOutRequest.class) {
            return "You have been logged out";
        }
        return "Incorrect command";
    }


    void run() {
//        activeBank.printReport();

        System.out.println("==============");
        System.out.println("localhost:" + serverPort);
        System.out.println("==============");

        try {
            // 1. creating a server socket
            providerSocket = new ServerSocket(serverPort, 10);
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection successful");
            // 4. The two parts communicate via the input and output streams
            do {
                try {
                    request = (Request) in.readObject();
                    sendMessage(serviceRequest(request));

                } catch (ClassNotFoundException classnot) {
                    classnot.printStackTrace();
                }

            } while (!(request instanceof LogOutRequest));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {

        Bank bank = new Bank("Nowy Bank");
        Set<Client> listOfClientsInTestBank = bankService.loadClients("resources/");
        bank.setListOfClients(listOfClientsInTestBank);
        bank.printReport();

        BankServer server = new BankServer(bank);
        while (true) {
            server.run();
        }
    }



}