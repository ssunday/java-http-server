package Cobspec.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DirectoryDisplayTemplateTest
{
    private DirectoryDisplayTemplate display;

    @Test
    public void testRenderPageIncludesFileNameWhenGivenASingleFile(){
        String[] contents = {"/text.txt"};
        String file ="/text.txt";
        display = new DirectoryDisplayTemplate(contents, "/", "/");
        assertThat(display.renderPage(), containsString(file));
    }

    @Test
    public void testRenderPageIncludesDirectoryNameWhenGivenADirectory(){
        String[] contents = {"something/"};
        String directory ="/something/";
        display = new DirectoryDisplayTemplate(contents, "/", "/");
        assertThat(display.renderPage(), containsString(directory));
    }

    @Test
    public void testDisplayListingLinksDirectories(){
        String[] contents = {"something/"};
        String directoryLink ="href='/something/'";
        display = new DirectoryDisplayTemplate(contents, "/", "/");
        assertThat(display.renderPage(), containsString(directoryLink));
    }

    @Test
    public void testRenderPageLinksNestedDirectories(){
        String[] contents = {"/something/"};
        String directoryLink ="href='/folder/something/'";
        display = new DirectoryDisplayTemplate(contents, "/folder", "/");
        assertThat(display.renderPage(), containsString(directoryLink));
    }

    @Test
    public void testRenderPageLinksFile(){
        String[] contents = {"text.txt"};
        String fileLink ="href='/text.txt'";
        display = new DirectoryDisplayTemplate(contents, "/", "/");
        assertThat(display.renderPage(), containsString(fileLink));
    }

    @Test
    public void testRenderPageLinksFileInFolder(){
        String[] contents = {"/text.txt"};
        String fileLink ="href='/folder/text.txt'";
        display = new DirectoryDisplayTemplate(contents, "/folder", "/");
        assertThat(display.renderPage(), containsString(fileLink));
    }

    @Test
    public void testRenderPageIncludesBothDirectoryAndFile(){
        String[] contents = {"something/", "text.txt"};
        String directory ="something";
        String file ="text.txt";
        display = new DirectoryDisplayTemplate(contents, "/", "/");
        assertThat(display.renderPage(), containsString(directory));
        assertThat(display.renderPage(), containsString(file));
    }


    @Test
    public void testRenderPageBackNavigationShowsLinkToPreviousFolder(){
        String previousDirectory = "folder";
        String folderLink = "href='" + previousDirectory + "'";
        display = new DirectoryDisplayTemplate(new String[0], "/", previousDirectory);
        assertThat(display.renderPage(), containsString(folderLink));
    }

}