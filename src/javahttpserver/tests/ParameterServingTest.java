package javahttpserver.tests;

import javahttpserver.main.ParameterServing;
import org.junit.Before;
import org.junit.Test;

import java.net.URLEncoder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParameterServingTest {

    private ParameterServing parametersServing;

    @Before
    public void initialize(){
        parametersServing = new ParameterServing();
    }

    @Test
    public void testGetBytesReturnsByteOfMultipleParameters() throws Exception {
        String params1 = "variable_1=Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String params2 = "variable_2=stuff";
        String encoded1  = URLEncoder.encode(params1, "UTF-8");
        String encoded2  = URLEncoder.encode(params2, "UTF-8");
        String completeParams = params1 + "\n" + params2;
        byte[] bytes = completeParams.getBytes();
        String path = "/somePath?" + encoded1 + "&" + encoded2;
        assertArrayEquals("Byte arrays of two decoded variables equal each other", bytes, parametersServing.getBytes(path));
    }

    @Test
    public void testGetBytesReturnsByteOfComplexEncodedParameters() throws Exception {
        String params = "variable_1=Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String encoded  = URLEncoder.encode(params, "UTF-8");
        byte[] bytes = params.getBytes();
        String path = "/somePath?" + encoded;
        assertArrayEquals("Byte arrays of single complex decoded string equals each other", bytes, parametersServing.getBytes(path));
    }

    @Test
    public void testGetBytesReturnsBytesOfSimpleParameters() throws Exception {
        String params = "variable_2=stuff";
        String encoded  = URLEncoder.encode(params, "UTF-8");
        byte[] bytes = params.getBytes();
        String path = "/somePath?" + encoded;
        assertArrayEquals("Byte arrays of simple string equal each other", bytes, parametersServing.getBytes(path));
    }

    @Test
    public void testGetContentTypeReturnsTextPlain() throws Exception {
        assertEquals("Content type is text/plain", "text/plain", parametersServing.getContentType(null));
    }


}