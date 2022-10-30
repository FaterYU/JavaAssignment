package abdn.scnu.cs;

public class OpponentGameGrid extends GameGrid {

    public OpponentGameGrid(int row, int colum, int numberOfShips) {
        super(row, colum, numberOfShips);
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
