package Routes.Wiki.HTML;

public class EditPostTemplate extends PageTemplate{

    private String title;
    private String content;
    private int postID;

    public EditPostTemplate(int postID, String title, String content){
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.pageTitle = "Editing Post - " + title;
    }

    @Override
    public String getBody() {
        String body = formStart();
        body += editPostTitle();
        body += editPostContent();
        body += saveChanges();
        body += formEnd();
        return body;
    }

    private String formStart(){
        return String.format("<form action='/edit-%s' method='post'>", postID);
    }

    private String editPostTitle() {
        String label = "<label for=\"title\">Post Title</label><br>";
        String input = String.format("<input type=\"text\" id=\"title\" name =\"title\" value='%s'/>", title);
        return label + input;

    }

    private String editPostContent() {
        String label = "<br><br><label for='content'>Post Content</label><br>";
        String form = String.format("<TEXTAREA NAME='content' ROWS=3 COLS=30>%s</TEXTAREA>", content);
        return label + form;
    }

    private String saveChanges(){
        return "<br><br><input type='submit' value='Save Changes'>";
    }

    private String formEnd(){
        return "</form>";
    }
}
