package reversiapp;

public class TurnManager {
    private ScoreTracker tracker;
    private boolean p1Turn;
    private boolean p2Turn;
    private int maxCells;

    /**
     * C'tor.
     *
     * @param newTracker - a score tracker.
     * @param boardSize  - a board size.
     */
    public TurnManager(ScoreTracker newTracker, int boardSize) {
        this.p1Turn = true;
        this.p2Turn = true;
        this.tracker = newTracker;
        this.maxCells = boardSize * boardSize;
    }

    /**
     * updates moves value for a given player.
     *
     * @param player   - a player.
     * @param hasMoves - whether the player has moves or not.
     */
    public void updateMoves(Player player, boolean hasMoves) {
        if (player.getDisk() == Globals.kBlacks) {
            p1Turn = hasMoves;
        } else if (player.getDisk() == Globals.kWhites) {
            p2Turn = hasMoves;
        }
    }

    /**
     * @return - true if the game is over.
     */
    public boolean gameOver() {
        return (!p1Turn && !p2Turn) ||
                (tracker.getPlayer1Score() + tracker.getPlayer2Score() == this.maxCells);
    }
}
