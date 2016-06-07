package Wiki.DelivererSupport;

public class PathParser {

    public static int getIDFromPath(String postPath){
        String[] splitBySlash = postPath.split("/");
        String[] splitByHyphen = splitBySlash[2].split("-");
        String stringID = splitByHyphen[splitByHyphen.length-1];
        return Integer.parseInt(stringID);
    }

    public static String getTitleFromPath(String postPath){
        String[] splitBySlash = postPath.split("/");
        String[] splitByHyphen = splitBySlash[2].split("-");
        String stringTitle = splitByHyphen[0];
        return stringTitle;
    }
}
