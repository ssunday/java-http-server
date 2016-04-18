import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FormServingTest {

    private File file;
    private HTMLFormDisplay htmlFormDisplay;

    @Before
    public void setUp(){
        file = new File("form.txt");
        htmlFormDisplay = new HTMLFormDisplay();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWhenGet(){
        FormServing formServing = new FormServing(null, "GET");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when get", htmlBytes, formServing.getBytes());
    }

    @Test
    public void testGetBytesReturnsByteArrayWithParamsWhenPost() {
        FormServing formServing = new FormServing("foo", "POST");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("foo").getBytes();
        assertArrayEquals("Returns byte array including post params when post", htmlBytes, formServing.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWithParamsAfterPostWhenGet(){
        FormServing formServing = new FormServing("foo", "POST");
        formServing.getBytes();
        FormServing formServing2 = new FormServing(null, "GET");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("foo").getBytes();
        assertArrayEquals("Returns bytes of html page with params when get after post", htmlBytes, formServing2.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLPageWithParamsWhenPut(){
        FormServing formServing = new FormServing("bar", "PUT");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("bar").getBytes();
        assertArrayEquals("Returns byte array including post params when put", htmlBytes, formServing.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedIn(){
        FormServing formServing = new FormServing(null, "DELETE");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formServing.getBytes());
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedInAfterPost(){
        FormServing formServing = new FormServing("foo", "POST");
        formServing.getBytes();
        FormServing formServingDelete = new FormServing(null, "DELETE");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formServingDelete.getBytes());
    }

    @Test
    public void testGetHTTPCodeReturns200ForGet(){
        FormServing formServing = new FormServing("foo", "GET");
        assertEquals("Returns 200 for GET", 200, formServing.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns200ForPOST(){
        FormServing formServing = new FormServing("foo", "POST");
        assertEquals("Returns 200 for POST", 200, formServing.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns200ForPUT(){
        FormServing formServing = new FormServing("foo", "PUT");
        assertEquals("Returns 200 for PUT", 200, formServing.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns200ForDelete(){
        FormServing formServing = new FormServing("foo", "DELETE");
        assertEquals("Returns 200 for DELETE", 200, formServing.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextHTML(){
        FormServing formServing = new FormServing(null, "GET");
        assertEquals("Content type is text/html", "text/html", formServing.getContentType());
    }


    @Test
    public void testGetMethodOptionsReturnsGET(){
        FormServing formServing = new FormServing(null, "GET");
        String[] options = new String[]{"GET", "POST", "PUT", "DELETE"};
        assertArrayEquals("Method options returns array with get, post, put, delete", options, formServing.getMethodOptions());
    }

    @After
    public void tearDown(){
        file.delete();
    }


}