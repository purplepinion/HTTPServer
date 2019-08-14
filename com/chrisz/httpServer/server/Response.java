package com.chrisz.httpServer.server;

import com.chrisz.httpServer.utils.CloseUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @Description 封装Response
 */
public class Response {

    //常量
    public static final String CRLF="\r\n";
    public static final String BLANK=" ";

    //响应头
    private StringBuilder header;

    //正文长度
    private int contentLength;

    //正文
    private StringBuilder content;

    //流
    private BufferedWriter bw;

    public Response() {
        header = new StringBuilder();
        content = new StringBuilder();
        contentLength = 0;
    }

    public Response(OutputStream os){
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public Response(Socket client)  {
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            header = null;
        }
    }


    public Response print(String info){
        content.append(info);
        contentLength+=info.getBytes().length;
        return this;
    }

    public Response println(String info){
        content.append(info).append(CRLF);
        contentLength+=(info+CRLF).getBytes().length;
        return this;
    }

    private void createHeader(int statusCode){
        header.append("HTTP/1.1").append(BLANK).append(statusCode).append(BLANK);
        switch(statusCode){
            case 200:
                header.append("OK");
                break;
            case 404:
                header.append("NOT FOUND");
                break;
            case 505:
                header.append("SEVER ERROR");
                break;
        }
        header.append(CRLF);
        //2)  响应头(Response Head)
        header.append("Server:bjsxt Server/0.0.1").append(CRLF);
        header.append("Date:").append(new Date()).append(CRLF);
        header.append("Content-type:text/html;charset=GBK").append(CRLF);
        //正文长度 ：字节长度
        header.append("Content-Length:").append(contentLength).append(CRLF);
        header.append(CRLF); //分隔符
    }

    public void pushToClient(int statusCode) throws IOException {
        if(null==header){
            statusCode = 500;
            System.out.println("header 为 空...");
        }
        //1.创建头，添加头
        createHeader(statusCode);
        bw.append(header.toString());

        //2.添加正文
        bw.append(content);

        //3.刷新出去
        bw.flush();
    }

    public void close(){
        CloseUtil.closeIO(bw);
    }
}
