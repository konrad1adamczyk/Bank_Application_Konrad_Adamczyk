package com.luxoft.bankapp.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.requests.LogOutRequest;
import com.luxoft.bankapp.requests.Request;


/**
 * Created by Konrad on 2016-01-10.
 */
public class ServerThread extends BankServer implements Runnable {
    private final Socket clientSocket;

    public ServerThread(Socket clientSocket, Bank bank) {
        super(bank);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BankServerThreaded.incrementCounter();
        try {
            System.out.println("Connection received from "
                    + clientSocket.getInetAddress().getHostName());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
            sendMessage("Connection successful");

            do {
                try {
                    request = (Request) in.readObject();
                    sendMessage(serviceRequest(request));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (!(request instanceof LogOutRequest));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BankServerThreaded.decrementCounter();

        }

    }

}
