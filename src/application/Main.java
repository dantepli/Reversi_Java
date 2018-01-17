package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int kWidth = 1300;
    public static final int kHeight = 900;

    @Override
    /**
     * starts the application.
     */
    public void start(Stage primaryStage) {
        try {
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(root, kWidth, kHeight);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setMinHeight(kHeight);
            primaryStage.setMinWidth(kWidth);
            primaryStage.setTitle("Reversi");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}