package uob.oop;

public class NLP {
    /***
     * Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     * @param _content Text that need to be cleaned.
     * @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.1 - 3 marks
        char[] characterArray = _content.toLowerCase().toCharArray();
        for (char a : characterArray) {
            if (Character.isLowerCase(a) || Character.isDigit(a) || a == ' ') {
                sbContent.append(a);
            }
        }
        return sbContent.toString().trim();
    }

    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.2 - 3 marks
        String[] complexWords = _content.split(" ");
        for (String word : complexWords) {
            if (word.endsWith("ing")) {
                word = word.substring(0, word.length() - 3);
            } else if (word.endsWith("ed")) {
                word = word.substring(0, word.length() - 2);
            } else if (word.endsWith("es")) {
                word = word.substring(0, word.length() - 2);
            } else if (word.endsWith("s")) {
                word = word.substring(0, word.length() - 1);
            }

            sbContent.append(word).append(" ");
        }
        return sbContent.toString().trim();
    }

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */
    public static String removeStopWords(String _content, String[] _stopWords) {
        StringBuilder sbConent = new StringBuilder();
        //TODO Task 2.3 - 3 marks
        String[] contentArray = _content.split(" ");

        for (String word : contentArray) {
            boolean isStopWord = false;
            for (String stopWord : _stopWords) {
                if (stopWord.equals(word)) {
                    isStopWord = true;
                    break;
                }
            }
            if (!isStopWord) {
                sbConent.append(word).append(" ");
                }
            }
        return sbConent.toString().trim();
    }
}