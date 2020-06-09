package com.autumn.pay.channel.weixin.util;

import com.autumn.pay.channel.AutumnPayException;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信Xml帮助
 *
 * @author ycg
 */
public final class WeiXinXmlUtils {

    /**
     * 创建文档生成器
     *
     * @return
     */
    public static DocumentBuilder newDocumentBuilder() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
            return documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new AutumnPayException(e.getMessage(), e);
        }
    }

    /**
     * 创建新的文档
     *
     * @return
     */
    public static Document newDocument() {
        return newDocumentBuilder().newDocument();
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     */
    public static Map<String, String> xmlToMap(String strXML) {
        InputStream stream = null;
        try {
            Map<String, String> data = new HashMap<>(16);
            DocumentBuilder documentBuilder = WeiXinXmlUtils.newDocumentBuilder();
            stream = new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8));
            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            return data;
        } catch (Exception e) {
            throw new AutumnPayException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) {
        StringWriter writer = null;
        try {
            Document document = WeiXinXmlUtils.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                } else {
                    value = value.trim();
                }
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            //是否  .replaceAll("\n|\r", "");
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new AutumnPayException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
