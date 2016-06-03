package Wiki.HTML;

public class InputForms {

    public static String formStart(String path){
        return String.format("<form action=%s method='post'>", path);
    }

    public static String getTitleForm(String title){
        return String.format("<html>\n" +
                "<label for='title'>Post Title</label><br>" +
                "<input type='text' name='title' value=%s onfocusout=\"nospaces(this)\" pattern=\"^[a-zA-Z0-9_]*$\" required/>\n" +
                "<script>\n" +
                "function nospaces(t){\n" +
                "\n" +
                "    if(t.value.match(/\\s/g) || t.value.match(/_$/)){\n" +
                "\n" +
                "        alert('Title must not have spaces or trailing underscores.');\n" +
                "\n" +
                "        t.value = t.value.replace(/\\s/g,'_');\n" +
                "        t.value = t.value.replace(/_$/, '');" +
                "    }\n" +
                "\n" +
                "}\n" +
                "</script>\n" +
                "</html>", title);
    }

    public static String getPostContentForm(String content) {
        String label = "<br><br><label for='content'>Post Content</label><br>";
        String form = String.format("<TEXTAREA name='content' ROWS=10 COLS=60>%s</TEXTAREA>", content);
        return label + form;
    }

    public static String getSubmitButton(String buttonValue){
        return String.format("<br><br><input type='submit' value=%s>", buttonValue);
    }

    public static String formEnd(){
        return "</form>";
    }
}
