package reversiapp;

public class Board {
    private Cell[][] board;
    private int size;

    /**
     * C'tor. Default board size initialized with 8x8.
     */
    public Board() {
        this.size = Globals.kSize;
        this.initBoard();
    }

    /**
     * C'tor.
     *
     * @param newSize - a size for the board. board will be newSize x newSize.
     */
    public Board(int newSize) {
        size = newSize;
        this.initBoard();
    }

    /**
     * initializes the board.
     */
    private void initBoard() {
        // board table init
        this.board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = new Cell(i, j);
            }
        }
        // player disks init
        int mid = (size / 2) - 1;
        int roofMid = (size / 2);
        this.board[mid][mid].setDisk(Globals.kWhites);
        this.board[roofMid][roofMid].setDisk(Globals.kWhites);
        this.board[mid][roofMid].setDisk(Globals.kBlacks);
        this.board[roofMid][mid].setDisk(Globals.kBlacks);
    }

    /**
     * @return - size of the board.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @param row - row index.
     * @param col - column index.
     * @return - a cell in the corresponding location, null if out of bounds.
     */
    public Cell getCell(int row, int col) {
        if (row > this.size || col > this.size || row < 0 || col < 0) {
            return null;
        }
        return this.board[row][col];
    }
}
