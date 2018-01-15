package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import reversiapp.GameSettings;
import settings_io.SettingsParser;
import settings_io.SettingsReader;
import settings_io.SettingsWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static reversiapp.Globals.kBlacks;
import static reversiapp.Globals.kWhites;

public class SettingsController {
    ObservableList<String> sizes = FXCollections.observableArrayList("4", "6", "8", "10", "12", "14", "16", "18", "20");
    @FXML
    private RadioButton firstPlayer;
    @FXML
    private RadioButton secondPlayer;
    @FXML
    private Text messageText;
    @FXML
    private ColorPicker firstPlayerColor;
    @FXML
    private ColorPicker secondPlayerColor;
    @FXML
    private ChoiceBox boardSize;

    @FXML
    private void initialize() {
        GameSettings gameSettings = SettingsReader.readFile();
        boardSize.setItems(sizes);
        boardSize.setValue(gameSettings.getBoardSizeStr());
        firstPlayerColor.setValue(gameSettings.getPlayer1Color());
        secondPlayerColor.setValue(gameSettings.getPlayer2Color());
        if (gameSettings.getStartingPlayerStr().equals(SettingsParser.kBlack)) {
            firstPlayer.setSelected(true);
        } else {
            secondPlayer.setSelected(true);
        }
    }

    @FXML
    protected void done(javafx.event.ActionEvent event) {
        GameSettings gameSettings = new GameSettings();
        gameSettings.setBoardSize(boardSize.getValue().toString());
        gameSettings.setPlayer1Color(firstPlayerColor.getValue().toString());
        gameSettings.setPlayer2Color(secondPlayerColor.getValue().toString());
        if (firstPlayer.isSelected()) {
            gameSettings.setStartingPlayer(SettingsParser.kBlack);
        } else {
            gameSettings.setStartingPlayer(SettingsParser.kWhite);
        }
        SettingsWriter.writeFile(gameSettings);
        back(event);
    }

    @FXML
    protected void back(javafx.event.ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(parent, 400, 350);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

