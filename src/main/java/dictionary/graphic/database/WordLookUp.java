package dictionary.graphic.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WordLookUp {
    MySQL db = new MySQL();
    public void wordLookUp(String word) throws SQLException {
        Connection conn = db.connectToDB();
        if (conn != null) {
            System.out.println("Connected");
            String sql = "SELECT * FROM entries WHERE word = \'" + word + "\'";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            System.out.println(word + "\n");
            while (result.next()) {
                String wordtype = result.getString("wordtype");
                String def = result.getString("definition");
                System.out.println(wordtype);
                System.out.println(def);
                System.out.println("\n");
            }
        }
    }
}
