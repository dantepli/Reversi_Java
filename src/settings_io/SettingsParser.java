package settings_io;

import javafx.scene.paint.Color;
import reversiapp.Globals;

public class SettingsParser {
    public static final String kBlack = "Black";
    public static final String kWhite = "White";

    /**
     * 
     * @param startingStr
     *            - a starting player value line.
     * @return - true if valid.
     */
    public static boolean isStartingPlayerValid(String startingStr) {
        if (startingStr.equals(kBlack) || startingStr.equals(kWhite)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param colorStr
     *            - a color string.
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
     * 
     * @param starting
     *            - a starting value line.
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

    public static Color parsePlayerColor(String colorStr) {
        Color color;
        try {
            color = Color.web(colorStr);
        } catch (Exception e) {
            System.err.println("Invalid color value. " + e.getMessage());
            color = Color.BLACK;
            // TODO:::::::::::::::::::::::::::::::::::::::::::::::::::::
        }
        return color;
    }

    public static int parseBoardSize(String boardSizeStr) {
        int boardSize = 0;
        if (isBoardSizeValid(boardSizeStr)) {
            boardSize = Integer.parseInt(boardSizeStr);
        } else {
            // default board size.
            System.err
                    .println("Error parsing board size, default will be given");
            boardSize = Globals.kSize;
        }
        return boardSize;
    }
}
