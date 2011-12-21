/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cave.xsdcleaner.util;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author hbaptista
 */
public class XsdHelperTest {
    
    public XsdHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTypes method, of class XsdHelper.
     */
    @Test
    public void testGetTypes() {
        System.out.println("getTypes");
        Document document = null;
        List expResult = null;
        List result = XsdHelper.getTypes(document);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUnusedTypes method, of class XsdHelper.
     */
    @Test
    public void testGetUnusedTypes() {
        System.out.println("getUnusedTypes");
        Document document = null;
        List expResult = null;
        List result = XsdHelper.getUnusedTypes(document);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeUnusedTypes method, of class XsdHelper.
     */
    @Test
    public void testRemoveUnusedTypes() {
        System.out.println("removeUnusedTypes");
        Document document = null;
        XsdHelper.removeUnusedTypes(document);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
