package Cobspec.Deliverer;

import Cobspec.HTML.ParameterDisplayTemplate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class ParameterDelivererTest {

    private ParameterDisplayTemplate parameterDisplayTemplate;

    @Test
    public void testGetBytesReturnsBytesOfSimpleParameters() throws Exception {
        String[] param = new String[]{"variable_2=stuff"};
        String[] parsedParam = new String[]{"variable_2 = stuff"};
        parameterDisplayTemplate = new ParameterDisplayTemplate(parsedParam);
        String wrappedParam = parameterDisplayTemplate.renderPage();
        byte[] bytes = wrappedParam.getBytes();
        String path = "/somePath?" + param[0];
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(path, "GET");
        assertArrayEquals("Byte arrays of simple string with padding equal each other", bytes, parametersDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsByteOfComplexEncodedParameters() throws Exception {
        String[] param = new String[]{"variable_1=Operators <%2C"};
        String[] parsedParam = new String[]{"variable_1 = Operators <,"};
        parameterDisplayTemplate = new ParameterDisplayTemplate(parsedParam);
        String wrappedParam = parameterDisplayTemplate.renderPage();
        byte[] bytes = wrappedParam.getBytes();
        String path = "/somePath?" + param[0];
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(path, "GET");
        assertArrayEquals("Byte arrays of single complex decoded string equals padded decoded version", bytes, parametersDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsBytesOfMultipleParametersWrappedInHTML() throws Exception {
        String[] params = new String[]{"variable_1=Operators <%2C", "variable_2=stuff"};
        String[] parsedParam = new String[]{"variable_1 = Operators <,", "variable_2 = stuff"};
        parameterDisplayTemplate = new ParameterDisplayTemplate(parsedParam);
        String wrappedParams = parameterDisplayTemplate.renderPage();
        byte[] bytes = wrappedParams.getBytes();
        String path = "/somePath?" + params[0] + "&" + params[1];
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer(path, "GET");
        assertArrayEquals("Returns HTML wrapped and padded two decoded parameters", bytes, parametersDeliverer.getBytes());
    }

    @Test
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "OPTIONS");
        String response = parametersDeliverer.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @Test
    public void testGetResponseHeaderIncludes200(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "GET");
        String response = parametersDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testResponseHeaderIncludes405WhenPost(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "POST");
        String response = parametersDeliverer.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

    @Test
    public void testResponseHeaderIncludesTextHTML(){
        ParameterDeliverer parametersDeliverer = new ParameterDeliverer("/parameters?stuff=1", "GET");
        String response = parametersDeliverer.getResponseHeader();
        assertThat(response, containsString("text/html"));
    }

}