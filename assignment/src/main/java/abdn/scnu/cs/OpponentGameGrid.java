package abdn.scnu.cs;

public class OpponentGameGrid extends GameGrid {

    public OpponentGameGrid(int width, int high, int numberOfShips) {
        super(width, high, numberOfShips);
    }

    public void printGrid() {
        for (int i = 0; i < this.gameGrid.length; i++) {
            for (int j = 0; j < this.gameGrid[i].length; j++) {
                System.out.print(this.gameGrid[i][j] == "*" ? "." : this.gameGrid[i][j]);
            }
            System.out.println();
        }
    }
}
