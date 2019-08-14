package com.chrisz.httpServer.server;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description servlet-mapping
 *  servlet-name 和 url-pattern 映射
 *
 * <servlet-mapping>
 * <servlet-name>login</servlet-name>
 * <url-pattern>/login</url-pattern>
 * <url-pattern>/log</url-pattern>
 * </servlet-mapping>
 */
public class Mapping {

    private String name;

    private List<String> urlPattern;

    public Mapping(){
        urlPattern = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }
}
