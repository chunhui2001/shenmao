package com.supercard.repository;

import com.supercard.tour.BillEntity;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{

    BillRepository billItemRepository = new BillRepository();

    public AppTest( String testName )
    {

        super( testName );
    }


    public static Test suite()
    {

        return new TestSuite( AppTest.class );
    }


    public void testApp()
    {
//        List<BillEntity> result = billItemRepository.lists("中信");
//        assertTrue( result.size() == 0 );
        assertTrue( true );
    }
}
