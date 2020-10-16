package dictionary.graphic.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/NewGUI.fxml"));
        primaryStage.setTitle("A Dictionary Application");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        // System.out.println(123);
    }


    public static void main(String[] args) {
        launch(args);
    }
}