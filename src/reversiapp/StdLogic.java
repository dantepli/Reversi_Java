package reversiapp;

import java.util.ArrayList;
import java.util.List;

public class StdLogic implements Logic {
    private final int dx[] = {1, 1, 0, -1, -1, -1, 0, 1};
    private final int dy[] = {0, 1, 1, 1, 0, -1, -1, -1};
    private final int kNumOfDirs = 8;
    private ScoreTracker tracker;

    public StdLogic(ScoreTracker newTracker) {
        this.tracker = newTracker;
    }

    @Override
    public List<Cell> getPossibleMoves(Player player, Board board) {
        List<Cell> moves = new ArrayList<Cell>();
        int size = board.getSize();
        char opponentDisk = opponentCell(player.getDisk());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell current = board.getCell(i, j);
                if (current.getDisk() == Globals.kEmpty) {
                    // a possible cell to place a disk
                    if (isPossibleMove(opponentDisk, current, board)) {
                        moves.add(current);
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public void flip(Player player, Cell cell, Board board) {
        char opponentDisk = opponentCell(player.getDisk());
        List<Cell> flips = new ArrayList<Cell>();
        List<Cell> temp = new ArrayList<Cell>();
        int row = 0, col = 0;
        for (int i = 0; i < this.kNumOfDirs; i++) {
            // iterate through the possible directions in the 2D plain.
            row = cell.getRow();
            col = cell.getCol();
            row += this.dx[i];
            col += this.dy[i];
            if (outOfBounds(row, col, board)) {
                // illegal direction.
                continue;
            }
            Cell currentCheck = board.getCell(row, col);
            char disk = currentCheck.getDisk();
            if (disk == opponentDisk) {
                // iterate through the opponents disks
                while (disk == opponentDisk) {
                    temp.add(currentCheck);
                    row += this.dx[i];
                    col += this.dy[i];
                    if (outOfBounds(row, col, board)) {
                        // not between disks.
                        break;
                    }
                    currentCheck = board.getCell(row, col);
                    disk = currentCheck.getDisk();
                }
                if (outOfBounds(row, col, board)) {
                    // didn't reach a valid place.
                    temp.clear();
                    continue;
                }
                if (board.getCell(row, col)
                        .getDisk() == opponentCell(opponentDisk)) {
                    // reached the original player cell from the opponent, means
                    // it's a valid move.
                    flips.addAll(temp);
                    temp.clear();
                }
                temp.clear();
            }
        }
        for (int i = 0; i < flips.size(); i++) {
            flips.get(i).setDisk(player.getDisk());
        }
        tracker.updateScore(player.getDisk(), flips.size());
    }

    /**
     * @param opponentDisk - opponent's disk.
     * @param cell         - a given cell to check for.
     * @param board        - a board.
     * @return - true if possible to place player's disk.
     */
    private boolean isPossibleMove(char opponentDisk, Cell cell, Board board) {
        int row = 0, col = 0;
        for (int i = 0; i < kNumOfDirs; i++) {
            // iterate through the possible directions in the 2D plain.
            row = cell.getRow();
            col = cell.getCol();
            row += dx[i];
            col += dy[i];
            if (outOfBounds(row, col, board)) {
                // illegal direction.
                continue;
            }
            Cell currentCheck = board.getCell(row, col);
            char disk = currentCheck.getDisk();
            if (disk == opponentDisk) {
                // we are not out of bounds, and there is an
                // opponent disk on the cell, continue checking.
                int rowCol[] = {row, col};
                if (!iterateOpponentDisks(dx[i], dy[i], rowCol, opponentDisk,
                        board)) {
                    row = rowCol[0]; // retrieve updated row val
                    col = rowCol[1]; // retrieve updated col val
                    continue;
                }
                row = rowCol[0]; // retrieve updated row val
                col = rowCol[1]; // retrieve updated col val
                if (board.getCell(row, col)
                        .getDisk() == opponentCell(opponentDisk)) {
                    // reached the original player cell from the opponent, means
                    // it's a valid move.
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param row   - row value.
     * @param col   - column value.
     * @param board - a board.
     * @return - true if out of the bounds of the board.
     */
    private boolean outOfBounds(int row, int col, Board board) {
        return row < 0 || row > (board.getSize() - 1) || col < 0
                || col > (board.getSize() - 1);
    }

    /**
     * @param dx           - x direction.
     * @param dy           - y direction.
     * @param rowCol       - row and column values.
     * @param opponentDisk - opponent's disk.
     * @param board        - a board.
     * @return - true if we iterated through the opponents disks without
     * reaching the end of the board.
     */
    private boolean iterateOpponentDisks(
            int dx, int dy, int rowCol[],
            char opponentDisk, Board board) {
        Cell currentCheck = board.getCell(rowCol[0], rowCol[1]);
        char disk = currentCheck.getDisk();
        while (disk == opponentDisk) {
            // iterate through the opponents disks.
            rowCol[0] += dx; // row
            rowCol[1] += dy; // col
            if (outOfBounds(rowCol[0], rowCol[1], board)) {
                // not between disks.
                return false;
            }
            currentCheck = board.getCell(rowCol[0], rowCol[1]);
            disk = currentCheck.getDisk();
        }
        return true;
    }

    /**
     * @param playerCell - a current player cell disk.
     * @return - the opponent's player cell disk.
     */
    private char opponentCell(char playerCell) {
        if (playerCell == Globals.kWhites) {
            return Globals.kBlacks;
        }
        return Globals.kWhites;
    }
}
