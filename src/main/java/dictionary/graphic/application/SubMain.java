package dictionary.graphic.application;

import dictionary.graphic.database.WordLookUp;
import dictionary.graphic.my_dictionary.Manipulation;
import dictionary.graphic.sentence_translator.GoogleAPI;

import java.util.Scanner;

public class SubMain {
    public static void main(String[] args) throws Exception {
//        String text = "Hello darkness my old friend";
        System.out.println(new GoogleAPI().textMeaning((new Scanner(System.in)).nextLine()));
//        Manipulation mani = new Manipulation();
//        mani.createMyDictionary();
//        mani.deleteMyDictionary("thanhdopro");
//        (new WordLookUp()).wordLookUp("apple");
    }
}
