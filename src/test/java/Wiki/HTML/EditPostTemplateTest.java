package Wiki.HTML;

import Wiki.HTML.EditPostTemplate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class EditPostTemplateTest {

    private EditPostTemplate editPostTemplate;

    @Test
    public void testRenderPageHasOldContent(){
        String title = "ATitle";
        String oldContent = "This is my old content";
        editPostTemplate = new EditPostTemplate(1, title, oldContent);
        assertThat(editPostTemplate.renderPage(), containsString(oldContent));
    }

    @Test
    public void testRenderPageHasSaveChangesButton(){
        String title = "ATitle";
        String oldContent = "This is my old content";
        editPostTemplate = new EditPostTemplate(1, title, oldContent);
        assertThat(editPostTemplate.renderPage(), containsString("Save Changes"));
    }

}