package com.chrisz.httpServer.server;

import com.chrisz.httpServer.enums.HttpStatusCode;
import com.chrisz.httpServer.servlet.Servlet;
import com.chrisz.httpServer.utils.CloseUtil;

import java.io.IOException;
import java.net.Socket;

/**
 * Dispatcher 请求分发器
 * @Description: 根据请求URL，分发请求到相应的servlet
 */
public class Dispatcher implements Runnable {

    private Socket client;

    private Request req;

    private Response rep;

    private int statusCode;

    public Dispatcher(Socket client){
        this.statusCode = HttpStatusCode.OK.value();
        this.client = client;
        try {
            this.req = new Request(client.getInputStream());
            this.rep = new Response(client.getOutputStream());
        } catch (IOException e) {
            this.statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR.value();
            System.out.println(HttpStatusCode.INTERNAL_SERVER_ERROR.getReasonPhrase());
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        try {
            System.out.println("...req.getUrl():"+req.getUrl());
            Servlet servlet = WebApp.getServlet(req.getUrl());


            if(null==servlet){
                statusCode = HttpStatusCode.NOT_FOUND.value();
                System.out.println(HttpStatusCode.NOT_FOUND.getReasonPhrase());
            }else {
                servlet.service(req,rep);
                System.out.println(HttpStatusCode.OK.getReasonPhrase());
            }

            rep.pushToClient(statusCode);
        } catch (Exception e) {

            try {
                statusCode = HttpStatusCode.INTERNAL_SERVER_ERROR.value();
                System.out.println(HttpStatusCode.INTERNAL_SERVER_ERROR.getReasonPhrase());
                rep.pushToClient(statusCode);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }finally {
            req.close();
            rep.close();
            CloseUtil.closeSocket(client);
        }


    }
}
