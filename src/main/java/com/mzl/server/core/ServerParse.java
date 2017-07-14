package com.mzl.server.core;

import com.mzl.server.util.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;

/**
 * @Author:muzonglin
 * @Description:
 * @Date:2017/7/12
 */
public class ServerParse {

    public static int getPort() {

        int port = 8080;

        try {

            SAXReader reader = new SAXReader();
            Document document = reader.read("webserver/conf/server.xml");
            String xPath = "/server/service/connector";
            Element connectorElt = (Element)document.selectSingleNode(xPath);
            Attribute portAttr = connectorElt.attribute("port");
            port =Integer.parseInt(portAttr.getValue());
            Logger.log("port "+port);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }
}
