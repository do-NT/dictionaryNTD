package dictionary.graphic.application;

import dictionary.graphic.db_dictionary.MySQL;
import dictionary.graphic.my_dictionary.Manipulation;
import dictionary.graphic.my_dictionary.Word;
import dictionary.graphic.text_to_speech.Audio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SubController {

    Connection conn = (new MySQL()).connectToDB();
    Manipulation mani = new Manipulation();

    public SubController() throws SQLException {}

    // add List window
    @FXML
    public Button addNewListButton;
    @FXML
    public TextField addNewListTextField;
    @FXML
    public Label addNewListMessageLabel;

    public void addNewList() {
        String listName = addNewListTextField.getText();
        String messsage = mani.createMyDictionary(listName);
        addNewListMessageLabel.setText(messsage);
    }

    // delete List
    @FXML
    public TextField deleteListNameTextField;
    @FXML
    public Button deleteListButton;
    @FXML
    public Label deleteListMessageLabel;

    public void deleteList() {
        String listName = deleteListNameTextField.getText();
        try {
            String message = mani.deleteMyDictionary(listName);
            deleteListMessageLabel.setText(message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // add word
    @FXML
    public TextField wordTextField, wordTypeTextField;
    @FXML
    public TextArea definitionTextField;
    @FXML
    public TextField addToListTextField;
    @FXML
    public Label addWordMessageLabel;
    @FXML
    public Button addWordButton;

    public void addWord() {
        Word word = new Word(wordTextField.getText(), wordTypeTextField.getText(), definitionTextField.getText());
        String listName = addToListTextField.getText();
        String message = mani.addWord(word, listName);
        addWordMessageLabel.setText(message);
    }

    // delete word
    @FXML
    public Label deleteWordLabel, deleteWordMessageLabel;
    @FXML
    public Button yesDeleteButton, noDeleteButton;


    public void closeDeleteWindow(ActionEvent event) {
        Stage stage = (Stage) noDeleteButton.getScene().getWindow();
        stage.close();
    }

    public void setDeleteWordLabel(String text) {
        deleteWordLabel.setText(text);
    }
    public void deleteWord() {
        // get data
        String str = deleteWordLabel.getText();
        str = str.replace("Are you sure to delete the word ", "");
        str = str.replace(" from", "");
        str = str.replace(" ?", "");

        String[] data = str.split("\\s");
        String list = data[data.length - 1];
        String word_target = str.replace(list, "");
        list = list.replace("[", ""); list = list.replace("]", "");

        // System.out.println(word_target);
        // delete procedure
        String message = mani.deleteWord(word_target, list);
        deleteWordMessageLabel.setText(message);
    }

    // update word
    @FXML
    public TextField updateWordTextField, updateWordTypeTextField;
    @FXML
    public TextArea updateWordTextArea;
    @FXML
    public Button updateWordButton;
    @FXML
    public TextField updateWordDictTextField;
    @FXML
    public Label updateWordMessageLabel;

    public void setUpdate(String word, String wordtype, String definition, String dict) {
        updateWordTextField.setText(word);
        updateWordTypeTextField.setText(wordtype);
        updateWordTextArea.setText(definition);
        updateWordDictTextField.setText(dict);
    }

    public void updateWord() {
        String word_target = updateWordTextField.getText();
        String wordtype = updateWordTypeTextField.getText();
        String definition = updateWordTextArea.getText();
        String dict = updateWordDictTextField.getText();
        Word word = new Word(word_target, wordtype, definition);
        String message = mani.updateWord(word_target, dict, word);
        updateWordMessageLabel.setText(message);
    }

    // search...
    @FXML
    public Button dictSearchButton;
    @FXML
    public Button dictSpeakButton;
    @FXML
    public TextField dictSearchField;
    @FXML
    public TextArea dictDefinitionField;
    @FXML
    public ListView listView;
    @FXML
    public Label myListName;

    public void setListName(String name) {
        myListName.setText("LIST: " + name);
    }
    public void dictGetWordDefinition() throws SQLException {
        String list = myListName.getText().replace("LIST: ", "");
        String word = dictSearchField.getText();
        String def = (new Manipulation()).lookUpWord(word, list);
        dictDefinitionField.setText(def);
    }

    public void dictGetWordDefinitionMouseClickedEvent() throws SQLException {
        String list = myListName.getText().replace("LIST: ", "");
        String word = listView.getSelectionModel().getSelectedItems().toString();
        word = word.replace("[", "");
        word = word.replace("]", "");
        String def = (new Manipulation()).lookUpWord(word, list);
        dictDefinitionField.setText(def);
    }

    public void dictGetSpeech() {
        String word = dictSearchField.getText();
        (new Audio()).speak(word);
    }

    public void getSuggestionWord() throws SQLException {
        String list = myListName.getText().replace("LIST: ", "");
        ArrayList<Word> words = new ArrayList<Word>();
        String sql = "SELECT * FROM " + list + " WHERE word LIKE \'" + dictSearchField.getText() + "%\'";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String word = rs.getString("word");
            String wordtype = rs.getString("wordtype");
            String def = rs.getString("definition");
            words.add((new Word(word, wordtype, def)));

        }
        listView.getItems().clear();
        for (Word word : words) {
            listView.getItems().add(word.getWord());
        }
    }
}
