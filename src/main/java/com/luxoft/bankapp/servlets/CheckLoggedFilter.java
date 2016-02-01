package com.luxoft.bankapp.servlets;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
 

/**
 * Created by Konrad on 28.01.2016.
 */
public class CheckLoggedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession();

        String clientName = (String) session.getAttribute("clientName");
        String path = ((HttpServletRequest) req).getRequestURI();
        HttpServletResponse response = (HttpServletResponse) resp;

        if (path.startsWith("/login")|| path.startsWith("/validation.js")|| path.startsWith("/css/fontello.css")
                || path.startsWith("/timer.js") || path.startsWith("/font/fontello.woff")

                || path.startsWith("/style.css") || path.startsWith("/index") || path.equals("/") ||  clientName != null) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect("/login.jsp");
        }
    }
    @Override
    public void destroy() {
    }
}
