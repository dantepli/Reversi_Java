
public class Main {

    public static void main(String[] args) {
        Display display = new ConsoleDisplay();
        Board board = new Board();
        Logic logic = new StdLogic();
        Player p1 = new HumanPlayer(display, Globals.kBlacks);
        Player p2 = new HumanPlayer(display, Globals.kWhites);
        Game g = new Game(display, board, logic, p1, p2);
        g.play();
    }

}
