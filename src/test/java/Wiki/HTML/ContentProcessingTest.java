package Wiki.HTML;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContentProcessingTest {

    @Test
    public void testHyperlinkPostTitlesHyperlinksWordsInTitleFormatToTempPostPage(){
        String content = "A Post_Title";
        String[] postTitles = new String[0];
        String[] postIDs = new String[0];
        String newContent = ContentProcessing.hyperlinkPostTitles(content, postTitles, postIDs);
        assertEquals("A <a href='/tmp/Post_Title'>Post_Title</a>", newContent);
    }

    @Test
    public void testHyperlinkPostTitlesHyperlinksWordsInTitleFormatToTempPostPageWithPunctuation(){
        String content = "A Post_Title.";
        String[] postTitles = new String[0];
        String[] postIDs = new String[0];
        String newContent = ContentProcessing.hyperlinkPostTitles(content, postTitles, postIDs);
        assertEquals("A <a href='/tmp/Post_Title'>Post_Title.</a>", newContent);
    }

    @Test
    public void testHyperlinkPostTitlesHyperlinksWordsInTitleFormatToPost(){
        String content = "A Post_Title";
        String[] postTitles = {"Post_Title"};
        String[] postIDs = {"1"};
        String newContent = ContentProcessing.hyperlinkPostTitles(content, postTitles, postIDs);
        assertEquals("A <a href='/post/Post_Title-1'>Post_Title</a>", newContent);
    }

    @Test
    public void testHyperlinkPostTitlesHyperlinksWordsInTitleFormatToPostWithPunctuation(){
        String content = "A Post_Title.";
        String[] postTitles = {"Post_Title"};
        String[] postIDs = {"1"};
        String newContent = ContentProcessing.hyperlinkPostTitles(content, postTitles, postIDs);
        assertEquals("A <a href='/post/Post_Title-1'>Post_Title.</a>", newContent);
    }

    @Test
    public void testHyperlinkPostTitlesHyperlinksMultipleTitles(){
        String content = "A Post_Title Non_Existent";
        String[] postTitles = {"Post_Title"};
        String[] postIDs = {"1"};
        String newContent = ContentProcessing.hyperlinkPostTitles(content, postTitles, postIDs);
        assertEquals("A <a href='/post/Post_Title-1'>Post_Title</a> <a href='/tmp/Non_Existent'>Non_Existent</a>", newContent);
    }

}