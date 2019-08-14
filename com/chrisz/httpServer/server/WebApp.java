package com.chrisz.httpServer.server;


import com.chrisz.httpServer.servlet.Servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import java.util.Map;

public class WebApp {

    private static ServletContext servletContext;

    /**
     * @Description XML文件解析
     * 将XML文件的URL和Servlet映射保存到上下文servletContext
     */
    static {

        try {
            //1.创建SAXParserFactory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            //2.创建SAXParser
            SAXParser saxParser = factory.newSAXParser();

            //3.创建WebHandler
            WebHandler webHandler = new WebHandler();

            //4.解析XML
            saxParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("WEB_INF/web.xml"),webHandler);

            //构建上下文
            servletContext = new ServletContext();


            //servlet-name 和 servlet-class 映射
            Map<String,String> servlet = servletContext.getServlet();

            for (Entity entity:webHandler.getEntityList()
                 ) {
                servlet.put(entity.getName(),entity.getClazz());
            }

            //servlet-name 和 url-pattern 映射
            Map<String,String> mapping = servletContext.getMapping();

            for (Mapping mappingOne:webHandler.getMappingList()
                 ) {

                List<String> urlPatterns = mappingOne.getUrlPattern();

                for (String url:urlPatterns
                     ) {
                    mapping.put(url,mappingOne.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description 根据url动态创建servlet
     * @param url
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Servlet getServlet(String url) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.out.println("...请求url:"+url);
        if(null==url || url.trim().equals("")){
            System.out.println("...请求url: null;");
            return null;

        }

        String servletClassName = servletContext.getServlet().get(servletContext.getMapping().get(url));
        //todo
        System.out.println("...servletClassName：" + servletClassName);
        return (Servlet)Class.forName(servletClassName).newInstance();

    }
}


//todo
/*
 *相同点：都是在JVM加载类时且在构造方法执行之前执行，在类中都可以定义多个，

 　　　　一般在代码块中对一些static变量进行赋值。

 不同点：(静态代码块—>非静态代码块—>构造方法)。

 　　　　静态代码块只在第一次new执行一次，之后不再执行
 非静态代码块在每new一次就执行一次。
 非静态代码块可在普通方法中定义(不过作用不大)，而静态代码块不行。
*/