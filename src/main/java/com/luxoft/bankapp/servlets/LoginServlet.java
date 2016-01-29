package com.luxoft.bankapp.servlets;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by KAdamczyk on 2016-01-28.
 */
public class LoginServlet extends HttpServlet {
    Logger logger = Logger.getLogger("clients." + this.getClass().getName());

    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {


        final String clientName = request.getParameter("clientName");
        System.out.println(clientName + " ***************************************************************************");
        if (clientName == null) {
            logger.warning("Client not found");
            throw new ServletException("No client specified.");
        }
        request.getSession().setAttribute("clientName", clientName);

                logger.info("Client " + clientName + " logged into ATM");

        response.sendRedirect("logedin.jsp");
    }

}
