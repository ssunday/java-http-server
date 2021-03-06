package Cobspec.Deliverer;

import Cobspec.HTML.FormDisplayTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class FormDelivererTest {

    private File file;
    private FormDisplayTemplate htmlFormDisplay;

    @Before
    public void setUp(){
        file = new File("form.txt");
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWhenGet(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "GET");
        htmlFormDisplay = new FormDisplayTemplate(null);
        String html = htmlFormDisplay.renderPage();
        byte[] htmlBytes = html.getBytes();
        assertArrayEquals("Returns byte of html blank form page when get", htmlBytes, formDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsByteArrayWithParamsWhenPost() {
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        htmlFormDisplay = new FormDisplayTemplate("foo");
        String html = htmlFormDisplay.renderPage();
        byte[] htmlBytes = html.getBytes();
        assertArrayEquals("Returns byte array including post params when post", htmlBytes, formDeliverer.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWithParamsAfterPostWhenGet(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        formDeliverer.getBytes();
        FormDeliverer formDeliverer2 = new FormDeliverer(null, "GET");
        htmlFormDisplay = new FormDisplayTemplate("foo");
        String html = htmlFormDisplay.renderPage();
        byte[] htmlBytes = html.getBytes();
        assertArrayEquals("Returns bytes of html page with params when get after post", htmlBytes, formDeliverer2.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLPageWithParamsWhenPut(){
        FormDeliverer formDeliverer = new FormDeliverer("bar", "PUT");
        htmlFormDisplay = new FormDisplayTemplate("bar");
        String html = htmlFormDisplay.renderPage();
        byte[] htmlBytes = html.getBytes();
        assertArrayEquals("Returns byte array including post params when put", htmlBytes, formDeliverer.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedIn(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "DELETE");
        htmlFormDisplay = new FormDisplayTemplate(null);
        String html = htmlFormDisplay.renderPage();
        byte[] htmlBytes = html.getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedInAfterPost(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        formDeliverer.getBytes();
        FormDeliverer formDelivererDelete = new FormDeliverer(null, "DELETE");
        htmlFormDisplay = new FormDisplayTemplate(null);
        String html = htmlFormDisplay.renderPage();
        byte[] htmlBytes = html.getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formDelivererDelete.getBytes());
    }

    @Test
    public void testGetResponseIncludes200CodeForGet(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "GET");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void tesGetResponseIncludes200CodeForPOST(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "POST");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseIncludes200CodeForPUT(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "PUT");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseIncludes200CodeForDelete(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "DELETE");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseIncludes405CodeForPATCH(){
        FormDeliverer formDeliverer = new FormDeliverer("foo", "PATCH");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesTextHtml(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "GET");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("text/html"));
    }

    @Test
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        FormDeliverer formDeliverer = new FormDeliverer(null, "OPTIONS");
        String response = formDeliverer.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @After
    public void tearDown(){
        file.delete();
    }


}