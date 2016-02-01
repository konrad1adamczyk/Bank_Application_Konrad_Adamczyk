package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.commands.UserInterface;
import com.luxoft.bankapp.database.AccountDAOImpl;
import com.luxoft.bankapp.database.BankDAOImpl;
import com.luxoft.bankapp.database.ClientDAOImpl;
import com.luxoft.bankapp.ecxeptions.BankException;
import com.luxoft.bankapp.ecxeptions.BankNotFoundException;
import com.luxoft.bankapp.ecxeptions.DAOException;
import com.luxoft.bankapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Konrad on 01.02.2016.
 */
public class AddNewClientServlet extends HttpServlet {
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        final String name = request.getParameter("name");
        final String city = request.getParameter("clientCity");
        final Gender gender = Gender.parseGender(request.getParameter("gender"));
        final String email = request.getParameter("email");
        final String phoneNumber = request.getParameter("phone");
        final float debt = Float.parseFloat(request.getParameter("debt"));
        final float initialOverdraft = Float.parseFloat(request.getParameter("overdraft"));

        BankDAOImpl bankDAO = new BankDAOImpl();
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        try {
            Bank bank = bankDAO.getBankByName("PKO BP");


            int clientId = clientDAO.findClientByName(bank, name).getId();

            Client client = new Client(clientId, name,gender, initialOverdraft, debt,email,phoneNumber,city);
            client.setBankId(bank.getId());

            clientDAO.save(client);

            response.sendRedirect("/newClientAdded.html");
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (BankException e) {
            e.printStackTrace();
        }
    }
}
