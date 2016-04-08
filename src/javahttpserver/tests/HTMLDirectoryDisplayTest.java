package javahttpserver.tests;

import javahttpserver.main.HTMLDirectoryDisplay;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTMLDirectoryDisplayTest {

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

    @Test
    public void testDisplayListingIncludesFileNameWhenGivenASingleFile(){
        String[] contents = {"text.txt"};
        String file ="text.txt";
        assertTrue("Contains file name", display.displayListing(contents).contains(file));
    }

    @Test
    public void testDisplayListingIncludesDirectoryNameWhenGivenADirectory(){
        String[] contents = {"something"};
        String directory ="something";
        assertTrue("Contains directory", display.displayListing(contents).contains(directory));
    }

    @Test
    public void testDisplayListingIncludesBothDirectoryAndFile(){
        String[] contents = {"something", "text.txt"};
        String directory ="something";
        String file ="text.txt";
        assertTrue("Contains directory", display.displayListing(contents).contains(directory));
        assertTrue("Contains file", display.displayListing(contents).contains(file));
    }

}