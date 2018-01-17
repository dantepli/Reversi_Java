package settings_io;

import javafx.scene.paint.Color;
import reversiapp.Globals;

public class SettingsParser {
    private static boolean colorError = false;
    public static final String kBlack = "Black";
    public static final String kWhite = "White";

    /**
     * @param startingStr - a starting player value line.
     * @return - true if valid.
     */
    public static boolean isStartingPlayerValid(String startingStr) {
        return startingStr.equals(kBlack) || startingStr.equals(kWhite);
    }

    /**
     * @param colorStr - a color string.
     * @return - true if a valid color representation.
     */
    public static boolean isPlayerColorValid(String colorStr) {
        Color color;
        try {
            color = Color.web(colorStr);
        } catch (Exception e) {
            return false; // invalid color.
        }
        return true;
    }

    /**
     * @param boardSizeStr - a board size string representation.
     * @return - true if the board size is valid.
     */
    public static boolean isBoardSizeValid(String boardSizeStr) {
        int boardSize = 0;
        try {
            boardSize = Integer.parseInt(boardSizeStr);
        } catch (Exception e) {
            // not an int value.
            return false;
        }
        if (boardSize % 2 != 0 || boardSize < 3 || boardSize > 20) {
            // board size is invalid, odd or not in range.
            return false;
        }
        return true;
    }

    /**
     * @param startingStr - a starting value line.
     * @return - StartingPlayer enum representation.
     */
    public static StartingPlayer parseStartingPlayer(String startingStr) {
        if (startingStr.equals(kBlack)) {
            return StartingPlayer.BLACK;
        } else if (startingStr.equals(kWhite)) {
            return StartingPlayer.WHITE;
        }
        return null;
    }

    /**
     * @param colorStr - a color string.
     * @return - the Color object corresponding with the string, if string is invalid return black/white according
     * to calls. (black first time, white second time - default colors).
     */
    public static Color parsePlayerColor(String colorStr) {
        Color color;
        try {
            color = Color.web(colorStr);
        } catch (Exception e) {
            if (!colorError) {
                // first time error parsing
                color = Color.BLACK;
                colorError = true;
            } else {
                color = Color.WHITE;
            }

        }
        return color;
    }

    /**
     * @param boardSizeStr - a string representation of the board size.
     * @return - the corresponding int of the board size, default size is given if there is an error.
     */
    public static int parseBoardSize(String boardSizeStr) {
        int boardSize = 0;
        if (isBoardSizeValid(boardSizeStr)) {
            boardSize = Integer.parseInt(boardSizeStr);
        } else {
            // default board size.
            boardSize = Globals.kSize;
        }
        return boardSize;
    }
}
