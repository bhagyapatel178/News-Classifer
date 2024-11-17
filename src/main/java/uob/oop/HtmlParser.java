package uob.oop;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        //TODO Task 1.1 - 5 marks

    if (_htmlCode.contains("<title>") && _htmlCode.contains("</title>")) {
        int startIndex = _htmlCode.indexOf("<title>");
        int endIndex = _htmlCode.indexOf("</title>");
        String fullTitle = _htmlCode.substring(startIndex,endIndex);
        int startTitleIndex = fullTitle.indexOf("<title>");
        int endTitleIndex = fullTitle.indexOf("|");
        String title = fullTitle.substring(startTitleIndex+7, endTitleIndex-1);

        return title.trim();
        }
    else{
    return "Title not found!";}
    }

    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        //TODO Task 1.2 - 5 marks
        if (_htmlCode.contains("articleBody")) {
            int startIndex = _htmlCode.indexOf("articleBody");
            int endIndex = _htmlCode.indexOf("mainEntityOfPage");
            String content = _htmlCode.substring(startIndex+15, endIndex-3);

            return content.toLowerCase().trim();
        }
        else {
            return "Content not found!";
        }
    }

}
