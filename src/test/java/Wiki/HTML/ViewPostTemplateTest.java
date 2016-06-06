package Wiki.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ViewPostTemplateTest {

    private ViewPostTemplate viewPostTemplate;

    @Test
    public void testRenderPageHasPostContent(){
        String postTitle = "PostTitle";
        String postContent = "THIS IS MY POST CONTENT";
        viewPostTemplate = new ViewPostTemplate(1, postTitle, postContent, new String[0], new String[0]);
        assertThat(viewPostTemplate.renderPage(), containsString(postContent));
    }

    @Test
    public void testRenderPageHasPostTitle(){
        String postTitle = "PostTitle";
        String postContent = "THIS IS MY POST CONTENT";
        viewPostTemplate = new ViewPostTemplate(1, postTitle,postContent, new String[0], new String[0]);
        assertThat(viewPostTemplate.renderPage(), containsString(postTitle));
    }

    @Test
    public void testRenderPageHasHomeLink(){
        String postTitle = "PostTitle";
        String postContent = "THIS IS MY POST CONTENT";
        viewPostTemplate = new ViewPostTemplate(1, postTitle,postContent, new String[0], new String[0]);
        assertThat(viewPostTemplate.renderPage(), containsString("<a href='/'>"));
    }

    @Test
    public void testRenderPageHasLinkToEditPage(){
        String postTitle = "Post_Title";
        String postContent = "A Post_Title";
        viewPostTemplate = new ViewPostTemplate(1, postTitle, postContent, new String[0], new String[0]);
        assertThat(viewPostTemplate.renderPage(), containsString("<a href='/edit/Post_Title-1'>"));
    }

    @Test
    public void testRenderPageHasPostTitleLink(){
        String postTitle = "Post_Title";
        String postContent = "A Post_Title2";
        viewPostTemplate = new ViewPostTemplate(1, postTitle, postContent, new String[0], new String[0]);
        assertThat(viewPostTemplate.renderPage(), containsString("<a href='/tmp/Post_Title2'>"));
    }

}