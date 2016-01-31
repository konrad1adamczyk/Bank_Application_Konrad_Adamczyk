package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankNotFoundException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankReport;
import com.luxoft.bankapp.model.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Konrad on 31.01.2016.
 */
public class BankStatisticsServlet extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        float sumOfBalance = 0;

        try {
            Bank bank = bankDAO.getBankByName("MY BANK");
            for (Client client : clientDAO.getAllClients(bank)) {
                for (Account account : accountDAO.getClientAccounts(client.getId())) {
                    client.addAccount(account);
                    sumOfBalance += account.getBalance();
                }
                bank.addClient(client);
            }

            String clientsByCity = BankReport.getClientsByCityToString(bank);

            int numberOfClients = bank.getClients().size();
            request.getSession().setAttribute("sumOfBalance", sumOfBalance);
            request.getSession().setAttribute("numberOfClients", numberOfClients);
            request.getSession().setAttribute("clientsByCity", clientsByCity);
        } catch (DAOException | BankNotFoundException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/bankStatistics.jsp").forward(request, response);
    }
}
