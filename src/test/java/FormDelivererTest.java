import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

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
    public void testGetResponseIncludes200CodeForGet(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "GET");
        assertTrue("Includes 200 for GET",formDeliverer.getResponseHeader().contains("200 OK"));
    }

    @Test
    public void tesGetResponseIncludes200CodeForPOST(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        assertTrue("Includes 200 for POST", formDeliverer.getResponseHeader().contains("200 OK"));
    }

    @Test
    public void testGetResponseIncludes200CodeForPUT(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "PUT");
        assertTrue("Includes 200 for PUT", formDeliverer.getResponseHeader().contains("200 OK"));
    }

    @Test
    public void testGetResponseIncludes200CodeForDelete(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "DELETE");
        assertTrue("Includes 200 for DELETE", formDeliverer.getResponseHeader().contains("200 OK"));
    }

    @Test
    public void testGetResponseIncludes405CodeForPATCH(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "PATCH");
        assertTrue("Includes 405 for PATCH", formDeliverer.getResponseHeader().contains("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesTextHtml(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "GET");
        assertTrue("Includes text/html", formDeliverer.getResponseHeader().contains("text/html"));
    }

    @Test
    public void testGetResponseHeaderIncludesOptionsWhenOPTIONS(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "OPTIONS");
        assertTrue("Response header includes all options when options request", formDeliverer.getResponseHeader().contains("Allow: GET,POST,PUT,DELETE,OPTIONS"));
    }

    @After
    public void tearDown(){
        file.delete();
    }


}