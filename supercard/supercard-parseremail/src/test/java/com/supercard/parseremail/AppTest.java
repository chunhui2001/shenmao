package com.supercard.parseremail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercard.parseremail.cardparse.*;
import com.supercard.tour.BillEntity;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

    public static String readFile(String filePath)  {

        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            return IOUtils.toString(inputStream, "utf-8");
        } catch (IOException e) {
            return null;
        }

    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {

        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {

        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception
    {

        String email = "76920104@qq.com";

//        ParsePCCCEmail parsePCCCEmail = new ParsePCCCEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//        Collection<BillEntity> billEntityList = parsePCCCEmail.parse();
//        System.out.println(new ObjectMapper().writeValueAsString(billEntityList));
//
//
//        ParseCmbEmail parsePmbEmail = new ParseCmbEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//        Collection<BillEntity> billEntityList = parsePmbEmail.parse();
//        System.out.println(new ObjectMapper().writeValueAsString(billEntityList));
//
//        ParseCiticEmail parseCiticEmail = new ParseCiticEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//        Collection<BillEntity> billEntityList = parseCiticEmail.parse();
//        System.out.println(new ObjectMapper().writeValueAsString(billEntityList));
//
//
//        ParseSpdbEmail parseSpdbEmail = new ParseSpdbEmail(email, readFile("/Users/keesh/Desktop/demo.html"));
//        Collection<BillEntity> billEntityList = parseSpdbEmail.parse();
//        System.out.println(new ObjectMapper().writeValueAsString(billEntityList));
//
//        new ParseCitiBankEmail(email, null).parse();

//        new ParseCgbEmail(email, null).parse();

        assertTrue( true );
    }
}
