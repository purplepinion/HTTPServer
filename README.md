# HTTPServer

* 支持对url的解析，用不同的servlet响应
* 支持xml文件配置url和servlet的映射
* 使用时编写自己的CustomServlet继承com.chrisz.httpServer.servlet.Servlet类，并重写doGet(),doPost()方法
* 支持POST 和 GET 请求
* 基于Socket+SAXparser+线程池
* 默认最大30个链接，默认端口8090
* 配置类com.chrisz.httpServer.config
