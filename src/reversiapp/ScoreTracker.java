package reversiapp;


public class ScoreTracker {
    private int player1Score;
    private int player2Score;

    /**
     * C'tor, initializes the scores.
     */
    public ScoreTracker() {
        player1Score = 2; // initial disks
        player2Score = 2;
    }

    /**
     * updates the score.
     *
     * @param disk         - player disk representation.
     * @param cellsFlipped - amount of cells flipped.
     */
    public void updateScore(char disk, int cellsFlipped) {
        if (disk == Globals.kBlacks) {
            player1Score += cellsFlipped + 1;
            player2Score -= cellsFlipped;
        } else if (disk == Globals.kWhites) {
            player2Score += cellsFlipped + 1;
            player1Score -= cellsFlipped;
        }
    }

    /**
     * @return - player 1's score.
     */
    public int getPlayer1Score() {
        return player1Score;
    }

    /**
     * @return - player 2's score.
     */
    public int getPlayer2Score() {
        return player2Score;
    }
}
