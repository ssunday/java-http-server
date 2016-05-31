package Wiki.HTML;

public class InputJavascript {

    public static String getTitleInputForm(String titleValue){
        return String.format("<html>\n" +
                "<input type='text' name='title' value=%s onkeyup=\"nospaces(this)\"/>\n" +
                "<script>\n" +
                "function nospaces(t){\n" +
                "\n" +
                "    if( t.value.match(/\\s/g)){\n" +
                "\n" +
                "        alert('Title cannot have spaces');\n" +
                "\n" +
                "        t.value= t.value.replace(/\\s/g,'');\n" +
                "    }\n" +
                "\n" +
                "}\n" +
                "</script>\n" +
                "</html>", titleValue);
    }
}
