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
    private GameSettings gameSettings;
    private Logic logic;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GuiReversiBoard reversiBoard;
    private ScoreTracker scoreTracker;

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
    public ReversiGameController () {
        this.gameSettings = SettingsReader.readFile();
        this.player1 = new HumanPlayer(Globals.kBlacks);
        this.player2 = new HumanPlayer(Globals.kWhites);
        if (this.gameSettings.getStartingPlayer() == StartingPlayer.BLACK) {
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
    public void initialize(URL location, ResourceBundle
            resources) {
        playerTurn.setText("Current Player: " + currentPlayer.playerName());
        player1Score.setText("First Player Score: " + scoreTracker.getPlayer1Score());
        player2Score.setText("Second Player Score: " + scoreTracker.getPlayer2Score());
        this.reversiBoard.setPrefWidth(400);
        this.reversiBoard.setPrefHeight(400);
        root.getChildren().add(0, reversiBoard);
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            double boardNewWidth = newValue.doubleValue() - 180;
            reversiBoard.setPrefWidth(boardNewWidth);
            reversiBoard.draw();
        });
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue.doubleValue());
            reversiBoard.setPrefHeight(newValue.doubleValue());
            reversiBoard.draw();
        });
        reversiBoard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Cell picked = reversiBoard.clicked(event);
            event.consume();
            if(this.playTurn(currentPlayer, picked)) {
                updatePlayer();
            }
        });
    }

    /**
     *
     * @param player - current player.
     * @param picked - a picked cell.
     * @return
     */
    private boolean playTurn(Player player, Cell picked) {
        List<Cell> moves = logic.getPossibleMoves(player, this.board);
        if (moves.size() == 0) {
            // text warning
            return true;
        }
        for(Cell move : moves) {
            // sets the disks as flip potential.
            board.getCell(move.getRow(), move.getCol()).setDisk(Globals.kToFlip);
        }
//        this.reDraw();
        if (moves.size() == 0) {
            // text warning
            return true;
        }
        for(Cell move : moves) {
            // sets the disks back to empty
            board.getCell(move.getRow(), move.getCol()).setDisk(Globals.kEmpty);
        }
        if(cellValidity(picked, moves)) {
            Cell changedCell = board.getCell(picked.getRow(), picked.getCol());
            changedCell.setDisk(player.getColor());
            logic.flip(player, changedCell, this.board);
            this.reDraw();
            return true;
        }
        return false;

    }
    /**
     *
     * @param cell - a chosen cell.
     * @param moves - a list of possible moves.
     * @return - true if the chosen cell is valid.
     */
    private boolean cellValidity(Cell cell, List<Cell> moves) {
        for(Cell move : moves) {
            if(cell.equals(move)) {
                return true;
            }
        }
        return false;
    }

    private void reDraw() {
        reversiBoard.draw();
    }

    public void updatePlayer() {
        if(currentPlayer.getColor() == this.player1.getColor()) {
            this.currentPlayer = this.player2;
        } else if(currentPlayer.getColor() == this.player2.getColor()) {
            this.currentPlayer = this.player1;
        }
        playerTurn.setText("Current Player: " + currentPlayer.playerName());
        player1Score.setText("First Player Score:" + scoreTracker.getPlayer1Score());
        player2Score.setText("Second Player Score:" + scoreTracker.getPlayer2Score());
    }
    @FXML
    protected void showMoves(javafx.event.ActionEvent event) {
        List<Cell> moves = logic.getPossibleMoves(currentPlayer, this.board);
        if (moves.size() == 0) {
            // text warning
        }
        for(Cell move : moves) {
            // sets the disks as flip potential.
            board.getCell(move.getRow(), move.getCol()).setDisk(Globals.kToFlip);
        }
        this.reDraw();
        for(Cell move : moves) {
            // sets the disks back to empty
            board.getCell(move.getRow(), move.getCol()).setDisk(Globals.kEmpty);
        }
    }
}