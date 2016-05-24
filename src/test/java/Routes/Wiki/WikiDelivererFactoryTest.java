package Routes.Wiki;

import Routes.DelivererBase;
import Routes.Wiki.Deliverer.HomePageDeliverer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WikiDelivererFactoryTest {

    private WikiDelivererFactory wikiDelivererFactory;

    @Before
    public void setUp(){
        wikiDelivererFactory = new WikiDelivererFactory(null);
    }

    @Test
    public void testGetDelivererReturnsHomePageDelivererOnHomeRoute() throws Exception {
        String request = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = wikiDelivererFactory.getDeliverer(request);
        assertTrue(deliverer instanceof HomePageDeliverer);
    }
}