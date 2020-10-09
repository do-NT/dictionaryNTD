package dictionary.commandline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement d = new DictionaryManagement();

    public void dictionaryBasic() {
        d.insertFromCommandline();
        d.showAllWords();
    }

    public void dictionaryAdvanced() {
        d.insertFromFile();
        d.showAllWords();
        d.UpdateWord();
//        d.dictionaryLookup();
//        d.DeleteWord();
//        d.showAllWords();
    }

    public void dictionarySearcher() {
        Scanner sc = new Scanner(System.in);
        System.out.print("What word you want to search: ");
        String myWord = sc.nextLine();
        for (int i = 0; i < Dictionary.currentIndex; i++) {
            if (d.dict.word[i].getWord_target().indexOf(myWord) == 0) {
                d.showWord(i);
            }
        }
    }

    public void dictionaryExportToFile() {
        try {
            File myObj = new File("D:\\thanhdonguyen\\3. Java Programming\\dictionaryNTD\\" +
                    "src\\main\\resources\\fxml\\exportedDictionary.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("D:\\thanhdonguyen\\3. Java Programming\\dictionaryNTD\\" +
                    "src\\main\\resources\\fxml\\exportedDictionary.txt");
            for (int i = 0; i < Dictionary.currentIndex; i++) {
                myWriter.write(d.dict.word[i].getWord_target());
                myWriter.write("\t");
                myWriter.write(d.dict.word[i].getWord_explain());
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
