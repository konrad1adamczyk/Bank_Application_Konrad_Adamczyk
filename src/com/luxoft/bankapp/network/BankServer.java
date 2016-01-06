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
import java.util.Set;

import static com.luxoft.bankapp.service.BankCommander.currentClient;

/**
 * Created by KAdamczyk on 2016-01-04.
 */
public class BankServer {

    //    protected int serverPort;
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

    public Bank getActiveBankBank() { return activeBank ;}

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

    public String changeActiveAccountService(Request request) {
            return "No implemented";
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

            } while (!(request instanceof LogInRequest));
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