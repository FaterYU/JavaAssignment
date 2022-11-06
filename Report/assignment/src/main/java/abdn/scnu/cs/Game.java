package abdn.scnu.cs;

import java.util.Random;

public class Game implements GameControls {
    PlayerGameGrid playerGameGrid;
    OpponentGameGrid opponentGameGrid;

    // constructor
    public Game(int row, int colum, int numberOfShips) {
        this.playerGameGrid = new PlayerGameGrid(row, colum, numberOfShips);
        this.opponentGameGrid = new OpponentGameGrid(row, colum, numberOfShips);
    }

    // player round
    public void playRound(String input) {
        System.out.println("Player is attacking");
        String coordinates[] = input.split(",");
        int row = Integer.parseInt(coordinates[0]);
        int colum = Integer.parseInt(coordinates[1]);
        boolean isHit = false;
        for (int i = 0; i < this.opponentGameGrid.ships.length; i++) {
            if (this.opponentGameGrid.ships[i].checkAttack(row, colum)) {
                this.opponentGameGrid.gameGrid[row][colum] = "X";
                isHit = true;
                System.out.println("HIT " + this.opponentGameGrid.ships[i].name + "!!!");
            }
        }
        if (this.opponentGameGrid.gameGrid[row][colum] != "X") {
            this.opponentGameGrid.gameGrid[row][colum] = "%";
        }
        if (!isHit) {
            System.out.println("MISS!!!");
        }
        this.opponentRound();
        System.out.println("Player's grid");
        this.playerGameGrid.printGrid();
        System.out.println("Opponent's grid");
        this.opponentGameGrid.printGrid();
    }

    // oppenent coordinates decision
    private int[] decision() {
        int row = this.getPlayersGrid().gameGrid.length;
        int colum = this.getPlayersGrid().gameGrid[0].length;
        double[][] probability = new double[row][colum];
        // depend on "X" coordinates
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                int u = i > 1 ? i - 2 : 0;
                int d = i < row - 2 ? i + 2 : row - 1;
                int l = j > 1 ? j - 2 : 0;
                int r = j < colum - 2 ? j + 2 : colum - 1;
                if (this.getPlayersGrid().gameGrid[i][j] == "X") {
                    for (int k = u; k <= d; k++) {
                        if (k != i) {
                            probability[k][j] += (1d / 3) / Math.abs(k - i);
                        }
                    }
                    for (int k = l; k <= r; k++) {
                        if (k != j) {
                            probability[i][k] += (1d / 3) / Math.abs(k - j);
                        }
                    }
                }
            }
        }
        // convert the probability which on "X"
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                if (this.getPlayersGrid().gameGrid[i][j] == "X") {
                    for (int x = -1; x < 2; x++) {
                        if (i + x > -1 && i + x < row) {
                            probability[i + x][j] += probability[i][j];
                        }
                    }
                    for (int x = -1; x < 2; x++) {
                        if (j + x > -1 && j + x < colum) {
                            probability[i][j + x] += probability[i][j];
                        }
                    }
                    probability[i][j] = Integer.MIN_VALUE;
                }
                if (this.getPlayersGrid().gameGrid[i][j] == "%") {
                    probability[i][j] = Integer.MIN_VALUE;
                }
            }
        }
        int[] result = new int[] { 0, 0 };
        // get the result whose probability is max
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                if (probability[i][j] > probability[result[0]][result[1]]) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        if (probability[result[0]][result[1]] <= 0) {
            // depend on the number of unknown dots
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < colum; j++) {
                    int u = i > 1 ? i - 2 : 0;
                    int d = i < row - 2 ? i + 2 : row - 1;
                    int l = j > 1 ? j - 2 : 0;
                    int r = j < colum - 2 ? j + 2 : colum - 1;
                    double cot = 0;
                    for (; u <= d; u++) {
                        if (this.getPlayersGrid().gameGrid[u][j] != "X" &&
                                this.getPlayersGrid().gameGrid[u][j] != "%"
                                && u != i) {
                            cot++;
                        }
                    }
                    for (; l <= r; l++) {
                        if (this.getPlayersGrid().gameGrid[i][l] != "X" &&
                                this.getPlayersGrid().gameGrid[i][l] != "%"
                                && l != j) {
                            cot++;
                        }
                    }
                    if (probability[i][j] >= 0) {
                        probability[i][j] = cot / 8;
                    }
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < colum; j++) {
                    if (probability[i][j] > probability[result[0]][result[1]]) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }
        return result;
    }

    // opponent round
    private void opponentRound() {
        System.out.println("Oppenent is attacking");
        int[] decision = this.decision();
        int row = decision[0];
        int colum = decision[1];
        boolean isHit = false;
        for (int i = 0; i < this.playerGameGrid.ships.length; i++) {
            if (this.playerGameGrid.ships[i].checkAttack(row, colum)) {
                this.playerGameGrid.gameGrid[row][colum] = "X";
                isHit = true;
                System.out.println("HIT " + this.playerGameGrid.ships[i].name + "!!!");
            }
        }
        if (this.playerGameGrid.gameGrid[row][colum] != "X") {
            this.playerGameGrid.gameGrid[row][colum] = "%";
        }
        if (!isHit) {
            System.out.println("MISS!!!");
        }
    }

    // check game victory
    public boolean checkVictory() {
        int opponentcount = 0;
        int playercount = 0;
        for (int i = 0; i < this.opponentGameGrid.ships.length; i++) {
            opponentcount += this.opponentGameGrid.ships[i].hits == 3 ? 1 : 0;
        }
        for (int i = 0; i < this.playerGameGrid.ships.length; i++) {
            playercount += this.playerGameGrid.ships[i].hits == 3 ? 1 : 0;
        }
        // if win at the same round, return player win
        if (opponentcount == this.opponentGameGrid.ships.length && playercount <= this.playerGameGrid.ships.length) {
            System.out.println("You have won!");
            return true;
        } else if (opponentcount < this.opponentGameGrid.ships.length
                && playercount == this.playerGameGrid.ships.length) {
            System.out.println("You have lost!");
            return true;
        } else {
            return false;
        }
    }

    // exit game
    public void exitGame(String input) {
        if (input.contains("exit")) {
            System.out.println("Exiting game - thank you for playing");
            System.exit(0);
        }
    }

    // get player grid
    public GameGrid getPlayersGrid() {
        return this.playerGameGrid;
    }

    // get opponent grid
    public GameGrid getOpponentssGrid() {
        return this.opponentGameGrid;
    }
}
