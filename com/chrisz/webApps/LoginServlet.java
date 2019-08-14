package com.chrisz.webApps;

import com.chrisz.httpServer.server.Request;
import com.chrisz.httpServer.server.Response;
import com.chrisz.httpServer.servlet.Servlet;

public class LoginServlet extends Servlet {

    @Override
    protected void doGet(Request req, Response rep) throws Exception {
        System.out.println("...响应：Login..." + req.getParameter("username"));
        rep.println("Login..." + req.getParameter("username"));
    }

    @Override
    protected void doPost(Request req, Response rep) throws Exception {

    }
}
