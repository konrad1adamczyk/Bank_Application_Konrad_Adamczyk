package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
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
 * Created by Konrad on 01.02.2016.
 */
public class DepositeServlet extends HttpServlet {
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String clientName = (String) request.getSession().getAttribute("clientName");
        final Integer accountId = (Integer) request.getSession().getAttribute("accountId");
        final Float amount = Float.parseFloat(request.getParameter("depositAmount"));

        if (accountId != null) {
            BankDAOImpl bankDAO = new BankDAOImpl();
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            AccountDAOImpl accountDAO = new AccountDAOImpl();

            try {
                Bank bank = bankDAO.getBankByName("PKO BP");
                Client client = clientDAO.findClientByName(bank, clientName);
                Account account = accountDAO.getAccountById(accountId, client.getId());

                account.deposit(amount);
                accountDAO.save(account);

                request.getRequestDispatcher("/deposite.jsp").forward(request, response);

            } catch (DAOException e) {
                e.printStackTrace();
            } catch (BankException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect("/setActiveAccount");
        }
    }
}
