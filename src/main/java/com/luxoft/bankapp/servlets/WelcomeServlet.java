package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletOutputStream out = res.getOutputStream();
        out.println("<!DOCTYPE HTML>");
        out.println("<html>");
        out.println(" <body>");
        out.println("Hello! I'm ATM! <br>");
        out.println("<a href='login.jsp'> Login</a>");
        out.println(" </body>");
        out.println("</html>");
    }
}
