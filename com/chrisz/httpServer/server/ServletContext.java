package com.chrisz.httpServer.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description Servlet上下文
 * 保存xml文件的url和Servlet映射关系
 */
public class ServletContext {

    /**
     * <servlet>
     * <servlet-name>login</servlet-name>
     * <servlet-class>com.chrisz.httpServer.servlet.LoginServlet</servlet-class>
     * </servlet>
     */
    private Map<String,String> servlet;

    /**
     * <servlet-mapping>
     * <servlet-name>login</servlet-name>
     * <url-pattern>/login</url-pattern>
     * <url-pattern>/log</url-pattern>
     * </servlet-mapping>
     */
    private Map<String,String> mapping;

    public ServletContext(){
        this.servlet = new HashMap<String,String>();
        this.mapping = new HashMap<String,String>();
    }
    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
