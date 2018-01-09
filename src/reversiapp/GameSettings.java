package reversiapp;

import javafx.scene.paint.Color;
import settings_io.SettingsParser;
import settings_io.StartingPlayer;

public class GameSettings {
    private String startingPlayer;
    private String player1Color;
    private String player2Color;
    private String boardSize;

    /**
     * Initializes the settings with the default game.
     */
    public GameSettings() {
        this.startingPlayer = SettingsParser.kBlack;
        this.player1Color = SettingsParser.kBlack;
        this.player2Color = SettingsParser.kWhite;
        this.boardSize = String.valueOf(Globals.kSize);
    }

    // String getters
    public String getStartingPlayerStr() {
        return this.startingPlayer;
    }

    public String getPlayer1Str() {
        return this.player1Color;
    }

    public String getPlayer2Str() {
        return this.player2Color;
    }

    public String getBoardSizeStr() {
        return this.boardSize;
    }

    // String setters
    
    public void setStartingPlayer(String startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public void setPlayer1Color(String player1Color) {
        this.player1Color = player1Color;
    }

    public void setPlayer2Color(String player2Color) {
        this.player2Color = player2Color;
    }

    public void setBoardSize(String boardSize) {
        this.boardSize = boardSize;
    }

    // Representation Getters
    
    /**
     * 
     * @return - enum representation of the starting player.
     */
    public StartingPlayer getStartingPlayer() {
        return SettingsParser.parseStartingPlayer(startingPlayer);
    }

    /**
     * 
     * @return - color representation of player 1's color.
     */
    public Color getPlayer1Color() {
        return SettingsParser.parsePlayerColor(player1Color);
    }

    /**
     * 
     * @return - color representation of player 2's color.
     */
    public Color getPlayer2Color() {
        return SettingsParser.parsePlayerColor(player2Color);
    }

    /**
     * 
     * @return - int representation of player 2's color.
     */
    public int getBoardSize() {
        return SettingsParser.parseBoardSize(boardSize);
    }

}
