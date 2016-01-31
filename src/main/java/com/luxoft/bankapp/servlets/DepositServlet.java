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
public class DepositServlet extends HttpServlet {
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String clientName = (String) request.getSession().getAttribute("clientName");
        final Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        final Float amount = Float.parseFloat(request.getParameter("depositAmount"));

        if (accountId != null) {
            BankDAOImpl bankDAO = new BankDAOImpl();
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            AccountDAOImpl accountDAO = new AccountDAOImpl();

            try {
                Bank bank = bankDAO.getBankByName("MY BANK");
                Client client = clientDAO.findClientByName(bank, clientName);
                Account account = accountDAO.getAccountById(accountId, client.getId());

                account.deposit(amount);
                accountDAO.save(account);

                request.getRequestDispatcher("/index1.jsp").forward(request, response);

            } catch (DAOException | BankNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("/changeAccount");
        }
    }

}
