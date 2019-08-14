package com.chrisz.httpServer.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description XML解析Handler
 * 用于XML文件解析，主要是servlet 和 servletMapping的解析
 * 需要继承org.xml.sax.helpers.DefaultHandler
 * 重写 startDocument()、startElement()、characters()、endDocument()、endElement()
 */
public class WebHandler extends DefaultHandler{

    private List<Entity> entityList;

    private List<Mapping> mappingList;

    private Entity entity;

    private Mapping mapping;

    //当前标签
    private String tag;

    //标签是否是servletMapping
    private boolean isMapping;



    /**
     * @Description XML解析整个文档开始
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {

        entityList = new ArrayList<Entity>();
        mappingList = new ArrayList<Mapping>();
    }


    /**
     * @Description XML单个标签开始，注意空元素
     * @param uri
     * @param localName
     * @param qName 标签名
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(null!=qName){
            tag = qName;

            if("servlet".equals(qName)){

                isMapping = false;
                entity = new Entity();
            }else if("servlet-mapping".equals(qName)){

                isMapping = true;
                mapping = new Mapping();
            }
        }
    }

    /**
     * @Description XML处理单个标签，注意空元素
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if(null!=tag){

            //取出标签中内容
            String str = new String(ch,start,length);

            if(isMapping){

                if("servlet-name".equals(tag)){
                    mapping.setName(str);
                }else if("url-pattern".equals(tag)){
                    mapping.getUrlPattern().add(str);
                }
            }else{
                if("servlet-name".equals(tag)){
                    entity.setName(str);
                }else if("servlet-class".equals(tag)){
                    entity.setClazz(str);
                }
            }
        }
    }

    /**
     * @Description XML单个标签结束，注意空元素
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        //todo bugger if(null!=tag)
        if(null!=qName){
            if("servlet".equals(qName)){

                entityList.add(entity);
            }else if("servlet-mapping".equals(qName)){

                mappingList.add(mapping);
            }
        }

        tag = null;
    }

    /**
     * @Description XML解析整个文档结束
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {

    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<Mapping> mappingList) {
        this.mappingList = mappingList;
    }
}
