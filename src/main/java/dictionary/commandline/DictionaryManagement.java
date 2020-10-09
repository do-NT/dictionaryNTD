package dictionary.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryManagement {
    public Dictionary dict = new Dictionary();

    // insert from commandline
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int numberOfWords = sc.nextInt();
        String randomWord = sc.nextLine();
        String wordTarget, wordExplain;
        for (int i = 0; i < numberOfWords; i++) {
            wordTarget = sc.nextLine();
            wordExplain = sc.nextLine();
            Word newWord = new Word(wordTarget, wordExplain);
            dict.addWord(newWord);
        }
    }

    // insert from file
    public void insertFromFile() {
        try {
            File myObj = new File("D:\\thanhdonguyen\\3. Java Programming\\dictionaryNTD\\src" +
                    "\\main\\resources\\fxml\\dictionaries.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] data = line.split("\t");
                Word newWord = new Word(data[0], data[1]);
                dict.addWord(newWord);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            // e.printStackTrace();
        }
    }

    // look up word
    public void dictionaryLookup() {
        System.out.print("Type your word you want to look up: ");
        Scanner sc = new Scanner(System.in);
        String myWord = sc.nextLine();
        int i;
        for (i = 0; i < Dictionary.currentIndex; i++) {
            if (dict.word[i].getWord_target().equals(myWord)) {
                System.out.println("The meaning of " + myWord + " is: " + dict.word[i].getWord_explain());
            }
        }
        if (i == Dictionary.currentIndex) {
            System.out.println("We can't find your word");
        }
    }

    // show a single word and its explanation
    public void showWord(int i) {
        System.out.printf("| %-12d| %-20s | %s\n", i,
                dict.getWord(i).getWord_target(), dict.getWord(i).getWord_explain());
    }

    // show all the words
    public void showAllWords() {
        System.out.println("| index       | word                 | meaning ");
        for (int i = 0; i < Dictionary.currentIndex; i++) {
            showWord(i);
        }
    }

    // delete a word
    public void DeleteWord() {
        dict.deleteWord();
    }

    // update a word
    public void UpdateWord() {
        dict.updateWord();
    }
}
