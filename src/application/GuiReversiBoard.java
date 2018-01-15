package application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
    private int height;
    private int width;
    private GameSettings gameSettings;
    private boolean showPossibleMoves;

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
        this.showPossibleMoves = false;
    }

    /**
     * draws the board.
     */
    public void draw(List<Cell> possibleMoves) {
        this.getChildren().clear();
        this.height = (int) this.getPrefHeight();
        this.width = (int) this.getPrefWidth();
        int bSize = board.getSize();
        int cellHeight = height / bSize;
        int cellWidth = width / bSize;
        int diskRadius = Math.min(cellHeight, cellWidth) / 3;
        Color p1Color = gameSettings.getPlayer1Color();
        Color p2Color = gameSettings.getPlayer2Color();
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                Rectangle rect = new Rectangle(cellWidth, cellHeight, Color.LEMONCHIFFON);
                rect.setStroke(Color.BLACK);
                this.add(rect, j, i);
                if (this.board.getCell(i, j).getDisk() == Globals.kEmpty) {
                    // no disk to draw.
                    continue;
                }
                Circle disk = null;
                if (this.board.getCell(i, j).getDisk() == Globals.kBlacks) {
                    disk = new Circle(diskRadius, p1Color);
                } else if (this.board.getCell(i, j).getDisk() == Globals.kWhites) {
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
        int x = (int) event.getX();
        int y = (int) event.getY();
        int prefHeight = (int) this.getPrefHeight();
        int prefWidth = (int) this.getPrefWidth();
        int cellHeight = (prefHeight / board.getSize());
        int cellWidth = ((prefWidth) / board.getSize());
        int i = (int) (x / cellWidth);
        int j = (int) (y / cellHeight);
        System.out.println(i + " " + j);
        return new Cell(j, i);
    }

    /**
     * toggles whether to show the possible moves or not.
     */
    public void togglePossibleMoves() {
        if (this.showPossibleMoves) {
            this.showPossibleMoves = false;
        } else {
            this.showPossibleMoves = true;
        }
    }
}
