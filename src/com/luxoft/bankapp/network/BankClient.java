package com.luxoft.bankapp.network;

import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.requests.*;
import com.luxoft.bankapp.service.BankServiceImpl;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by KAdamczyk on 2016-01-04.
 */
public class BankClient {

    protected Client theClient = null;
    protected Socket requestSocket;
    protected ObjectOutputStream out;
    protected ObjectInputStream in;
    protected String message;
    protected static final String SERVER = "localhost";
    protected final int port;
    protected Request[] requestArray = {
//            new LogInRequest(),
            new GetAccountsRequest(),
            new ChangeActiveAccountRequest(),
            new DepositRequest(),
            new WithdrawRequest(),
            new LogOutRequest()};
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public BankClient(int port) {
        this.port = port;
    }

    public void printRequests() {
        int i = 1;
        for (Request request : requestArray) {
            System.out.print(i + ") ");
            request.printRequestInfo();
            i++;
        }
    }

    public void sendRequest(Request request) {
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeActiveAccount() {
        System.out.println("\nWrite account number that you wolud like to use: ");

    }

    public void deposit() {
        System.out.println("\nWrite amount of money to deposit: ");
        String amount = null;
        try {
            amount = reader.readLine();
            DepositRequest depositRequest = new DepositRequest();
            depositRequest.setDepositAmount(Float.valueOf(amount));
            sendRequest(depositRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void withdraw() {
        System.out.println("\nWrite amount of money to withdraw: ");
        String amount = null;
        try {
            amount = reader.readLine();
            WithdrawRequest withdrawRequest = new WithdrawRequest();
            withdrawRequest.setWithdrawAmount(Float.valueOf(amount));
            sendRequest(withdrawRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login() throws IOException {
        System.out.println("\nWrite your name to log in: ");

        if (theClient == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            String name = reader.readLine();
            LogInRequest logInRequest = new LogInRequest(name);
            sendRequest(logInRequest);

        } else {
            System.out.print("\nYour name is: ");
            System.out.println(theClient.getName());
        }
        System.out.println();
    }

    public void serviceRequest() throws IOException {
        try {
            login();

            printRequests();
            reader = new BufferedReader(new InputStreamReader(System.in));
            String answer = reader.readLine();

            if (answer.equals("1")) {
                sendRequest(new GetAccountsRequest());
            } else if (answer.equals("2")) {
                changeActiveAccount();
            } else if (answer.equals("3")) {
                deposit();
            } else if (answer.equals("4")) {
                withdraw();
            } else if (answer.equals("5")) {
                message = "bye";
                sendRequest(new LogOutRequest());
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }



    void run() {
        try {
            requestSocket = new Socket(SERVER, port);
            System.out.println("Connected to localhost in port " + port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            do {
                try {
                    message = (String) in.readObject();
                    System.out.println("server> " + message);
                    if(message.equals("Username incorrect")){
                        theClient = null;
                    }
                    serviceRequest();

                } catch (ClassNotFoundException classNot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    public static void main(final String args[]) {
        int port = 2800;
        BankClient client = new BankClient(port);
        client.run();
    }

}