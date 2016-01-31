package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankNotFoundException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Konrad on 31.01.2016.
 */
public class FindBankClients extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        try {
            Bank bank = bankDAO.getBankByName("MY BANK");
            for (Client client : clientDAO.getAllClients(bank)) {
                for (Account account : accountDAO.getClientAccounts(client.getId())) {
                    client.addAccount(account);
                }
                bank.addClient(client);
            }

            List<Client> clientList = clientDAO.getAllClients(bank);

            request.getSession().setAttribute("clientList", clientList);
        } catch (DAOException | BankNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/clients.jsp").forward(request, response);
    }
}
