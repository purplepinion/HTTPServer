package com.chrisz.httpServer.servlet;


import com.chrisz.httpServer.server.Request;
import com.chrisz.httpServer.server.Response;

/**
 * @Description servlet抽象类，提供doGet、doPost方法处理HTTP请求
 * 继承此类，重写doGet、doPost方法可自定义相应URL对应的请求
 */
public abstract class Servlet {

    public void service(Request req, Response rep) throws Exception{
        this.doGet(req, rep);
        this.doPost(req, rep);
    }

    /**
     * @Description 处理GET请求
     * @param req
     * @param rep
     * @throws Exception
     */
    protected abstract void doGet(Request req,Response rep) throws Exception;

    /**
     * @Description 处理POST请求
     * @param req
     * @param rep
     * @throws Exception
     */
    protected abstract void doPost(Request req,Response rep) throws Exception;
}
