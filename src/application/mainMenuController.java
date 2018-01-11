package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import reversiapp.Board;
import reversiapp.Game;

public class mainMenuController {
    @FXML
    protected void exit(javafx.event.ActionEvent event) {

    }
    @FXML
    protected void play(javafx.event.ActionEvent event){
        Board board = new Board();
    }
    @FXML
    protected void settings(javafx.event.ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("settings.fxml"));
            Scene scene = new Scene(parent,430,350);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
