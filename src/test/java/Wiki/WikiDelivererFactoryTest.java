package Wiki;

import Server.Deliverer.DelivererBase;
import Server.Deliverer.LogDeliverer;
import TestingSupport.MockDataType;
import Wiki.Deliverer.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WikiDelivererFactoryTest {

    private WikiDelivererFactory wikiDelivererFactory;
    private MockDataType dataType;
    private final int TEST_PORT = 6000;

    @Before
    public void setUp(){
        dataType = new MockDataType();
        wikiDelivererFactory = new WikiDelivererFactory("wiki-test-log", dataType, TEST_PORT);
    }

    @Test
    public void testGetDelivererReturnsHomePageDelivererOnHomeRoute() throws Exception {
        String request = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof HomePageDeliverer);
    }

    @Test
    public void testGetDelivererReturnsCreatePostDeliverOnCreateRoute() throws Exception {
        String request = "GET /create-post HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof CreatePostDeliverer);
    }

    @Test
    public void testGetDelivererReturnsCreatePostDeliverOnPostCreate() throws Exception {
        String request = "POST /create-post HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n\r\n" +
                "title=something&content=anotherone";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof CreatePostDeliverer);
    }

    @Test
    public void testGetDelivererReturnsViewPostDeliverOnPostRoute() throws Exception {
        String request = "GET /post/ATitle-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        dataType.title = "ATitle";
        dataType.content = "Content";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof ViewPostDeliverer);
    }

    @Test
    public void testGetDelivererReturnsEditPostDeliverOnEditRoute() throws Exception {
        String request = "GET /edit/ATitle-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        dataType.title = "ATitle";
        dataType.content = "Content";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof EditPostDeliverer);
    }

    @Test
    public void testGetDelivererReturnsEditPostDeliverOnPostRoute() throws Exception {
        String request = "POST /edit/A-Title-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n\r\n" +
                "title=something&content=anotherone";
        dataType.title = "ATitle";
        dataType.content = "Content";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof EditPostDeliverer);
    }

    @Test
    public void testGetDelivererReturnsDeletePostDeliverer() throws Exception {
        String request = "POST /delete/A_Title-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        dataType.title = "ATitle";
        dataType.content = "Content";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof DeletePostDeliverer);
    }

    @Test
    public void testGetDelivererReturnsLogDeliverer() throws Exception {
        String request = "GET /logs/ HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof LogDeliverer);
    }

    @Test
    public void testGetDelivererReturnsTempPostDeliverer() throws Exception {
        String request = "POST /tmp/A-Title HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n\r\n" +
                "content=anotherone";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof TempPostDeliverer);
    }
}