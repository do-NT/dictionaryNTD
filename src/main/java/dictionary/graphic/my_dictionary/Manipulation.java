package dictionary.graphic.my_dictionary;

import dictionary.graphic.database.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Manipulation {

    public void createMyDictionary() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name your new dictionary: ");
        String name = sc.nextLine();
        Connection conn = (new MySQL()).connectToDB();

        if (conn != null) {
            // System.out.println("Connected");
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet rs = dbm.getTables(null, null, name, null);
            if (rs.next()) {
                System.out.println("That dictionary has already existed\nPlease try again.");
                return;
            } else {
                String sql = "CREATE TABLE IF NOT EXISTS " + name + " (" +
                        "word varchar(25) not null, " +
                        "wordtype varchar(10), " +
                        "pronunciation varchar(40), " +
                        "meaning text not null" +
                        ")  ENGINE=MyISAM DEFAULT CHARSET=latin1;";
                Statement statement = conn.createStatement();
                int result = statement.executeUpdate(sql);
                if (result == 0) {
                    System.out.println("Your dictionary " + name + " successfully created!");
                } else {
                    System.out.println("Errors occured.");
                }
            }

        } else {
            System.out.println("Errors on connecting to database");
        }
    }

    public void deleteMyDictionary(String targetDict) throws SQLException {
        // ArrayList<String> myDicts = new ArrayList<String>();
        Connection conn = (new MySQL()).connectToDB();
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet rs = dbm.getTables(null, null, targetDict, null);
        if (rs.next()) {
            String sql = "DROP TABLE " + targetDict + ";";
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
            if (result == 0) {
                System.out.println("The dictionary " + targetDict + " deleted successfully!");
            } else {
                System.out.println("Errors occured.");
            }
        } else {
            System.out.println("That dictionary does not exist\nPlease try again.");
        }
    }

    public void addWord(String word) {

    }

    public void deleteWord(String word) {

    }

    public void updateWord(String word) {

    }
}
