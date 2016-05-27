package Routes.Wiki.HTML;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ViewNewPostTemplateTest {

    private ViewNewPostTemplate viewNewPostTemplate;

    @Before
    public void setUp(){
        viewNewPostTemplate = new ViewNewPostTemplate("Created Post", "New Post Title", 1);
    }

    @Test
    public void testRenderPageShowsTitleOfNewlyCreatedPost(){
        assertThat(viewNewPostTemplate.renderPage(), containsString("New Post Title"));
    }

    @Test
    public void testRenderPageHasLinkToPostPage(){
        assertThat(viewNewPostTemplate.renderPage(), containsString("<a href='/post-1'>"));
    }
}