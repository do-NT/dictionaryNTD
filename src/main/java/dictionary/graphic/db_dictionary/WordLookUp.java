package dictionary.graphic.db_dictionary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WordLookUp {
    public String wordLookUp(String word_target, String dict) throws SQLException {
        Connection conn = (new MySQL()).connectToDB();
        StringBuilder result = new StringBuilder();
        if (conn != null) {
            // System.out.println("Connected");
            // String sql = "SELECT * FROM entries WHERE word = \'" + word + "\'";
            String sql = "SELECT * FROM " + dict + " WHERE word = \'" + word_target + "\'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            result.append(word_target + "\n\n");
            while (rs.next()) {
                String wordtype = rs.getString("wordtype");
                String def = rs.getString("definition");

                result.append(wordtype + '\n');
                result.append(def + "\n\n");
            }
        }
        return result.toString();
    }
}
