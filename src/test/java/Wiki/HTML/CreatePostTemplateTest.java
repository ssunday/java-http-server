package Wiki.HTML;

import Wiki.HTML.CreatePostTemplate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class CreatePostTemplateTest {

    private CreatePostTemplate createPostTemplate;

    @Before
    public void setUp(){
        createPostTemplate = new CreatePostTemplate();
    }

    @Test
    public void testRenderPageIncludesCreatePost(){
        assertThat(createPostTemplate.renderPage(), containsString("Create Post"));
    }

    @Test
    public void testRenderPageIncludesForm(){
        assertThat(createPostTemplate.renderPage(), containsString("</form>"));
    }

    @Test
    public void testRenderPageIncludesInput(){
        assertThat(createPostTemplate.renderPage(), containsString("<input"));
    }

    @Test
    public void testRenderPageIncludesContent(){
        assertThat(createPostTemplate.renderPage(), containsString("Content"));
    }
}