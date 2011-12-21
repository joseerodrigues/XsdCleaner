package com.cave.xsdcleaner.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author José Rodrigues
 */
public class XsdHelper {

    private static String getAttributeValue(String attrName, Node node) {
        String ret = null;

        if (node != null && node.hasAttributes() && node.getAttributes().getNamedItem(attrName) != null) {
            ret = node.getAttributes().getNamedItem(attrName).getNodeValue();
        }

        return ret;
    }

    private static boolean isType(Node node) {

        String nodeName = node.getNodeName();

        // indexOf em vez de equals por causa das possiveis diferenças de namespace.        
        // ex : xs:complexType

        boolean ret = nodeName.indexOf("complexType") != -1 || nodeName.indexOf("simpleType") != -1;
                ret = ret || nodeName.indexOf("attributeGroup") != -1 ;
                
        return ret;
    }

    private static HashMap<String, Node> _getTypes(Node rootNode) {

        HashMap<String, Node> types = new HashMap<String, Node>();

        String nameValue = getAttributeValue("name", rootNode);

        if (isType(rootNode)) {
            if (nameValue != null && !types.containsKey(nameValue)) {
                types.put(nameValue, rootNode);
            }
        }

        if (rootNode.hasChildNodes()) {
            NodeList childNodes = rootNode.getChildNodes();

            int len = childNodes.getLength();

            for (int i = 0; i < len; i++) {
                Node node = childNodes.item(i);

                HashMap<String, Node> childTypes = _getTypes(node);

                for (Entry<String, Node> entry : childTypes.entrySet()) {
                    if (!types.containsKey(entry.getKey())) {
                        types.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        return types;
    }

    public static List<String> getTypes(Document document) {
        
        if (document == null){
            throw new NullPointerException("Document is null");
        }
        
        return new ArrayList(_getTypes(document).keySet());
    }

    private static boolean isUsed(String typeName, Node rootNode) {

        boolean hasTypeName = typeName.equals(getAttributeValue("type", rootNode));
        hasTypeName = hasTypeName || typeName.equals(getAttributeValue("ref", rootNode));
        hasTypeName = hasTypeName || typeName.equals(getAttributeValue("base", rootNode));

        if (hasTypeName) {
            return true;
        }

        if (rootNode.hasChildNodes()) {
            NodeList childNodes = rootNode.getChildNodes();

            int len = childNodes.getLength();

            for (int i = 0; i < len; i++) {
                Node node = childNodes.item(i);

                if (isUsed(typeName, node)){
                    return true;
                }
            }
        }
        return false;
    }
    
    private static HashMap<String, Node> _getUnusedTypes(Document document) {
        HashMap<String, Node> foundTypes = _getTypes(document);
        HashMap<String, Node> notUsedTypes = new HashMap<String, Node>();

        for (Entry<String, Node> entry : foundTypes.entrySet()) {
            if (!isUsed(entry.getKey(), document)) {
                notUsedTypes.put(entry.getKey(), entry.getValue());
            }
        }

        return notUsedTypes;
    }

    public static List<String> getUnusedTypes(Document document) {
        
        if (document == null){
            throw new NullPointerException("Document is null");
        }
        
        return new ArrayList(_getUnusedTypes(document).keySet());        
    }

    public static void removeUnusedTypes(Document document) {
        
        if (document == null){
            throw new NullPointerException("Document is null");
        }
        
        HashMap<String, Node> unusedTypes = null;
        do {
            unusedTypes = _getUnusedTypes(document);

            for (Entry<String, Node> entry: unusedTypes.entrySet()) {
                
                Node node = entry.getValue();
                
                node.getParentNode().removeChild(node);
                
               // System.out.println("removed : " + entry.getKey());
            }
        } while (unusedTypes.size() > 0);
    }
}
