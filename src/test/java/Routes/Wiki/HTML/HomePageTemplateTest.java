package Routes.Wiki.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class HomePageTemplateTest {

    private HomePageTemplate homePageTemplate;

    @Test
    public void testRenderHomePageIncludesHomePage(){
        homePageTemplate =  new HomePageTemplate(null, null);
        assertThat(homePageTemplate.renderHomePage(), containsString("Home Page"));
    }

    @Test
    public void testRenderHomePageIncludesCreatePostButton(){
        homePageTemplate =  new HomePageTemplate(null, null);
        assertThat(homePageTemplate.renderHomePage(), containsString("Create Post"));
    }


    @Test
    public void testRenderHomePageIncludesPostListWithTitles(){
        String[] postIDs = {"1", "3"};
        String[] titles = {"A Title", "Some Title"};
        homePageTemplate =  new HomePageTemplate(postIDs, titles);
        assertThat(homePageTemplate.renderHomePage(), containsString("A Title"));
        assertThat(homePageTemplate.renderHomePage(), containsString("Some Title"));
    }

    @Test
    public void testRenderHomePageIncludesPostIDLinks(){
        String[] postIDs = {"1", "3"};
        String[] titles = {"A Title", "Some Title"};
        homePageTemplate =  new HomePageTemplate(postIDs, titles);
        assertThat(homePageTemplate.renderHomePage(), containsString("href='/post-1'"));
        assertThat(homePageTemplate.renderHomePage(), containsString("href='/post-3'"));
    }
}