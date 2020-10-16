package dictionary.graphic.sentence_translator;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Scanner;

public class JustTranslateAPI {
    // chuan hoa text va lay url
    public static String getURL(String text) throws UnsupportedEncodingException {
        String url = "https://rapidapi.p.rapidapi.com/?text=" + URLEncoder.encode(text, "UTF-8")
                + "&lang_from=en&lang_to=vi";
        return url;
    }

    public static void translateText(String value) throws IOException, UnirestException {
        HttpResponse<String> response = Unirest.get(getURL(value))
                .header("x-rapidapi-host", "just-translated.p.rapidapi.com")
                .header("x-rapidapi-key", "1e50165eb2msh67e5327e5c8f318p1b3da3jsnf4f2baf596f6")
                .asString();

        String jsonString = response.getBody();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray arr = jsonObject.getJSONArray("text");
        String text = arr.getString(0);
        System.out.println(text);
    }

    public static void main(String[] args) throws IOException, SQLException, UnirestException {
        Scanner sc = new Scanner(System.in);
//        System.out.print("Translate: ");
        String line = sc.nextLine();
        translateText(line);
        // WordLookUp w = new WordLookUp();
        // w.wordLookUp(line);
    }
}
