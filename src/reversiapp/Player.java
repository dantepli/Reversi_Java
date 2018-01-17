package reversiapp;

import javafx.scene.paint.Color;

public interface Player {
    /**
     * @return - disk type.
     */
    char getDisk();

    /**
     * @return - string representation of the player.
     */
    String playerName();

    /**
     * @return - color of the player disk.
     */
    Color getColor();
}
