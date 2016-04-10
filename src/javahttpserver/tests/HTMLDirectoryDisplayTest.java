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
        String directoryListing = display.displayListing(contents);
        assertTrue("Contains empty body", directoryListing.contains(html));
    }

    @Test
    public void testDisplayListingIncludesFileNameWhenGivenASingleFile(){
        String[] contents = {"text.txt"};
        String file ="text.txt";
        String directoryListing = display.displayListing(contents);
        assertTrue("Contains file name", directoryListing.contains(file));
    }

    @Test
    public void testDisplayListingIncludesDirectoryNameWhenGivenADirectory(){
        String[] contents = {"something"};
        String directory ="something";
        String directoryListing = display.displayListing(contents);
        assertTrue("Contains directory", directoryListing.contains(directory));
    }

    @Test
    public void testDisplayListingIncludesBothDirectoryAndFile(){
        String[] contents = {"something", "text.txt"};
        String directory ="something";
        String file ="text.txt";
        String directoryListing = display.displayListing(contents);
        assertTrue("Contains directory", directoryListing.contains(directory));
        assertTrue("Contains file", directoryListing.contains(file));
    }

    @Test
    public void testDisplayListingLinksDirectories(){
        String[] contents = {"something"};
        String directoryLink ="href='/something'";
        String directoryListing = display.displayListing(contents);
        assertTrue("Contains link to directory", directoryListing.contains(directoryLink));
    }

    @Test
    public void testDisplayListingLinksFiles(){
        String[] contents = {"text.txt"};
        String fileLink ="href='/text.txt'";
        String directoryListing = display.displayListing(contents);
        assertTrue("Links to file", directoryListing.contains(fileLink));
    }

    @Test
    public void testDisplayDirectoryBackNavigationShowsLinkToPreviousFolder(){
        String previousDirectory = "/folder";
        String backNavigation = display.displayDirectoryBackNavigation(previousDirectory);
        String folderLink = "href='" + previousDirectory + "'";
        assertTrue("Links to previous navigation", backNavigation.contains(folderLink));
    }


}