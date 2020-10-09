package dictionary.commandline;

import java.util.Scanner;

public class Dictionary {
    public Word[] word = new Word[3000];
    public static int currentIndex = 0;

    public Word getWord(int i) {
        return word[i];
    }

    public void addWord(Word newWord) {
        word[currentIndex++] = newWord;
    }

    public void deleteWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What word you want to delete from the dictionary?");
        String badWord = sc.nextLine();
        int badWordIndex = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (word[i].getWord_target().equals(badWord)) {
                badWordIndex = i;
                break;
            }
        }

        if (badWordIndex != -1) {
            for (int i = badWordIndex; i < currentIndex; i++) {
                word[i] = word[i+1];
            }
            currentIndex--;
        } else {
            System.out.println("That word isn't in the dictionary!");
        }
    }

    public void updateWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What word you want to update from the dictionary?");
        String badWord = sc.nextLine();
        int wordIndex = -1;
        for (int i = 0; i < currentIndex; i++) {
            if (word[i].getWord_target().equals(badWord)) {
                wordIndex = i;
                break;
            }
        }
        if (wordIndex != -1) {
            System.out.println("Enter your definition: ");
            String newDef = sc.nextLine();
            word[wordIndex].setWord_explain(newDef);
            System.out.println("Word updated successfully!");
            currentIndex--;
        } else {
            System.out.println("That word isn't in the dictionary!");
        }
    }
}
