package com.luxoft.bankapp.network;

import com.luxoft.bankapp.requests.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Created by Konrad on 2016-01-10.
 */
public class BankClientMock extends BankClient implements Runnable {

    private final String username = "Anna Kowalska";
    private final int id = 1;


    //    I withdraw only 1 $ becouse I have created 1000 the same clients,
//    so to finish my test positively I have to withfraw 1$ thousand times.
    private final float amount = 1;

    public BankClientMock(int port) {
        super(port);
    }

    @Override
    public void run() {
        try {
            requestSocket = new Socket(SERVER, 2004);
            System.out.println("Connected to localhost in port 2004");
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            message = (String) in.readObject();
            sendRequest(new LogInRequest(username));
            message = (String) in.readObject();
            sendRequest(new GetAccountsRequest());
            message = (String) in.readObject();
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

//    @Override
//    public Long call() throws Exception {
//        long timeBeforeConnection = Calendar.getInstance().getTimeInMillis();
//
//        try {
//            requestSocket = new Socket(SERVER, 2004);
//            System.out.println("Connected to localhost in port 2004");
//            out = new ObjectOutputStream(requestSocket.getOutputStream());
//            out.flush();
//            in = new ObjectInputStream(requestSocket.getInputStream());
//
//            message = (String) in.readObject();
//            sendRequest(new LogInRequest(username));
//            message = (String) in.readObject();
//            ChangeActiveAccountRequest changeActiveAccountRequest = new ChangeActiveAccountRequest();
//            changeActiveAccountRequest.setAccountId(id);
//            sendRequest(changeActiveAccountRequest);
//            message = (String) in.readObject();
//            WithdrawRequest withdrawRequest = new WithdrawRequest();
//            withdrawRequest.setWithdrawAmount(amount);
//            sendRequest(withdrawRequest);
//            message = (String) in.readObject();
//            sendRequest(new LogOutRequest());
//            message = (String) in.readObject();
//
//        } catch (UnknownHostException unknownHost) {
//            System.err.println("You are trying to connect to an unknown host!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                in.close();
//                out.close();
//                requestSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        long timeAfterConnection = Calendar.getInstance().getTimeInMillis();
//
//        return timeBeforeConnection - timeAfterConnection;
//    }
}
