/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cave.xsdcleaner.main;

import com.cave.xsdcleaner.util.XsdHelper;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 *
 * @author José Rodrigues
 */
public class XsdCleaner {

    // credit where credit is due
    //http://www.chipkillmar.net/2009/03/25/pretty-print-xml-from-a-dom/
    public static String prettyPrintWithDOM3LS(Document document) {
        // Pretty-prints a DOM document to XML using DOM Load and Save's LSSerializer.
        // Note that the "format-pretty-print" DOM configuration parameter can only be set in JDK 1.6+.
        DOMImplementation domImplementation = document.getImplementation();
        if (domImplementation.hasFeature("LS", "3.0") && domImplementation.hasFeature("Core", "2.0")) {
            DOMImplementationLS domImplementationLS = (DOMImplementationLS) domImplementation.getFeature("LS", "3.0");
            LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
            DOMConfiguration domConfiguration = lsSerializer.getDomConfig();
            if (domConfiguration.canSetParameter("format-pretty-print", Boolean.TRUE)) {
                lsSerializer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
                LSOutput lsOutput = domImplementationLS.createLSOutput();
                lsOutput.setEncoding("UTF-8");
                StringWriter stringWriter = new StringWriter();
                lsOutput.setCharacterStream(stringWriter);
                lsSerializer.write(document, lsOutput);
                return stringWriter.toString();
            } else {
                throw new RuntimeException("DOMConfiguration 'format-pretty-print' parameter isn't settable.");
            }
        } else {
            throw new RuntimeException("DOM 3.0 LS and/or DOM 2.0 Core not supported.");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.err.println("XSD Filename argument Missing.");
            System.err.println("Use with : <xsdFile.xsd>");
            System.exit(1);
        }

        String filePath = args[0];

        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("Non Existing file : " + filePath);
            System.exit(1);
        } else if (!file.canRead()) {
            System.err.println("Unable to read file : " + filePath);
            System.err.println("Please check if you have read privileges.");
            System.exit(1);
        } else if (!file.isFile()) {
            System.err.println("Invalid Filename.");
            System.exit(1);
        }

        // ----------------

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XsdCleaner.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (builder == null) {
            System.err.println("\nBuilder is null.");
            System.exit(1);
        }

        Document document = null;

        try {
            document = builder.parse(filePath);
        } catch (SAXException ex) {
            Logger.getLogger(XsdCleaner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XsdCleaner.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (document == null) {
            System.err.println("\nError processing document.");
            System.exit(1);
        }

        // FUN STARTS NOW!!!

        /**
         * Demo das restantes funcionalidades
         *
       
         List<String> unusedTypes = XsdHelper.getUnusedTypes(document);

        if (unusedTypes.size() > 0) {
            System.out.println("Unused types:\n");

            for (String tn : unusedTypes) {
                System.out.println(tn);
            }
            System.out.println();
        }

        System.out.println("Found " + unusedTypes.size() + " unused types (" + XsdHelper.getTypes(document).size() + " total).\n\n");
          */
        
        
        XsdHelper.removeUnusedTypes(document);

       System.out.println(prettyPrintWithDOM3LS(document));                
    }
}
