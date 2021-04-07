package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstController {

    @FXML
    VBox vBox;

    @FXML
    Button btn;

    @FXML
    TextField textField;

    @FXML
    TextArea textArea;

    private boolean valid(String newVal) {
        return newVal.matches("\\d+") && Integer.parseInt(newVal) > textArea.getText().split(",").length ||
                newVal.matches("\\d+") && Integer.parseInt(newVal) == 0;
    }

    @FXML
    public void initialize() {

        textArea.setWrapText(true);
        textField.setAlignment(Pos.CENTER);
        textField.setDisable(true);
        btn.setDisable(true);

        textArea.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasFiles()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });


        textArea.setOnDragDropped(e -> {
            boolean done = false;
            Dragboard db = e.getDragboard();
            if (db.hasFiles()) {
                List<File> files = db.getFiles();
                StringBuilder stringBuilder = new StringBuilder();
                for(File f: files){

                    try {
                        stringBuilder.append(new String(Files.readAllBytes(Paths.get(f.getPath())), StandardCharsets.UTF_8));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
                textArea.setText(stringBuilder.toString());
                done = true;
            }

            e.setDropCompleted(done);
            e.consume();
        });

        textField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                textField.setText(oldVal);
            }

            if (valid(newVal)) {
                textField.setText(oldVal);
            }

            if (textArea.getText().isEmpty() || textField.getText().isEmpty()) btn.setDisable(true);
            else btn.setDisable(false);
        });

        textArea.textProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!textArea.getText().isEmpty()) {
                textField.setDisable(false);
            } else textField.setDisable(true);

            if (textArea.getText().isEmpty() || textField.getText().isEmpty()) btn.setDisable(true);
            else btn.setDisable(false);
        });
    }

    public void drawLots(ActionEvent actionEvent) throws IOException {
        int limit = Integer.parseInt(textField.getText());
        List<String> wordsList = Stream
                .of(textArea.getText().split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        SecondController.randomWords = ThreadLocalRandom
                .current()
                .ints(0, wordsList.size())
                .distinct()
                .limit(limit)
                .boxed()
                .map(wordsList::get)
                .collect(Collectors.toList());

        Stage stage = (Stage) textArea.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/static/viewTwo.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/static/css/stylesheet.css").toExternalForm());
        stage.setScene(scene);
    }
}
