package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by KAdamczyk on 2016-01-28.
 */
public class LoginServlet extends HttpServlet {
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

        if(checkName(clientName)){
            try {
                clientInfo = bank.getClient(clientName).printReport2();
            } catch (ClientNotExistsException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("clientInfo", clientInfo);

            request.getSession().setAttribute("clientName", clientName);
            logger.info("Client " + clientName + " logged into ATM");
            response.sendRedirect("logedin.jsp");

        } else {
            response.sendRedirect("login.jsp");
        }

    }

    public boolean checkName(String name){
        try{
            bank = bankDAO.getBankByName("PKO BP");

            Set<Client> listOfClients =clientDAO.getAllClients(bank);
            bank.setListOfClients(listOfClients);


            for (Client client : listOfClients){
                client.setListOfAccounts(accountDAO.getClientAccounts(client.getId()));

                client.printReport();
            }

        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientExistsException e) {
            e.printStackTrace();
        } catch (BankException e) {
            e.printStackTrace();
        }

        Client theClient = new Client();

        System.out.println(name);
        for(Client client: bank.getListOfClients()) {

            System.out.println(client.getName());
            if ( client.getName().equals(name)){
                theClient=client;
            }
        }
        if(theClient.getName()==null){
            return false;
        }
        else if (theClient.getName().equals(name)){
            return true;
        }
        else {
            return false;
        }
    }


}
