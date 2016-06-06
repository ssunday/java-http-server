package Cobspec.HTML;

import HTMLTemplating.PageTemplate;

public class ParameterDisplayTemplate extends PageTemplate{

    private String[] params;

    public ParameterDisplayTemplate(String[] params){
        this.pageTitle = "Parameter Display";
        this.params = params;
    }

    @Override
    public String getBody(){
        String html = "";
        for (String param: params){
            html += addParam(param);
        }
        return html;
    }

    private String addParam(String param){
        return "<p>" + param + "</p>";
    }
}
