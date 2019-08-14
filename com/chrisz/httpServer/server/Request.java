package com.chrisz.httpServer.server;

import com.chrisz.httpServer.utils.CloseUtil;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description 封装Request
 */
public class Request {

    //常量
    public static final String CRLF="\r\n";
    public static final String BLANK=" ";

    //请求方法
    private String method;

    //请求URL
    private String url;

    //请求参数
    private Map<String,List<String>> params;

    //流
    private InputStream is;

    private String requestInfo;

    /**
     * 空参构造器
     */
    public Request() {
        method = "";
        url = "";
        params = new HashMap<>();
    }


    /**
     *
     * @param is
     */
    public Request(InputStream is) {
        this();
        this.is = is;
        try {
            byte[] data = new byte[20480];
            int len = is.read(data);
            requestInfo = new String(data, 0, len);
        } catch (Exception e) {
            return ;
        }
        
        parseRequestInfo();
    }

    /**
     *
     */
    private void parseRequestInfo() {
        if(null==requestInfo || requestInfo.trim().equals("")){
            return;
        }

        //1.请求行处理
        String requestLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));

        int index = requestLine.indexOf("/");
        this.method = requestLine.substring(0,index).trim();

        //2.获取请求URL
        String strUrl = requestLine.substring(index,requestLine.indexOf("HTTP/")).trim();
        String strParams = "";
        if(this.method.toLowerCase().equals("post")){
            this.url = strUrl;
            strParams = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }else if(this.method.toLowerCase().equals("get")){
            if(!strUrl.contains("?")){
                this.url = strUrl;

            }else{

                String[] urlArr = strUrl.split("\\?");
                this.url = urlArr[0];
                strParams = urlArr[1];

            }
        }

        //3.封装请求参数
        if(!"".equals(strParams)){
            praseParams(strParams);
        }else{
            return;
        }


    }

    /**
     * 解码
     * @param value
     * @param code
     * @return
     */
    private String decode(String value,String code){
        try {
            return URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        return null;
    }

    /**
     *
     * @param strParams
     */
    private void praseParams(String strParams){

        //1.分割参数
        StringTokenizer tokens = new StringTokenizer(strParams,"&");

        while(tokens.hasMoreTokens()){
            String keyValue = tokens.nextToken();
            String[] keyValues = keyValue.split("=");
            if(keyValues.length==1){
                keyValues = Arrays.copyOf(keyValues,2);
                keyValues[1] = null;
            }
            String key = keyValues[0].trim();
            String value = null==keyValues[1]?null:decode(keyValues[1].trim(),"GBK");

            //2.封装进Map params
            if(!params.containsKey(key)){
                params.put(key,new ArrayList<String>());
            }

            List<String> values = params.get(key);
            values.add(value);

        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String getParameter(String name){
        String[] values = getParameters(name);

        if(null==values){
            return null;
        }
        return values[0];
    }

    /**
     *
     * @param name
     * @return
     */
    public String[] getParameters(String name){
        List<String> values = null;
        values = params.get(name);
        if(values==null){
            return null;
        }else {
            return values.toArray(new String[0]);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void close(){
        CloseUtil.closeIO(is);
    }
}
