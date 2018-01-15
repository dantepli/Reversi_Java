package reversiapp;

import javafx.scene.paint.Color;

import java.util.List;

public interface Player {
    /**
     * @param moves - a list of possible moves.
     * @return - a picked cell to place the disk.
     */
    public Cell pickMove(List<Cell> moves);

    /**
     * @return - the color of the player.
     */
    public char getDisk();

    /**
     * @return
     */
    public String playerName();
    public Color getColor();
}
