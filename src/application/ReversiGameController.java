package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import reversiapp.*;
import settings_io.SettingsReader;
import settings_io.StartingPlayer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReversiGameController implements Initializable {
    private Logic logic;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GuiReversiBoard reversiBoard;
    private ScoreTracker scoreTracker;
    private List<Cell> possibleMoves;
    private boolean p1HasTurn = true;
    private boolean p2HasTurn = true;

    @FXML
    private HBox root;
    @FXML
    private RadioButton sMoves;
    @FXML
    private Label playerTurn;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;
    @FXML
    private Button back;
    @FXML
    private Button restart;
    @FXML
    private Circle playerColor;

    /**
     * C'tor.
     * initializes the necessary members for the reversi game.
     */
    public ReversiGameController() {
        GameSettings gameSettings = SettingsReader.readFile();
        this.player1 = new HumanPlayer(Globals.kBlacks, gameSettings.getPlayer1Color());
        this.player2 = new HumanPlayer(Globals.kWhites, gameSettings.getPlayer2Color());
        if (gameSettings.getStartingPlayer() == StartingPlayer.BLACK) {
            currentPlayer = this.player1;
        } else {
            currentPlayer = this.player2;
        }
        this.board = new Board(gameSettings.getBoardSize());
        this.scoreTracker = new ScoreTracker();
        this.logic = new StdLogic(this.scoreTracker);
        this.reversiBoard = new GuiReversiBoard(board);
    }

    @Override
    /**
     * initializes the listeners and fields in the Controller.
     */
    public void initialize(
            URL location, ResourceBundle
            resources) {
        this.possibleMoves = logic.getPossibleMoves(currentPlayer, this.board);
        // init text fields on the side.
        playerColor.setFill(currentPlayer.getColor());
        playerTurn.setText("Current Player:");
        player1Score.setText("First Player Score: " + scoreTracker.getPlayer1Score());
        player2Score.setText("Second Player Score: " + scoreTracker.getPlayer2Score());
        reversiBoard.setPrefWidth(Main.kWidth / 2);
        reversiBoard.setPrefHeight(Main.kHeight / 2);
        root.getChildren().add(0, reversiBoard);
        // adds resize listeners.
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double boardNewWidth = newValue.doubleValue() - 220;
            reversiBoard.setPrefWidth(boardNewWidth);
            reversiBoard.draw(this.possibleMoves);
        });
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.doubleValue());
            reversiBoard.setPrefHeight(newValue.doubleValue());
            reversiBoard.draw(this.possibleMoves);
        });
        // adds mouse click event.
        reversiBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(event.getX() + " " + event.getY() + " " + "EVENT CORD");
            Cell picked = reversiBoard.clicked(event);
            event.consume();
            if (this.playTurn(currentPlayer, picked)) {
                // turn was valid.
                updateNextMove();
            }
        });
    }

    /**
     * plays a turn.
     *
     * @param player - current player.
     * @param picked - a picked cell.
     * @return - true if the move was successful (player had no moves,
     * or a cell was flipped).
     */
    private boolean playTurn(Player player, Cell picked) {
        if (this.possibleMoves.size() == 0) {
            return true;
        }
        if (cellValidity(picked, this.possibleMoves)) {
            // cell is valid to flip.
            Cell changedCell = board.getCell(picked.getRow(), picked.getCol());
            changedCell.setDisk(player.getDisk());
            logic.flip(player, changedCell, this.board);
            return true;
        }
        return false;

    }

    /**
     * @param cell  - a chosen cell.
     * @param moves - a list of possible moves.
     * @return - true if the chosen cell is valid.
     */
    private boolean cellValidity(Cell cell, List<Cell> moves) {
        for (Cell move : moves) {
            if (cell.equals(move)) {
                return true;
            }
        }
        return false;
    }

    /**
     * redraws the board.
     */
    private void reDraw() {
        reversiBoard.draw(this.possibleMoves);
    }

    /**
     * updates the next move, changes the player and updates the text
     * fields.
     */
    private void updateNextMove() {
        this.updatePlayer();
        this.updateTextFields();
        this.possibleMoves = logic.getPossibleMoves(currentPlayer, this.board);
        this.reDraw();
        if(!this.p1HasTurn && !this.p2HasTurn ||
                scoreTracker.getPlayer1Score() + scoreTracker.getPlayer2Score() == board.getSize() * board.getSize()) {
            // game over.
            AlertBox.display("WICTORY", "CYKA BLAT");
            System.exit(0);
        }
        if(this.possibleMoves.size() == 0) {
            AlertBox.display("No Move",
                    "You have no available moves, turn is passed to other player...");
            this.updateTurn(false);
            this.updateNextMove();
        }
        this.updateTurn(true);
    }

    /**
     * determines and updates the player's turn.
     * @param hasTurn - whether the current player has a turn.
     */
    private void updateTurn(boolean hasTurn) {
        if(currentPlayer.getDisk() == Globals.kBlacks) {
            this.p1HasTurn = hasTurn;
        } else if(currentPlayer.getDisk() == Globals.kWhites) {
            this.p2HasTurn = hasTurn;
        }
    }
    /**
     * updates the next player.
     */
    private void updatePlayer() {
        if (currentPlayer.getDisk() == this.player1.getDisk()) {
            this.currentPlayer = this.player2;
        } else if (currentPlayer.getDisk() == this.player2.getDisk()) {
            this.currentPlayer = this.player1;
        }
    }

    /**
     * updates the text fields.
     */
    private void updateTextFields() {
        playerColor.setFill(currentPlayer.getColor());
        playerTurn.setText("Current Player: ");
        player1Score.setText("First Player Score:" + scoreTracker.getPlayer1Score());
        player2Score.setText("Second Player Score:" + scoreTracker.getPlayer2Score());
    }

    @FXML
    protected void showMoves(javafx.event.ActionEvent event) {
        reversiBoard.togglePossibleMoves();
        this.reDraw();
    }
    @FXML
    protected void back(javafx.event.ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(parent, Main.kWidth, Main.kHeight);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setTitle("Reversi");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void restart(javafx.event.ActionEvent event) {
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
}