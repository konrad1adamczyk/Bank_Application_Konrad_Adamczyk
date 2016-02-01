package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Konrad on 01.02.2016.
 */
public class RemoveClientServlet extends HttpServlet {

    Logger logger = Logger.getLogger("clients." + this.getClass().getName());

    BankDAOImpl bankDAO = new BankDAOImpl();
    ClientDAOImpl clientDAO = new ClientDAOImpl();
    AccountDAOImpl accountDAO = new AccountDAOImpl();
    Bank bank = new Bank();

    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        final String clientName = request.getParameter("clientName");

        if (clientName == null) {
            logger.warning("Client not found");
            throw new ServletException("No client specified.");
        }

        String clientInfo = new String();

        LoginServlet loginServlet = new LoginServlet();
        if(loginServlet.checkName(clientName)){
            try {
                bank = bankDAO.getBankByName("PKO BP");
                clientDAO.remove(bank.getClient(clientName));

            } catch (BankException e) {
                e.printStackTrace();
            } catch (DAOException e) {
                e.printStackTrace();
            } catch (ClientNotExistsException e) {
                e.printStackTrace();
            }

            request.getSession().setAttribute("clientName", clientName);
            logger.info("Client " + clientName + " removed from Bank");
            response.sendRedirect("removed.jsp");

        } else {
            response.sendRedirect("unremoved.jsp");
        }

    }

}
