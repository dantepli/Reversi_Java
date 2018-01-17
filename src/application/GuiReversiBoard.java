package application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import reversiapp.Board;
import reversiapp.Cell;
import reversiapp.GameSettings;
import reversiapp.Globals;
import settings_io.SettingsReader;

import java.io.IOException;
import java.util.List;

public class GuiReversiBoard extends GridPane {
    private Board board;
    private GameSettings gameSettings;
    private boolean showPossibleMoves;

    /**
     * C'tor.
     *
     * @param board - a game board.
     */
    public GuiReversiBoard(Board board) {
        this.board = board;
        this.gameSettings = SettingsReader.readFile();
        FXMLLoader fxmlLoader = new
                FXMLLoader(getClass().getResource("reversiBoard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.showPossibleMoves = true;
    }

    /**
     * draws the board.
     */
    public void draw(List<Cell> possibleMoves) {
        this.getChildren().clear();
        int height = (int) this.getPrefHeight();
        int width = (int) this.getPrefWidth();
        int bSize = board.getSize();
        int cellHeight = height / bSize;
        int cellWidth = width / bSize;
        int diskRadius = Math.min(cellHeight, cellWidth) / 3;
        Color p1Color = gameSettings.getPlayer1Color();
        Color p2Color = gameSettings.getPlayer2Color();
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                Rectangle rect = new Rectangle(cellWidth, cellHeight,
                        Color.LEMONCHIFFON);
                rect.setStroke(Color.BLACK);
                this.add(rect, j, i);
                if (this.board.getCell(i, j).getDisk() == Globals.kEmpty) {
                    // no disk to draw.
                    continue;
                }
                Circle disk = null;
                if (this.board.getCell(i, j).getDisk() == Globals.kBlacks) {
                    disk = new Circle(diskRadius, p1Color);
                } else {
                    disk = new Circle(diskRadius, p2Color);
                }
                disk.setStroke(Color.BLACK);
                setHalignment(disk, HPos.CENTER);
                this.add(disk, j, i);
            }
            if (this.showPossibleMoves) {
                for (Cell possibleMove : possibleMoves) {
                    Circle disk = new Circle(diskRadius, Color.TRANSPARENT);
                    disk.setStroke(Color.BLACK);
                    setHalignment(disk, HPos.CENTER);
                    this.add(disk, possibleMove.getCol(), possibleMove.getRow());
                }
            }
        }
    }

    /**
     * calculates the cell that was clicked.
     *
     * @param event - a mouse click event.
     * @return - a cell location in the board.
     */
    public Cell clicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        double prefHeight = this.getPrefHeight();
        double prefWidth = this.getPrefWidth();
        double cellHeight = (prefHeight / board.getSize());
        double cellWidth = ((prefWidth) / board.getSize());
        int i = (int) x / (int) cellWidth;
        int j = (int) y / (int) cellHeight;
        return new Cell(j, i);
    }

    /**
     * toggles whether to show the possible moves or not.
     */
    public void togglePossibleMoves() {
        this.showPossibleMoves = !this.showPossibleMoves;
    }
}
