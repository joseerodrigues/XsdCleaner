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
import org.w3c.dom.Document;

/**
 *
 * @author José Rodrigues
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
    @Test(expected = NullPointerException.class)
    public void testGetTypes() {
        System.out.println("getTypes");

        XsdHelper.getTypes(null);
    }

    /**
     * Test of getUnusedTypes method, of class XsdHelper.
     */
    @Test(expected = NullPointerException.class)
    public void testGetUnusedTypes() {
        System.out.println("getUnusedTypes");

        XsdHelper.getUnusedTypes(null);
    }

    /**
     * Test of removeUnusedTypes method, of class XsdHelper.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveUnusedTypes() {
        System.out.println("removeUnusedTypes");

        XsdHelper.removeUnusedTypes(null);
    }
}
