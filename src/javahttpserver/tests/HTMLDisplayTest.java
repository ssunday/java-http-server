package javahttpserver.tests;

import javahttpserver.main.HTMLDirectoryDisplay;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by sarahsunday on 4/7/16.
 */
public class HTMLDisplayTest {

    private HTMLDirectoryDisplay display;

    @Before

    public void initialize() {
        display = new HTMLDirectoryDisplay();
    }

    @Test
    public void testDisplayListingReturnsEmptyBodyWhenGivenNothing(){
        String[] contents = new String[0];
        String html ="<body>" + "</body>";
        assertTrue("Contains empty body", display.displayListing(contents).contains(html));
    }

}