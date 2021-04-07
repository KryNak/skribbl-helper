package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class SecondController {

    public static List<String> randomWords;

    @FXML
    Button btn;

    public void copyText(ActionEvent e) {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(String.join(", ", randomWords));
        Clipboard.getSystemClipboard().setContent(clipboardContent);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info mordo");
        alert.setHeaderText("Wylosowane slowa zostaly skopiowane. Nacisnij Ctrl + V zeby wkleic slowa.");
        alert.setContentText(null);

        alert.show();
    }

    public void returnToPrev(ActionEvent e) throws IOException {
        Stage stage = (Stage) btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/static/viewOne.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/static/css/stylesheet.css").toExternalForm());
        stage.setScene(scene);
    }

}
