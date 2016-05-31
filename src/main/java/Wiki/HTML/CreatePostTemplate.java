package Wiki.HTML;

public class CreatePostTemplate extends PageTemplate {

    public CreatePostTemplate(){
        this.pageTitle = "Create Post";
    }

    @Override
    protected String getBody(){
        String body = formStart();
        body += postTitleInput();
        body += postContentInput();
        body += submitButton();
        body += formEnd();
        return body;
    }

    private String formStart(){
        return "<form action='/create-post' method='post'>";
    }

    private String postTitleInput(){
        return "<label for='title'>Post Title</label><br>" + InputJavascript.getTitleInputForm("YourPostTitle");
    }

    private String postContentInput(){
        return "<br><br><label for='content'>Post Content</label><br>" +
                "<TEXTAREA name='content' ROWS=3 COLS=30>Your Post Content</TEXTAREA>";
    }

    private String submitButton(){
        return "<br><br><input type='submit' value='Submit'>";
    }

    private String formEnd(){
        return "</form>";
    }
}
