package reversiapp;

import javafx.scene.paint.Color;

public class HumanPlayer implements Player {
    private char disk;
    private Color color;

    /**
     * @param newDisk - a color for the player.
     */
    public HumanPlayer(char newDisk, Color newColor) {
        this.disk = newDisk;
        this.color = newColor;
    }


    @Override
    public char getDisk() {
        return this.disk;
    }

    @Override
    public String playerName() {
        if (this.disk == Globals.kBlacks) {
            return "Player 1";
        } else {
            return "Player 2";
        }
    }

    @Override
    public Color getColor() {
        return this.color;
    }
}
