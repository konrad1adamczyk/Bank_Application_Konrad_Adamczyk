
        package com.luxoft.bankapp.network;

        import com.luxoft.bankapp.requests.*;

        import java.io.IOException;
        import java.io.ObjectInputStream;
        import java.io.ObjectOutputStream;
        import java.net.Socket;
        import java.net.UnknownHostException;
        import java.util.Calendar;


/**
 * Created by Konrad on 2016-01-10.
 */
public class BankClientMockOneClient extends BankClient implements Runnable {

    private final String username = "Adamczyk Konrad";
    private final String accountNumber = "11 317 7694 0000 0000 8266 1516";

    private final float amount = 1;

    public BankClientMockOneClient(int port) {
        super(port);
    }

    @Override
    public void run() {
        try {
            requestSocket = new Socket(SERVER, port);
            System.out.println("Connected to localhost in port " + port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();

            in = new ObjectInputStream(requestSocket.getInputStream());
            message = (String) in.readObject();

            sendRequest(new LogInRequest(username));
            message = (String) in.readObject();

            sendRequest(new GetAccountsRequest());
            message = (String) in.readObject();

            ChangeActiveAccountRequest changeActiveAccountRequest = new ChangeActiveAccountRequest();
            changeActiveAccountRequest.setAccountNumber(accountNumber);
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