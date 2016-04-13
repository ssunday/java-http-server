
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTMLParameterDisplayTest {

    @Test
    public void testhtmlWrapContainsParams(){
        String[] params = new String[]{"var1=stuff"};
        String wrappedParams = HTMLParameterDisplay.htmlWrap(params);
        assertTrue("HTML Wrapped parameters contains single parameter", wrappedParams.contains(params[0]));
    }

    @Test
    public void testhtmlWrapContainsBodyTag(){
        String[] params = new String[]{"var1=stuff"};
        String wrappedParams = HTMLParameterDisplay.htmlWrap(params);
        assertTrue("HTML Wrapped parameters contains body tag", wrappedParams.contains("<body>"));
    }

}