package Cobspec.HTML;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ParameterDisplayTemplateTest {

    private ParameterDisplayTemplate parameterDisplayTemplate;

    @Test
    public void testRenderPageContainsParams(){
        String[] params = new String[]{"var1=stuff"};
        parameterDisplayTemplate = new ParameterDisplayTemplate(params);
        String wrappedParams = parameterDisplayTemplate.renderPage();
        assertTrue("HTML Wrapped parameters contains single parameter", wrappedParams.contains(params[0]));
    }

    @Test
    public void testRenderPageContainsBodyTag(){
        String[] params = new String[]{"var1=stuff"};
        parameterDisplayTemplate = new ParameterDisplayTemplate(params);
        String wrappedParams = parameterDisplayTemplate.renderPage();
        assertTrue("HTML Wrapped parameters contains body tag", wrappedParams.contains("<body>"));
    }

}