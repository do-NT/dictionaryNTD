package dictionary.graphic.my_dictionary;

import dictionary.graphic.db_dictionary.MySQL;
import dictionary.graphic.db_dictionary.WordLookUp;

import java.sql.*;
import java.util.Scanner;

public class Manipulation {

    private Connection conn;

    public Manipulation() throws SQLException {
        conn = (new MySQL()).connectToDB();
    }

    public String createMyDictionary(String name) {
        String message = new String("Errors occured!");
        if (conn != null) {
            // System.out.println("Connected");
            try {
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet rs = dbm.getTables(null, null, name, null);
                if (rs.next()) {
                    message = "That list has already existed!";
                    System.out.println(message);
                    return message;
                } else {
                    String sql = "CREATE TABLE IF NOT EXISTS " + name + " (" +
                            "word varchar(25) not null, " +
                            "wordtype varchar(10), " +
                            // "pronunciation varchar(40), " +
                            "definition text not null" +
                            ")  ENGINE = MyISAM DEFAULT CHARSET = latin1;";
                    Statement statement = conn.createStatement();
                    int result = statement.executeUpdate(sql);
                    if (result == 0) {
                        message = "Your new list " + name + " is successfully created!";
                        System.out.println(message);
                    } else {
                        message = "Errors occured.";
                        System.out.println(message);
                    }
                }
            } catch (SQLException e) {
                message = "The name you chose is invalid, try again!";
                System.out.println(message);
            }

        } else {
            message = "Errors on connecting to database";
            System.out.println(message);
        }
        return message;
    }

    public String deleteMyDictionary(String targetDict) throws SQLException {
        String message = new String();

        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet rs = dbm.getTables(null, null, targetDict, null);
        if (rs.next()) {
            String sql = "DROP TABLE " + targetDict + ";";
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
            if (result == 0) {
                message = "The list " + targetDict + " has been deleted!";
                System.out.println(message);
            } else {
                message = "Errors occured.";
                System.out.println(message);
            }
        } else {
            message = "That list does not exist!";
            System.out.println(message);
        }
        return message;
    }

    public String addWord(Word word, String dict) {
        String sql = "INSERT INTO " + dict +  " (word, wordtype, definition) VALUES (?, ?, ?)";
        String message = new String();

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, word.getWord());
            statement.setString(2, word.getWordtype());
            statement.setString(3, word.getDefinition());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                message = "A new word has been inserted successfully!";
                System.out.println(message);
            } else {
                message = "Errors occured! Check your list name carefully.";
                System.out.println(message);
            }
        } catch (SQLException e) {
            message = "Errors occured! Check your list name carefully.";
            System.out.println(message);
        }
        return message;
    }

    public String deleteWord(String word_target, String dict) {
        String sql = "DELETE FROM " + dict + " WHERE word = ?";
        String message = new String();

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, word_target);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                message = "The word " + word_target + " has been deleted from " + dict + ".";
                System.out.println(message);
            } else {
                message = "That word doesn't exist! Check your typo.";
                System.out.println(message);
            }
        } catch (SQLException e) {
            message = "That word doesn't exist! Check your typo.";
            System.out.println(message);
        }
        return message;
    }

    public String updateWord(String word_target, String dict, Word word) {
        String sql = "UPDATE " + dict + " SET wordtype = ?, definition = ? WHERE word = ?";
        String message = new String();

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, word.getWordtype());
            statement.setString(2, word.getDefinition());
            statement.setString(3, word_target);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                message = "An existing word was updated successfully!";
                System.out.println(message);
            } else {
                message = "That word doesn't exist! Check your typo";
                System.out.println(message);
            }
        } catch (SQLException e) {
            message = "That word doesn't exist! Check your typo";
            System.out.println(message);
        }

        return message;
    }

    public String lookUpWord(String word_target, String dict) throws SQLException {
        return (new WordLookUp()).wordLookUp(word_target, dict);
    }
}
