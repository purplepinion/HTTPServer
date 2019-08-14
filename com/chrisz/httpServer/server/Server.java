package com.chrisz.httpServer.server;

import com.chrisz.httpServer.config.ServerConfig;
import com.chrisz.httpServer.utils.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {


    private  ServerSocket server;

    private boolean isShutDown = false;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(ServerConfig.MAX_REQUEST_NUMBER);

    public static void main(String[] args){
        Server server = new Server();
        server.start();
    }

    /**
     * 服务器start方法，新建一个ServerSocket 默认端口8090
     */
    public void start(){
        try {
            start(ServerConfig.SERVER_PORT);
        }catch (Exception e){
            System.out.println("启动8090端口失败...");
            stop();
        }


    }


    /**
     * 服务器start方法，新建一个ServerSocket 默认端口8090
     */
    public void start(int port){

        try {
            server = new ServerSocket(port);
            System.out.println("服务器启动在" + port + "端口...");
            this.receive();
        } catch (IOException e) {
            System.out.println("启动"+port+"端口失败...");
            stop();
        }

    }

    /**
     * 服务端receive方法，接收请求，解析请求，响应请求
     */
    private void receive(){

        try {
            while(!isShutDown){
                fixedThreadPool.execute(new Dispatcher(server.accept()));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            stop();
        }

    }

    /*
    private void receive(){

        try {
            while(!isShutDown){
                Thread thread = new Thread(new Dispatcher(server.accept()));
                System.out.println(thread.getName() + ":" +thread.getId() + " is running...");
                thread.start();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            stop();
        }

    }
     */

    private void stop(){
        isShutDown = true;
        CloseUtil.closeSocket(server);
    }
}
