package dictionary.graphic.application;

import dictionary.graphic.db_dictionary.WordLookUp;
import dictionary.graphic.my_dictionary.Manipulation;
import dictionary.graphic.my_dictionary.Word;
import dictionary.graphic.sentence_translator.GoogleAPI;
import dictionary.graphic.text_to_speech.Audio;

import java.util.Scanner;

public class SubMain {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
//        Word word = new Word("nguyen thanh do", "lop truong pro qua z");
//        String text = "Hello darkness my old friend";
        System.out.println(new GoogleAPI().textMeaning("chào mọi người", "vi", "en"));
//        Manipulation mani = new Manipulation();
        // mani.createMyDictionary();
//         mani.addWord(word, "thanhdo");
//        mani.deleteWord("nguyen thanh do", "thanhdo");
//        mani.updateWord("nguyen thanh d", "thanhdo", word);
//        mani.deleteMyDictionary("nguyenthanhdo");
//        (new WordLookUp()).wordLookUp("nguyen thanh do", "thanhdo");
//        for (int i = 0; i < 3; i++) {
//            System.out.println(mani.lookUpWord(sc.nextLine(), "entries"));
//        }
//        (new Audio()).speak("Con cóc là cậu ông trời, ai mà đánh cóc là trời đánh cho");
    }
}
