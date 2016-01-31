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
public class ChangeAccountServlet extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        BankDAOImpl bankDAO = new BankDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        final String clientName = (String) request.getSession().getAttribute("clientName");

        try {
            Bank bank = bankDAO.getBankByName("MY BANK");
            Client client = clientDAO.findClientByName(bank, clientName);
            List<Account> list = accountDAO.getClientAccounts(client.getId());

            request.getSession().setAttribute("list", list);
            request.getRequestDispatcher("/changeAccount.jsp").forward(request, response);
        } catch (DAOException | BankNotFoundException e) {
            e.printStackTrace();
        }

    }


    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        int activeAccountId = Integer.valueOf(request.getParameter("accountId"));

        request.getSession().setAttribute("accountId", activeAccountId);
        response.sendRedirect("/index1.jsp");
    }
}
