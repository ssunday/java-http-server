package Wiki.HTML;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class TempPostTemplateTest {

    private TempPostTemplate tempPostTemplate;
    private String title;

    @Before
    public void setUp(){
        title = "TempTitle";
        tempPostTemplate = new TempPostTemplate(title);
    }

    @Test
    public void testRenderPageIncludesTempTitle(){
        assertThat(tempPostTemplate.renderPage(), containsString(title));
    }

    @Test
    public void testRenderPageIncludesFormToCreatePost(){
        assertThat(tempPostTemplate.renderPage(), containsString("</form>"));
    }
}