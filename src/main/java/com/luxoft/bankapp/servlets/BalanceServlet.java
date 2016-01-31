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

/**
 * Created by Konrad on 31.01.2016.
 */
public class BalanceServlet  extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String clientName = (String) request.getSession().getAttribute("clientName");
        Integer accountId = (Integer) request.getSession().getAttribute("accountId");

        if (accountId != null) {
            BankDAOImpl bankDAO = new BankDAOImpl();
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            AccountDAOImpl accountDAO = new AccountDAOImpl();
            Bank bank = null;
            try {
                bank = bankDAO.getBankByName("MY BANK");
                Client client = clientDAO.findClientByName(bank, clientName);
                Account account = accountDAO.getAccountById(accountId, client.getId());

                request.getSession().setAttribute("balance", account.getBalance());
            } catch (DAOException | BankNotFoundException e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("/balance.jsp").forward(request, response);
        } else {
            response.sendRedirect("/changeAccount");
        }

    }
}
