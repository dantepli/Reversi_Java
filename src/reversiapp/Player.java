package reversiapp;

import javafx.scene.paint.Color;

import java.util.List;

public interface Player {
    /**
     * @param moves - a list of possible moves.
     * @return - a picked cell to place the disk.
     */
    Cell pickMove(List<Cell> moves);

    /**
     * @return - disk type.
     */
    char getDisk();

    /**
     * @return
     */
    String playerName();

    /**
     * @return - color of the player disk.
     */
    Color getColor();
}
