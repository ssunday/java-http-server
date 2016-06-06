package Wiki.Deliverer;

import Server.HTTP.HTTPVerbs;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.DelivererSupport.Postgres;
import Wiki.HTML.HomePageTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class HomePageDelivererTest {

    private HomePageDeliverer homePageDeliverer;
    private PostRecorder postRecorder;

    @Before
    public void setUp(){
        postRecorder = new PostRecorder(new Postgres("test"));
        homePageDeliverer = new HomePageDeliverer(postRecorder, HTTPVerbs.GET);
    }

    @Test
    public void testGetBytesReturnsHomePageBytes() throws Exception {
        HomePageTemplate homePageTemplate = new HomePageTemplate(postRecorder.getAllPostIDs(), postRecorder.getAllPostTitles());
        String html = homePageTemplate.renderPage();
        byte[] bytes = html.getBytes();
        assertArrayEquals(bytes, homePageDeliverer.getBytes());
    }

}