package Wiki.HTML;

import HTMLTemplating.PageTemplate;

public class HomePageTemplate extends PageTemplate {

    private String[] postIDs;
    private String[] postTitles;

    public HomePageTemplate(String[] postIDs, String[] postTitles){
        this.postIDs = postIDs;
        this.postTitles = postTitles;
        this.pageTitle = "Home Page";
    }

    @Override
    protected String getBody(){
        String body = postList();
        body += addCreatePostButton();
        return body;
    }

    private String postList(){
        String htmlWikiPosts = "<h2>Post List:</h2>\n";
        if (postIDs.length == 0 ){
            htmlWikiPosts += "No posts.";
        } else{
            for (int i = 0; i < postIDs.length; i++){
                htmlWikiPosts += String.format("<a href='/post/%s-%s'>%s</a><br>", postTitles[i], postIDs[i],postTitles[i]);
            }
        }
        return htmlWikiPosts + "\n";
    }

    private String addCreatePostButton(){
        return "<hr />\n<h2><a href='/create-post'>Create Post</a></h2>";
    }

}
