package dictionary.graphic.my_dictionary;

public class Word {
    private String word;
    private String wordtype;
    private String definition;

    public Word(String word, String wordtype, String definition) {
        this.definition = definition;
        this.word = word;
        this.wordtype = wordtype;
    }

    public Word(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.wordtype = "";
    }

    public String getDefinition() {
        return definition;
    }

    public String getWord() {
        return word;
    }

    public String getWordtype() {
        return wordtype;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWordtype(String wordtype) {
        this.wordtype = wordtype;
    }

}
