package reversiapp;

import java.util.List;

public interface Logic {
    /**
     * @param player - a player to find the possible moves for.
     * @param board  - a board.
     * @return - a List of possible moves.
     */
    public List<Cell> getPossibleMoves(Player player, Board board);

    /**
     * @param player - a player to flip the pieces for.
     * @param cell   - a cell to flip the cells around.
     * @param board  - a board.
     */
    public void flip(Player player, Cell cell, Board board);
}
