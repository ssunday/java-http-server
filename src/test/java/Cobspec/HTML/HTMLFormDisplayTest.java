package Cobspec.HTML;

import Cobspec.HTML.HTMLFormDisplay;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTMLFormDisplayTest {

    private HTMLFormDisplay htmlFormDisplay;

    @Before
    public void setUp(){
        htmlFormDisplay = new HTMLFormDisplay();
    }

    @Test
    public void testDisplayFormPageHasInputBox(){
        String html = htmlFormDisplay.displayFormPage(null);
        assertTrue("Form page has input text box", html.contains("input type='text'"));
    }

    @Test
    public void testDisplayFormPageHasPostAsMethod(){
        String html = htmlFormDisplay.displayFormPage(null);
        assertTrue("Form page has post as method", html.contains("method='post'"));
    }

    @Test
    public void testDisplayFormPageDisplaysParamIfPassedIn(){
        String param = "var1=foo";
        String html = htmlFormDisplay.displayFormPage(param);
        assertTrue("Form page has param data when passed in", html.contains(param));
    }
}