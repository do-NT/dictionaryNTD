package dictionary.graphic.application;

import dictionary.graphic.db_dictionary.MySQL;
import dictionary.graphic.my_dictionary.Manipulation;
import dictionary.graphic.my_dictionary.Word;
import dictionary.graphic.sentence_translator.GoogleAPI;
import dictionary.graphic.text_to_speech.Audio;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MyController implements Initializable {

    private Connection conn = (new MySQL()).connectToDB();

    // Dictionary Tab
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

    // My List Tab
    @FXML
    public ListView myListView;
    @FXML
    public TextField myListName;
    @FXML
    public TableColumn wordColumn, typeColumn, defColumn;
    @FXML
    public TableView myTableView = new TableView();
    @FXML
    public Button addNewList;
    @FXML
    public Button refreshButton;
    @FXML
    public Button myAddWord;
    @FXML
    public Button myUpdateWord;
    @FXML
    public Button mySearchButton;

    // Sentence Tranlator Tab
    @FXML
    public TextArea englishText;
    @FXML
    public TextArea vietnameseText;
    @FXML
    public Button eTrans;
    @FXML
    public Button vTrans;
    @FXML
    public Button eSpeak;
    @FXML
    public Button vSpeak;

    public MyController() throws SQLException {
    }


    // Dictionary tab methods
    public void dictGetWordDefinition() throws SQLException {
        String word = dictSearchField.getText();
        String def = (new Manipulation()).lookUpWord(word, "entries");
        dictDefinitionField.setText(def);
    }

    public void dictGetWordDefinitionMouseClickedEvent() throws SQLException {
        String word = listView.getSelectionModel().getSelectedItems().toString();
        word = word.replace("[", "");
        word = word.replace("]", "");
        String def = (new Manipulation()).lookUpWord(word, "entries");
        dictDefinitionField.setText(def);
    }

    public void dictGetSpeech() {
        String word = dictSearchField.getText();
        (new Audio()).speak(word);
    }

    public void getSuggestionWord() throws SQLException {
        ArrayList<Word> words = new ArrayList<Word>();
        String sql = "SELECT * FROM entries WHERE word LIKE \'" + dictSearchField.getText() + "%\'";
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

    // My List tab method
    public void getMyList() throws SQLException {
        String sql = "SHOW TABLES";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        myListView.getItems().clear();
        while (rs.next()) {
            String list = rs.getString("Tables_in_dictionary");
            if (!list.equals("entries") && !list.equals("tbl_edict")) {
                myListView.getItems().add(list);
            }
        }
    }

    public void showAlist() throws SQLException {
        String dict = myListView.getSelectionModel().getSelectedItems().toString();
        dict = dict.replace("[", "");
        dict = dict.replace("]", "");
        myListName.setText(dict);

        myTableView.getItems().clear();
        String sql = "SELECT * FROM " + dict + ";";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String word = rs.getString("word");
            String wordtype = rs.getString("wordtype");
            String def = rs.getString("definition");
            Word row = new Word(word, wordtype, def);
            myTableView.getItems().add(row);
        }
    }

    @FXML
    public void openAddNewListWindow(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/addListGUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Add list");
        primaryStage.setScene(new Scene(root, 400, 160));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

    @FXML
    public void openDeleteListWindow(ActionEvent event) throws SQLException {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/deleteListGUI.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Delete list");
        primaryStage.setScene(new Scene(root, 400, 160));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.show();
    }

    @FXML
    public void openAddWordWindow(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/addWordGUI.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Add word");
            primaryStage.setScene(new Scene(root, 580, 400));
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openDeleteWordWindow(ActionEvent event){
        try {
            ObservableList<Word> wordList;
            wordList = myTableView.getSelectionModel().getSelectedItems();
            String dict = myListView.getSelectionModel().getSelectedItems().toString();
            String word = wordList.get(0).getWord();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/deleteWordGUI.fxml"));
            Parent root = loader.load();

            //The following both lines are the only addition we need to pass the arguments
            SubController sub = loader.getController();
            sub.setDeleteWordLabel("Are you sure to delete the word " + word + " from " + dict + " ?");

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 487, 246.6));
            stage.setTitle("Delete word");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUpdateWordWindow(ActionEvent event){
        try {
            // get data
            ObservableList<Word> wordList;
            wordList = myTableView.getSelectionModel().getSelectedItems();
            String word = wordList.get(0).getWord(), wordtype = wordList.get(0).getWordtype(),
                    definition = wordList.get(0).getDefinition();

            String dict = myListView.getSelectionModel().getSelectedItems().toString();
            dict = dict.replace("[", ""); dict = dict.replace("]", "");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/updateWordGUI.fxml"));
            Parent root = loader.load();

            //The following both lines are the only addition we need to pass the arguments
            SubController sub = loader.getController();
            sub.setUpdate(word, wordtype, definition, dict);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update word");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMySearchWindow(ActionEvent event){
        try {
            // get data
            String dict = myListView.getSelectionModel().getSelectedItems().toString();
            dict = dict.replace("[", ""); dict = dict.replace("]", "");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mySearchGUI.fxml"));
            Parent root = loader.load();

            //The following both lines are the only addition we need to pass the arguments
            SubController sub = loader.getController();
            sub.setListName(dict);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Search");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Translate text tab methods
    public void translateEnglishText() throws Exception {
        String text = englishText.getText();
        String translatedText =(new GoogleAPI()).textMeaning(text, "en", "vi");
        vietnameseText.setText(translatedText);
    }

    public void translateGetEnglishSpeech() {
        String word = englishText.getText();
        (new Audio()).speak(word);
    }

    public void translateVietnameseText() throws Exception {
        String text = vietnameseText.getText();
        String translatedText =new GoogleAPI().textMeaning(text, "vi", "en");
        englishText.setText(translatedText);
        // System.out.println(text);
    }

    public void translateGetVietnameseSpeech() {
        String word = vietnameseText.getText();
        (new Audio()).speak(word);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getMyList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("wordtype"));

        defColumn.setCellValueFactory(new PropertyValueFactory<>("definition"));

    }
}
