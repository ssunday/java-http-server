package Cobspec.HTML;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FormDisplayTemplateTest {

    private FormDisplayTemplate htmlFormDisplay;

    @Test
    public void testDisplayFormPageHasInputBox(){
        htmlFormDisplay = new FormDisplayTemplate(null);
        String html = htmlFormDisplay.renderPage();
        assertTrue("Form page has input text box", html.contains("input type='text'"));
    }

    @Test
    public void testDisplayFormPageHasPostAsMethod(){
        htmlFormDisplay = new FormDisplayTemplate(null);
        String html = htmlFormDisplay.renderPage();
        assertTrue("Form page has post as method", html.contains("method='post'"));
    }

    @Test
    public void testDisplayFormPageDisplaysParamIfPassedIn(){
        String param = "var1=foo";
        htmlFormDisplay = new FormDisplayTemplate(param);
        String html = htmlFormDisplay.renderPage();
        assertTrue("Form page has param data when passed in", html.contains(param));
    }
}