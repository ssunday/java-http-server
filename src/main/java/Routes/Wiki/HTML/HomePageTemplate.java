package Routes.Wiki.HTML;

public class HomePageTemplate {

    private String[] postIDs;
    private String[] postTitles;

    public HomePageTemplate(String[] postIDs, String[] postTitles){
        this.postIDs = postIDs;
        this.postTitles = postTitles;
    }

    public String renderHomePage(){
        String html = startHTML();
        html += body();
        html += endHTML();
        return html;
    }

    private String startHTML(){
        return "<HTML><HEAD><style> .body {padding = 60px;}</style>"
                + "<title>Home Page</title>" +
                "<h1>Home Page</h1></HEAD>";
    }

    private String body(){
        String body = "<body>";
        body += postList();
        body += addCreatePostButton();
        body += "</body>";
        return body;
    }

    private String postList(){
        String htmlWikiPosts = "";
        for (int i = 0; i < postIDs.length; i++){
            htmlWikiPosts += String.format("<p><a href='/post-%s'>%s</a></p>", postIDs[i],postTitles[i]);
        }
        return htmlWikiPosts;
    }

    private String addCreatePostButton(){
        return "<form action= '/create-post' method='post'><input type='submit' value='Create Post'></form>";
    }

    private String endHTML(){
        return "</HTML>";
    }
}
