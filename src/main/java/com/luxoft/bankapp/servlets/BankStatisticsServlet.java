package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
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
import java.util.logging.Logger;

/**
 * Created by Konrad on 01.02.2016.
 */
public class BankStatisticsServlet extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        Logger logger = Logger.getLogger("clients." + this.getClass().getName());

        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        AccountDAOImpl accountDAO = new AccountDAOImpl();
        float sumOfBalance = 0;

        try {
            Bank bank = bankDAO.getBankByName("PKO BP");
            for (Client client : clientDAO.getAllClients(bank)) {
                for (Account account : accountDAO.getClientAccounts(client.getId())) {
                    client.addAccount(account);
                    sumOfBalance += account.getBalance();
                }
                bank.addClient(client);
            }

            String clientsByCity = BankReport.getClientsByCityToString(bank);

            int numberOfClients = bank.getListOfClients().size();

            request.getSession().setAttribute("sumOfBalance", sumOfBalance);
            request.getSession().setAttribute("numberOfClients", numberOfClients);
            request.getSession().setAttribute("clientsByCity", clientsByCity);
            request.getSession().setAttribute("bankName", bank.getBankName());

            logger.info("sum of balance: " + sumOfBalance);
            logger.info("number of clients:  " + numberOfClients);
            logger.info("clients by the city: " + clientsByCity);

        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ClientExistsException e) {
            e.printStackTrace();
        } catch (BankException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/bankStatistics.jsp").forward(request, response);
    }
}
