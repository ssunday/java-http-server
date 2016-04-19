import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParameterDelivererTest {

    private HTMLParameterDisplay htmlParameterDisplay;

    @Before
    public void setUp(){
        htmlParameterDisplay = new HTMLParameterDisplay();
    }

    @Test
    public void testGetBytesReturnsBytesOfSimpleParameters() throws Exception {
        String[] param = new String[]{"variable_2=stuff"};
        String[] parsedParam = new String[]{"variable_2 = stuff"};
        String wrappedParam = htmlParameterDisplay.htmlWrap(parsedParam);
        byte[] bytes = wrappedParam.getBytes();
        String path = "/somePath?" + param[0];
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(path, "GET");
        assertArrayEquals("Byte arrays of simple string with padding equal each other", bytes, parametersDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsByteOfComplexEncodedParameters() throws Exception {
        String[] param = new String[]{"variable_1=Operators <%2C"};
        String[] parsedParam = new String[]{"variable_1 = Operators <,"};
        String wrappedParam = htmlParameterDisplay.htmlWrap(parsedParam);
        byte[] bytes = wrappedParam.getBytes();
        String path = "/somePath?" + param[0];
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(path, "GET");
        assertArrayEquals("Byte arrays of single complex decoded string equals padded decoded version", bytes, parametersDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsBytesOfMultipleParametersWrappedInHTML() throws Exception {
        String[] params = new String[]{"variable_1=Operators <%2C", "variable_2=stuff"};
        String[] parsedParam = new String[]{"variable_1 = Operators <,", "variable_2 = stuff"};
        String wrappedParams = htmlParameterDisplay.htmlWrap(parsedParam);
        byte[] bytes = wrappedParams.getBytes();
        String path = "/somePath?" + params[0] + "&" + params[1];
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(path, "GET");
        assertArrayEquals("Returns HTML wrapped and padded two decoded parameters", bytes, parametersDeliverer.getBytes());
    }

    @Test
    public void testGetMethodOptionsReturnsGet(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "GET");
        assertArrayEquals("Method options returns array with only get", new String[]{"GET"}, parametersDeliverer.getMethodOptions());
    }

    @Test
    public void testGetHTTPCodeReturns200(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "GET");
        assertEquals("HTTP Code is 200 with get", 200, parametersDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "POST");
        assertEquals("HTTP Code is 405 with POST", 405, parametersDeliverer.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextHTML() throws Exception {
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(null, "GET");
        assertEquals("Content type is text/html", "text/html", parametersDeliverer.getContentType());
    }

}