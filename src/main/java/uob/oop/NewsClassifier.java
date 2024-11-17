package uob.oop;

import java.text.DecimalFormat;

public class    NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;

    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";

    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML();
        myStopWords = myTK.loadStopWords();

        loadData();
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }

    public void loadData() {
        //TODO 4.1 - 2 marks
    newsTitles = new String [myHTMLs.length];
    newsContents = new String[myHTMLs.length];

    for (int index = 0;index < myHTMLs.length; index= index+1) {
            newsTitles[index] = HtmlParser.getNewsTitle(myHTMLs[index]);
            newsContents [index] = HtmlParser.getNewsContent(myHTMLs[index]);
        }
    }

    public String[] preProcessing() {
        String[] myCleanedContent = null;
        //TODO 4.2 - 5 marks
       myCleanedContent = new String [newsContents.length];
        for (int index = 0;index < myHTMLs.length; index= index+1) {
            myCleanedContent[index] = NLP.removeStopWords(NLP.textLemmatization(NLP.textCleaning(newsContents[index])), myStopWords);
        }

        return myCleanedContent;
    }

    public double[][] calculateTFIDF(String[] _cleanedContents) {
        String[] vocabularyList = buildVocabulary(_cleanedContents);
        double[][] myTFIDF = null;

        //TODO 4.3 - 10 marks
        double[][] baseMyTFIDF = new double[_cleanedContents.length][vocabularyList.length];
        myTFIDF = new double[_cleanedContents.length][vocabularyList.length];

        for (int index1 = 0; index1 < _cleanedContents.length; index1 = index1 + 1) {
            String[] baseWords = _cleanedContents[index1].split(" ");
            int totalWords = baseWords.length;

            for (int index2 = 0; index2 < vocabularyList.length; index2 = index2 +1) {
                String phase = vocabularyList[index2];
                double TFtermFrequency = 0;

                for (String word : baseWords) {
                    if (word.equals(phase)) {
                        TFtermFrequency= TFtermFrequency + 1.0;
                    }
                }
                TFtermFrequency = TFtermFrequency/ totalWords;

                int numArticlesWithTerm = 0;

                for (String contentStrings : _cleanedContents) {
                    String[] contentWords = contentStrings.split(" ");
                    for (String contentWord : contentWords) {
                        if (contentWord.equals(phase)) {
                            numArticlesWithTerm = numArticlesWithTerm+1;
                            break;
                        }
                    }
                }
                double IDFinvDocFreq = (Math.log((double) _cleanedContents.length / (numArticlesWithTerm)) + 1);
                baseMyTFIDF[index1][index2] = TFtermFrequency * IDFinvDocFreq;
            }
        }
        for (int index1 = 0; index1 < myTFIDF.length; index1= index1+1) {
            for (int index2 = 0; index2 < myTFIDF[0].length; index2 = index2+1) {
                myTFIDF[index1][index2] = baseMyTFIDF[index1][index2];
            }
        }
        return myTFIDF;
    }

    public String[] buildVocabulary(String[] _cleanedContents) {
        String[] arrayVocabulary = null;

        //TODO 4.4 - 10 marks
        for (String cleanedContentString : _cleanedContents) {
            String[] nonUniqueWords = cleanedContentString.split(" ");
            if (arrayVocabulary == null) {
                arrayVocabulary = new String[0];
            }
            for (String vocabWord : nonUniqueWords) {
                int boolNumber = -500;

                for (int index = 0; index < arrayVocabulary.length; index=index+1) {
                    if (arrayVocabulary[index].equals(vocabWord)) {
                        boolNumber = index;
                        break;
                    }
                }
                if (boolNumber == -500) {
                    String[] updatingVocab = new String[arrayVocabulary.length + 1];
                    System.arraycopy(arrayVocabulary, 0, updatingVocab, 0, arrayVocabulary.length);
                    updatingVocab[arrayVocabulary.length] = vocabWord;
                    arrayVocabulary = updatingVocab;
                }
            }
        }
        return arrayVocabulary;
    }

    public double[][] newsSimilarity(int _newsIndex) {
        double[][] mySimilarity = null;

        //TODO 4.5 - 15 marks
        mySimilarity = new double[newsTFIDF.length][2];
        Vector initialVec = new Vector(newsTFIDF[_newsIndex]);

        for (int index = 0; index < newsContents.length; index = index +1) {
            Vector otherVec = new Vector(newsTFIDF[index]);

            mySimilarity[index][1] = initialVec.cosineSimilarity(otherVec);
            mySimilarity[index][0] = index;
        }
        for (int i = 0; i < newsContents.length - 1; i=i+1) {
            for (int j = i+1; j < newsContents.length; j=j+1) {
                if (mySimilarity[i][1] < mySimilarity[j][1]) {
                    double firstCompar = mySimilarity[i][0];
                    mySimilarity[i][0] = mySimilarity[j][0];
                    mySimilarity[i][1] = mySimilarity[j][1];

                    double secCompar = mySimilarity[i][1];
                    mySimilarity[j][0] = secCompar;
                    mySimilarity[j][1] = firstCompar;
                }
            }
        }
        return mySimilarity;
    }

    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1 = null, arrayGroup2 = null;

        //TODO 4.6 - 15 marks
        int totalNumArticles = newsContents.length;
        int title1Index = 0;
        int title2Index = 0;

        for (int index = 0; index < totalNumArticles; index=index+1) {
            if (newsTitles[index].equals(_firstTitle)) {
                title1Index = index;
            } else if (newsTitles[index].equals(_secondTitle)) {
                title2Index = index;
            }
        }
        int []interArrayGroup1 = new int[newsContents.length];
        int group1Mag = 0;

        int []interArrayGroup2 = new int[newsContents.length];
        int group2Mag = 0;

        for (int index = 0; index < newsContents.length; index = index+1) {
            Vector ogVec = new Vector(newsTFIDF[index]);
            double CSTitle1 = ogVec.cosineSimilarity(new Vector(newsTFIDF[title1Index]));
            double CSTitle2 = ogVec.cosineSimilarity(new Vector(newsTFIDF[title2Index]));

            if (CSTitle1 > CSTitle2) {
                interArrayGroup1[group1Mag] = index;
                group1Mag= group1Mag + 1;
            } else {
                interArrayGroup2[group2Mag] = index;
                group2Mag= group2Mag + 1;
            }
        }
        arrayGroup1=new int[group1Mag];

        for (int j = 0; j < group1Mag  ; j= j+1){
            arrayGroup1[j]=interArrayGroup1[j];
        }
        arrayGroup2=new int[group2Mag];

        for (int s = 0; s < group1Mag ; s=s+1){
            arrayGroup2[s]=interArrayGroup2[s];
        }
        return resultString(arrayGroup1, arrayGroup2);
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
