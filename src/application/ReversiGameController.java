package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    @FXML
    private HBox root;
    @FXML
    private Button sMoves;
    @FXML
    private Label playerTurn;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;

    /**
     * C'tor.
     * initializes the necessary members for the reversi game.
     */
    public ReversiGameController() {
        GameSettings gameSettings = SettingsReader.readFile();
        this.player1 = new HumanPlayer(Globals.kBlacks);
        this.player2 = new HumanPlayer(Globals.kWhites);
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
        playerTurn.setText("Current Player: " + currentPlayer.playerName());
        player1Score.setText("First Player Score: " + scoreTracker.getPlayer1Score());
        player2Score.setText("Second Player Score: " + scoreTracker.getPlayer2Score());
        reversiBoard.setPrefWidth(400);
        reversiBoard.setPrefHeight(400);
        root.getChildren().add(0, reversiBoard);
        // adds resize listeners.
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double boardNewWidth = newValue.doubleValue() - 180;
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
            // text warning
            return true;
        }
        if (cellValidity(picked, this.possibleMoves)) {
            Cell changedCell = board.getCell(picked.getRow(), picked.getCol());
            changedCell.setDisk(player.getColor());
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
    public void updateNextMove() {
        if (currentPlayer.getColor() == this.player1.getColor()) {
            this.currentPlayer = this.player2;
        } else if (currentPlayer.getColor() == this.player2.getColor()) {
            this.currentPlayer = this.player1;
        }
        playerTurn.setText("Current Player: " + currentPlayer.playerName());
        player1Score.setText("First Player Score:" + scoreTracker.getPlayer1Score());
        player2Score.setText("Second Player Score:" + scoreTracker.getPlayer2Score());
        this.possibleMoves = logic.getPossibleMoves(currentPlayer, this.board);
        this.reDraw();
    }

    @FXML
    protected void showMoves(javafx.event.ActionEvent event) {
        reversiBoard.togglePossibleMoves();
        this.reDraw();
    }
}