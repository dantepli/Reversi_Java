package reversiapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import settings_io.SettingsReader;
import settings_io.SettingsWriter;

public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My First App");
        Label lbl = new Label("Hello World!");
        lbl.setFont(new Font("Arial", 30));
        Button btn = new Button("Click me");
        btn.setOnAction(event -> {
            lbl.setText("Button clicked!");
        });
        VBox root = new VBox();
        root.getChildren().add(lbl);
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        GameSettings s = SettingsReader.readFile();
        SettingsWriter.writeFile(s);
        launch(args);
        ScoreTracker tracker = new ScoreTracker();
        Display display = new ConsoleDisplay();
        Board board = new Board(s.getBoardSize());
        Logic logic = new StdLogic(tracker);
        Player p1 = new HumanPlayer(display, Globals.kBlacks);
        Player p2 = new HumanPlayer(display, Globals.kWhites);
        Game g = new Game(display, board, logic, p1, p2, tracker);
        g.play();
    }
}
