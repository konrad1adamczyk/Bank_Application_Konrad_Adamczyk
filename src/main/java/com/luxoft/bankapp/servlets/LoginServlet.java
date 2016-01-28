package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by KAdamczyk on 2016-01-28.
 */
public class LoginServlet {
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException{
        final String clientName = request.getParameter("clientName");
        if (clientName == null) {
//            logger.warn("Client not fount");
            throw new ServletException("No client specified.");
        }
        request.getSession().setAttribute("clientName",clientName);
//        logger.info("Client" + clientName + "logger into ATM");

        response.sendRedirect("menu.html");
    }

}
