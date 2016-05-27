package Routes.Wiki.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ViewPostTemplateTest {

    private ViewPostTemplate viewPostTemplate;

    @Test
    public void testRenderPageHasPostContent(){
        String postTitle = "Post title";
        String postContent = "THIS IS MY POST CONTENT";
        viewPostTemplate = new ViewPostTemplate(1, postTitle, postContent);
        assertThat(viewPostTemplate.renderPage(), containsString(postContent));
    }

    @Test
    public void testRenderPageHasPostTitle(){
        String postTitle = "Post title";
        String postContent = "THIS IS MY POST CONTENT";
        viewPostTemplate = new ViewPostTemplate(1, postTitle,postContent);
        assertThat(viewPostTemplate.renderPage(), containsString(postTitle));
    }

    @Test
    public void testRenderPageHasHomeLink(){
        String postTitle = "Post title";
        String postContent = "THIS IS MY POST CONTENT";
        viewPostTemplate = new ViewPostTemplate(1, postTitle,postContent);
        assertThat(viewPostTemplate.renderPage(), containsString("<a href='/'>"));
    }

}