package Routes.Cobspec.HTML;

import Routes.Cobspec.HTML.HTMLParameterDisplay;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTMLParameterDisplayTest {

    private HTMLParameterDisplay htmlParameterDisplay;

    @Before
    public void setUp(){
        htmlParameterDisplay = new HTMLParameterDisplay();
    }

    @Test
    public void testhtmlWrapContainsParams(){
        String[] params = new String[]{"var1=stuff"};
        String wrappedParams = htmlParameterDisplay.htmlWrap(params);
        assertTrue("HTML Wrapped parameters contains single parameter", wrappedParams.contains(params[0]));
    }

    @Test
    public void testhtmlWrapContainsBodyTag(){
        String[] params = new String[]{"var1=stuff"};
        String wrappedParams = htmlParameterDisplay.htmlWrap(params);
        assertTrue("HTML Wrapped parameters contains body tag", wrappedParams.contains("<body>"));
    }

}