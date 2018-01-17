package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainMenuController {
    @FXML
    /**
     * Exits the application when the exit button is pressed.
     */
    protected void exit(javafx.event.ActionEvent event) {
        Platform.exit();
    }

    @FXML
    /**
     * Starts the game when the play button is pressed.
     */
    protected void play(javafx.event.ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("reversiGame.fxml"));
            Scene scene = new Scene(parent, Main.kWidth, Main.kHeight);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Reversi Game");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    /**
     * Opens up the settings when the settings button is pressed.
     */
    protected void settings(javafx.event.ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("settings.fxml"));
            Scene scene = new Scene(parent, Main.kWidth, Main.kHeight);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
