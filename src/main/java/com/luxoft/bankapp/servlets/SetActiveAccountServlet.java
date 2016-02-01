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
import java.util.List;

/**
 * Created by Konrad on 01.02.2016.
 */
public class SetActiveAccountServlet extends HttpServlet {


    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        BankDAOImpl bankDAO = new BankDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        final String clientName = (String) request.getSession().getAttribute("clientName");

        try {
            Bank bank = bankDAO.getBankByName("PKO BP");
            Client client = clientDAO.findClientByName(bank, clientName);
            List<Account> list = (List<Account>) accountDAO.getClientAccounts(client.getId());

            request.getSession().setAttribute("list", list);
            request.getRequestDispatcher("/changeAccount.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankException e) {
            e.printStackTrace();
        }

    }


    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        int activeAccountId = Integer.valueOf(request.getParameter("accountId"));

        request.getSession().setAttribute("accountId", activeAccountId);
        response.sendRedirect("/index1.jsp");
    }
}
