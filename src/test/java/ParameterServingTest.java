

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParameterServingTest {

    private ParameterServing parametersServing;

    @Before
    public void initialize(){
        parametersServing = new ParameterServing();
    }

    @Test
    public void testGetBytesReturnsBytesOfSimpleParameters() throws Exception {
        String[] param = new String[]{"variable_2=stuff"};
        String[] parsedParam = new String[]{"variable_2 = stuff"};
        String wrappedParam = HTMLParameterDisplay.htmlWrap(parsedParam);
        byte[] bytes = wrappedParam.getBytes();
        String path = "/somePath?" + param[0];
        assertArrayEquals("Byte arrays of simple string with padding equal each other", bytes, parametersServing.getBytes(path));
    }

    @Test
    public void testGetBytesReturnsByteOfComplexEncodedParameters() throws Exception {
        String[] param = new String[]{"variable_1=Operators <%2C"};
        String[] parsedParam = new String[]{"variable_1 = Operators <,"};
        String wrappedParam = HTMLParameterDisplay.htmlWrap(parsedParam);
        byte[] bytes = wrappedParam.getBytes();
        String path = "/somePath?" + param[0];
        assertArrayEquals("Byte arrays of single complex decoded string equals padded decoded version", bytes, parametersServing.getBytes(path));
    }

    @Test
    public void testGetBytesReturnsBytesOfMultipleParametersWrappedInHTML() throws Exception {
        String[] params = new String[]{"variable_1=Operators <%2C", "variable_2=stuff"};
        String[] parsedParam = new String[]{"variable_1 = Operators <,", "variable_2 = stuff"};
        String wrappedParams = HTMLParameterDisplay.htmlWrap(parsedParam);
        byte[] bytes = wrappedParams.getBytes();
        String path = "/somePath?" + params[0] + "&" + params[1];
        assertArrayEquals("Returns HTML wrapped and padded two decoded parameters", bytes, parametersServing.getBytes(path));
    }


    @Test
    public void testGetContentTypeReturnsTextHTML() throws Exception {
        assertEquals("Content type is text/html", "text/html", parametersServing.getContentType(null));
    }


}