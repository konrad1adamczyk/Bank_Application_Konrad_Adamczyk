package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Konrad on 28.01.2016.
 */
public class SessionAmountServlet extends HttpServlet {
    Logger logger = Logger.getLogger("clients." + this.getClass().getName());

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.print("Sessions: ");
        out.println(SessionListener.clientsConnected);
        out.println("</body>");
        out.println("</html>");
    }
}
