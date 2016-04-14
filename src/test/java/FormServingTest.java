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
        FormServing formServing = new FormServing("GET", null);
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when get", htmlBytes, formServing.getBytes());
    }

    @Test
    public void testGetBytesReturnsByteArrayWithParamsWhenPost() {
        FormServing formServing = new FormServing("POST", "foo");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("foo").getBytes();
        assertArrayEquals("Returns byte array including post params when post", htmlBytes, formServing.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLFormPageWithParamsAfterPostWhenGet(){
        FormServing formServing = new FormServing("POST", "foo");
        formServing.getBytes();
        FormServing formServing2 = new FormServing("GET", null);
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("foo").getBytes();
        assertArrayEquals("Returns bytes of html page with params when get after post", htmlBytes, formServing2.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofHTMLPageWithParamsWhenPut(){
        FormServing formServing = new FormServing("PUT", "bar");
        byte[] htmlBytes = htmlFormDisplay.displayFormPage("bar").getBytes();
        assertArrayEquals("Returns byte array including post params when put", htmlBytes, formServing.getBytes());
        file.delete();
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedIn(){
        FormServing formServing = new FormServing("DELETE", null);
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formServing.getBytes());
    }

    @Test
    public void testGetBytesReturnsBytesofGenericFormPageWhenDeleteIsPassedInAfterPost(){
        FormServing formServing = new FormServing("POST", "foo");
        formServing.getBytes();
        FormServing formServingDelete = new FormServing("DELETE", null);
        byte[] htmlBytes = htmlFormDisplay.displayFormPage(null).getBytes();
        assertArrayEquals("Returns byte of html blank form page when type is delete", htmlBytes, formServingDelete.getBytes());
    }

    @Test
    public void testGetContentTypeReturnsTextHTML(){
        FormServing formServing = new FormServing("GET", null);
        assertEquals("Content type is text/html", "text/html", formServing.getContentType());
    }

    @After
    public void tearDown(){
        file.delete();
    }


}