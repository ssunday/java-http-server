package Wiki.HTML;

public class ContentProcessing {

    public static String hyperlinkPostTitles(String content, String[] titles, String[] ids){
        String linkedBody = "";
        String[] contentWords = content.split("\\s+");
        for (int i = 0; i < contentWords.length; i++){
            String word = contentWords[i];
            if (isInPostTitleFormat(word)){
                linkedBody += addLink(word, titles, ids);
            }
            else{
                linkedBody += word;
            }
            linkedBody += " ";
        }
        return linkedBody.trim();
    }

    private static boolean isInPostTitleFormat(String word){
        return word.contains("_");
    }

    private static String addLink(String word, String[] titles, String[] ids){
        int index = postIndexInList(parsePunctuation(word), titles);
        String linkedWord;
        if (index != -1){
            linkedWord = existingPostLink(word, titles[index],ids[index]);
        } else{
            linkedWord = nonExistingPostLink(word, parsePunctuation(word));
        }
        return linkedWord;
    }

    private static String nonExistingPostLink(String word, String title){
        return String.format("<a href='/tmp/%s'>%s</a>", title, word);
    }

    private static String existingPostLink(String word, String title, String id){
        return String.format("<a href='/post/%s-%s'>%s</a>", title, id, word);
    }

    private static String parsePunctuation(String word){
        return word.replaceAll("(\\w+)\\p{Punct}(\\s|$)", "$1$2");
    }

    private static int postIndexInList(String word, String[] titles){
        int index = -1;
        for (int i = 0; i < titles.length; i++){
            if (word.equals(titles[i])){
                index = i;
            }
        }
        return index;
    }

}
