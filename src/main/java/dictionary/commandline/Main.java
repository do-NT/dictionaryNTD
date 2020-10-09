package dictionary.commandline;

public class Main {
    public static void main(String[] args) {
        DictionaryCommandline dC = new DictionaryCommandline();

        dC.dictionaryAdvanced();
//        dC.dictionaryBasic();
//        dC.dictionarySearcher();
        dC.dictionaryExportToFile();
    }
}
