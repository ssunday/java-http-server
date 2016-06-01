package Wiki.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class InputFormsTest {

    @Test
    public void testGetTitleInputFormIncludesTitle() throws Exception {
        assertThat(InputForms.getTitleForm("SomeTitle"), containsString("SomeTitle"));
    }

    @Test
    public void testGetPostContentFormIncludesContent() throws Exception {
        assertThat(InputForms.getPostContentForm("Content Content"), containsString("Content Content"));
    }

    @Test
    public void testSubmitButtonIncludesButtonValue() throws Exception {
        assertThat(InputForms.getSubmitButton("Submit"), containsString("Submit"));
    }

    @Test
    public void testFormStartIncludesBeginFormTag() throws Exception {
        assertThat(InputForms.formStart("/create-post"), containsString("<form"));
    }

    @Test
    public void testFormStartIncludesPath() throws Exception {
        assertThat(InputForms.formStart("/create-post"), containsString("/create-post"));
    }

    @Test
    public void testFormEndIsEndFormTag() throws Exception{
        assertEquals(InputForms.formEnd(), "</form>");
    }
}