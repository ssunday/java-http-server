package Wiki.HTML;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class HomePageTemplateTest {

    private HomePageTemplate homePageTemplate;

    @Test
    public void testRenderPageIncludesHomePage(){
        homePageTemplate =  new HomePageTemplate(new String[0], new String[0]);
        assertThat(homePageTemplate.renderPage(), containsString("Home Page"));
    }

    @Test
    public void testRenderPageIncludesCreatePostButton(){
        homePageTemplate =  new HomePageTemplate(new String[0], new String[0]);
        assertThat(homePageTemplate.renderPage(), containsString("Create Post"));
    }

    @Test
    public void testRenderPageIncludesNoPostWhenNoPosts(){
        homePageTemplate =  new HomePageTemplate(new String[0], new String[0]);
        assertThat(homePageTemplate.renderPage(), containsString("No posts"));
    }

    @Test
    public void testRenderPageIncludesPostListWithTitles(){
        String[] postIDs = {"1", "3"};
        String[] titles = {"A Title", "Some Title"};
        homePageTemplate =  new HomePageTemplate(postIDs, titles);
        assertThat(homePageTemplate.renderPage(), containsString("A Title"));
        assertThat(homePageTemplate.renderPage(), containsString("Some Title"));
    }

    @Test
    public void testRenderPageIncludesPostIDLinks(){
        String[] postIDs = {"1", "3"};
        String[] titles = {"ATitle", "SomeTitle"};
        homePageTemplate =  new HomePageTemplate(postIDs, titles);
        assertThat(homePageTemplate.renderPage(), containsString("href='/post/ATitle-1'"));
        assertThat(homePageTemplate.renderPage(), containsString("href='/post/SomeTitle-3'"));
    }
}