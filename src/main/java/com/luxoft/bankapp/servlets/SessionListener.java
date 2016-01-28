package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Konrad on 28.01.2016.
 */
public class SessionListener  implements HttpSessionListener {
    Logger logger = Logger.getLogger("clients." + this.getClass().getName());
    static Integer clientsConnected;
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        final ServletContext context =
                httpSessionEvent.getSession().getServletContext();
        synchronized (SessionListener.class) {
            clientsConnected =
                    (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected == null) {
                clientsConnected = 1;
            } else {
                clientsConnected++;
                logger.log(Level.ALL, "Client connected");
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        final ServletContext context =
                httpSessionEvent.getSession().getServletContext();
        synchronized (SessionListener.class) {
            Integer clientsConnected =
                    (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected == null) {
                clientsConnected = 1;
            } else {
                clientsConnected--;
                logger.log(Level.ALL, "Client disconnected");
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }
}
