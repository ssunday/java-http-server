package Wiki.HTML;

public class HomePageTemplate extends PageTemplate{

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
        String htmlWikiPosts = "<h3>Post List:</h3>";
        if (postIDs.length == 0 ){
            htmlWikiPosts += "No posts.";
        } else{
            for (int i = 0; i < postIDs.length; i++){
                htmlWikiPosts += String.format("<p><a href='/post-%s'>%s</a></p>", postIDs[i],postTitles[i]);
            }
        }
        return htmlWikiPosts;
    }

    private String addCreatePostButton(){
        return "<hr><h3><a href='/create-post'>Create Post</a></h3>";
    }

}
