package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Starter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/static/viewOne.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("static/css/stylesheet.css");
        stage.setTitle("SkribblApp");
        stage.setScene(scene);
        stage.show();
    }
}
