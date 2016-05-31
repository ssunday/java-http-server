package Wiki;

import Server.Deliverer.DelivererBase;
import Wiki.Deliverer.CreatePostDeliverer;
import Wiki.Deliverer.EditPostDeliverer;
import Wiki.Deliverer.HomePageDeliverer;
import Wiki.Deliverer.ViewPostDeliverer;
import Wiki.DelivererSupport.Postgres;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class WikiDelivererFactoryTest {

    private WikiDelivererFactory wikiDelivererFactory;
    private Postgres postgres;
    private final int TEST_PORT = 6000;

    @Before
    public void setUp(){
        postgres = new Postgres("test");
        wikiDelivererFactory = new WikiDelivererFactory(postgres, TEST_PORT);
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
        postgres.clearData();
    }

    @Test
    public void testGetDelivererReturnsViewPostDeliverOnPostRoute() throws Exception {
        String request = "GET /post-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        Map map = new HashMap();
        map.put("title", "A Title");
        map.put("content", "Content");
        postgres.addPost(map);
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof ViewPostDeliverer);
        postgres.clearData();
    }

    @Test
    public void testGetDelivererReturnsEditPostDeliverOnEditRoute() throws Exception {
        String request = "GET /edit-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        Map map = new HashMap();
        map.put("title", "A Title");
        map.put("content", "Content");
        postgres.addPost(map);
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof EditPostDeliverer);
        postgres.clearData();
    }

    @Test
    public void testGetDelivererReturnsEditPostDeliverOnPostRoute() throws Exception {
        String request = "POST /edit-1 HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n\r\n" +
                "title=something&content=anotherone";
        Map map = new HashMap();
        map.put("title", "A Title");
        map.put("content", "Content");
        postgres.addPost(map);
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof EditPostDeliverer);
        postgres.clearData();
    }
}