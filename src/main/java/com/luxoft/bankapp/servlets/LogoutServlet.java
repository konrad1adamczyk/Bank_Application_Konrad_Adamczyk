package com.luxoft.bankapp.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Konrad on 31.01.2016.
 */
public class LogoutServlet extends HttpServlet {
    Logger log = Logger.getLogger("clients." + this.getClass().getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/start.html");
        log.info("Client logged out from ATM");
    }
}
