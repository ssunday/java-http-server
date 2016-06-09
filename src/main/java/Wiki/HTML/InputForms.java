package Wiki.HTML;

public class InputForms {

    public static String formStart(String path){
        return String.format("</xmp><form action=%s method='post'>", path);
    }

    public static String getTitleForm(String title){
        return String.format("<label style='margin: 0px 35px;' for='title'>Post Title</label><br>" +
                "<input type='text' style='margin: 0px 35px;' name='title' value=%s pattern='^[a-zA-Z0-9_]*$' required/>\n", title);
    }

    public static String getPostContentForm(String content) {
        String label = "<br><br><label for='content' style='margin: 0px 35px;'>Post Content</label><br>";
        String form = String.format("<textarea name='content' style='display:inherit;height:180px;margin:0px 35px;' row='10' cols='80' required>%s</textarea>", content);
        return label + form;
    }

    public static String getSubmitButton(String buttonValue){
        return String.format("<br><br><input style='margin: 0px 35px;' type='submit' value=%s>", buttonValue);
    }

    public static String homeLink(){
        return "<button><a href='/'>Home</a></button>";
    }

    public static String formEnd(){
        return "</form>";
    }
}
