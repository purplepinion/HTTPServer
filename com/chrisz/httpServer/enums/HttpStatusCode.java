package com.chrisz.httpServer.enums;

/**
 * @Description HTTP状态码
 *
 */
public enum HttpStatusCode{

    //Informational 1xx  信息
    CONTINUE(100, "Continue"),//继续
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),// 交换协议

    //Successful 2xx  成功
    OK(200, "OK"),// ok

    //Redirection 3xx  重定向
    MULTIPLE_CHOICES(300, "Multiple Choices"),//多重选择
    MOVED_PERMANENTLY(301, "Moved Permanently"),//永久移动
    FOUND(302, "Found"),//找到
    NOT_MODIFIED(304, "Not Modified"),//未修改

    //Client Error 4xx  客户端错误
    BAD_REQUEST(400, "Bad Request"),//错误请求
    UNAUTHORIZED(401, "Unauthorized"),//未经授权
    FORBIDDEN(403, "Forbidden"),//禁止
    NOT_FOUND(404, "Not Found"),//没有找到
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),//方法不允许
    REQUEST_TIMEOUT(408, "Request Timeout"),//请求超时

    //Server Error 5xx  服务器错误
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),//内部服务器错误
    NOT_IMPLEMENTED(501, "Not Implemented"),//未实现
    BAD_GATEWAY(502, "Bad Gateway"),//错误的网关
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),//服务不可用
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),//网关超时
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),//HTTP版本不支持
    ;

    private final int statusCode;

    private final String reasonPhrase;

    private HttpStatusCode(int statusCode,String reasonPhrase){
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int value(){
        return this.statusCode;
    }

    public String getReasonPhrase(){
        return this.reasonPhrase;
    }
}
