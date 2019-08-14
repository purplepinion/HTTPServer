package com.chrisz.httpServer.server;

/**
 * @Description servlet
 *  servlet-name 和 servlet-class 映射
 *
 * <servlet>
 * <servlet-name>login</servlet-name>
 * <servlet-class>com.chrisz.httpServer.servlet.LoginServlet</servlet-class>
 * </servlet>
 */
public class Entity {

    private String name;

    private String clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
