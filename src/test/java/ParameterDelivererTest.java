import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

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
    public void testGetResponseHeaderIncludesGETAndOPTIONS(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "OPTIONS");
        String response = parametersDeliverer.getResponseHeader();
        assertTrue("Header includes options in allow line", response.contains("GET,OPTIONS"));
    }

    @Test
    public void testGetResponseHeaderIncludes200(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "GET");
        String response = parametersDeliverer.getResponseHeader();
        assertTrue("HTTP Code is 200 with get", response.contains("200 OK"));
    }

    @Test
    public void testResponseHeaderIncludes405WhenPost(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "POST");
        String response = parametersDeliverer.getResponseHeader();
        assertTrue("HTTP Code is 405 with POST", response.contains("405 Method Not Allowed"));
    }

    @Test
    public void testResponseHeaderIncludesTextHTML(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "GET");
        String response = parametersDeliverer.getResponseHeader();
        assertTrue("Header includes text/html", response.contains("text/html"));
    }

}