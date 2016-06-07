package Wiki.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class DeletePostTemplateTest {

    private DeletePostTemplate deletePostTemplate;

    @Test
    public void testRenderPageHasPostTitle(){
        deletePostTemplate = new DeletePostTemplate("Title", 1);
        assertThat(deletePostTemplate.renderPage(), containsString("Title"));
    }

    @Test
    public void testRenderPageHasHomeButton(){
        deletePostTemplate = new DeletePostTemplate("Title", 1);
        assertThat(deletePostTemplate.renderPage(), containsString("Home"));
    }

}