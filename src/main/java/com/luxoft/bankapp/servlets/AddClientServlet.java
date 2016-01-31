package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankNotFoundException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Konrad on 31.01.2016.
 */
public class AddClientServlet extends HttpServlet {
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String name = request.getParameter("name");
        final String city = request.getParameter("clientCity");
        final String gender = request.getParameter("gender");
        final String email = request.getParameter("email");
        final String phoneNumber = request.getParameter("phone");
        final String account = request.getParameter("account");
        final Float overdraft = Float.valueOf(request.getParameter("overdraft"));
        final Float initialBalance = Float.valueOf(request.getParameter("initialBalance"));

        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        try {
            Bank bank = bankDAO.getBankByName("MY BANK");
            Client client = new Client();
            client.setBankId(bank.getId());
            client.setName(name);
            client.setCity(city);

            if (gender.equalsIgnoreCase("male")) {
                client.setGender(Gender.MALE);
            } else if (gender.equalsIgnoreCase("female")) {
                client.setGender(Gender.FEMALE);
            }
            client.setEmail(email);
            client.setPhoneNumber(phoneNumber);

            clientDAO.save(client);
            int clientId = clientDAO.findClientByName(bank, name).getId();
            if (account.equalsIgnoreCase("saving")) {
                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setBalance(initialBalance);
                savingAccount.setClientId(clientId);
                accountDAO.add(savingAccount);
            } else if (account.equalsIgnoreCase("checking")) {
                CheckingAccount checkingAccount = new CheckingAccount();
                checkingAccount.setOverdraft(overdraft);
                checkingAccount.setBalance(initialBalance);
                checkingAccount.setClientId(clientId);
                accountDAO.add(checkingAccount);
            }

            response.sendRedirect("/start.html");
        } catch (DAOException | BankNotFoundException e) {
            e.printStackTrace();
        }
    }
}