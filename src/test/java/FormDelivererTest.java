import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FormDelivererTest {

    private File file;
    private HTMLFormDisplay htmlFormDisplay;

    @Before
    public void setUp(){
        file = new File("form.txt");
        htmlFormDisplay = new HTMLFormDisplay();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWhenGet(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "GET");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when get", htmlBytes, formDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsByteArrayWithParamsWhenPost() {
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("foo").getBytes();
        assertArrayEquals("Returns byte array including post params when post", htmlBytes, formDeliverer.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWithParamsAfterPostWhenGet(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        formDeliverer.getBytes();
        FormDeliverer formDeliverer2 = new FormDeliverer(null, "GET");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("foo").getBytes();
        assertArrayEquals("Returns bytes of html page with params when get after post", htmlBytes, formDeliverer2.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLPageWithParamsWhenPut(){
        FormDeliverer formDeliverer = new FormDeliverer("bar", "PUT");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("bar").getBytes();
        assertArrayEquals("Returns byte array including post params when put", htmlBytes, formDeliverer.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedIn(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "DELETE");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedInAfterPost(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        formDeliverer.getBytes();
        FormDeliverer formDelivererDelete = new FormDeliverer(null, "DELETE");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formDelivererDelete.getBytes());
    }

    @Test
    public void testGetHTTPCodeReturns200ForGet(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "GET");
        assertEquals("Returns 200 for GET", 200, formDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns200ForPOST(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        assertEquals("Returns 200 for POST", 200, formDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns200ForPUT(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "PUT");
        assertEquals("Returns 200 for PUT", 200, formDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns200ForDelete(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "DELETE");
        assertEquals("Returns 200 for DELETE", 200, formDeliverer.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextHTML(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "GET");
        assertEquals("Content type is text/html", "text/html", formDeliverer.getContentType());
    }


    @Test
    public void testGetMethodOptionsReturnsGET(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "GET");
        String[] options = new String[]{"GET", "POST", "PUT", "DELETE"};
        assertArrayEquals("Method options returns array with get, post, put, delete", options, formDeliverer.getMethodOptions());
    }

    @After
    public void tearDown(){
        file.delete();
    }


}