package Wiki.DelivererSupport;

public class PathParser {

    public static int getIDFromPath(String postPath){
        String[] splitByHyphen = postPath.split("-");
        String stringID = splitByHyphen[1];
        return Integer.parseInt(stringID);
    }
}
