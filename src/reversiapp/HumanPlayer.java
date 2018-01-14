package reversiapp;

import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private char color;
    
    /**
     * 
     * @param newColor - a color for the player.
     */
    public HumanPlayer(char newColor) {
        this.color = newColor;
    }
    
    @Override
    public Cell pickMove(List<Cell> moves) {
        if(moves.size() == 0) {
            return null;
        }
        int row = 0, col = 0;
        Cell cell;
        Cell invalid = new Cell(-1, -1);
        // ----------------------- REMOVE THIS
        Scanner in = new Scanner(System.in);
        do {
            try {
                row = in.nextInt();
                col = in.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid Input. Try Again.");
                cell = invalid;
                in.nextLine();
                continue;
            }
            row -= 1;
            col -= 1;
            cell = new Cell(row, col);
        } while(!cellValidity(cell, moves));
        // ------------------
        return cell;
    }

    @Override
    public char getColor() {
        return this.color;
    }
    
    /**
     * 
     * @param cell - a chosen cell.
     * @param moves - a list of possible moves.
     * @return - true if the chosen cell is valid.
     */
    private boolean cellValidity(Cell cell, List<Cell> moves) {
        for(Cell move : moves) {
            if(cell.equals(move)) {
                return true;
            }
        }
        return false;
    }
    public String playerName() {
        if(this.color == Globals.kBlacks) {
            return "First Player";
        } else {
            return "Second Player";
        }
    }
}
