package abdn.scnu.cs;

import java.util.Random;

public class Game implements GameControls {
    PlayerGameGrid playerGameGrid;
    OpponentGameGrid opponentGameGrid;

    public Game(int row, int colum, int numberOfShips) {
        this.playerGameGrid = new PlayerGameGrid(row, colum, numberOfShips);
        this.opponentGameGrid = new OpponentGameGrid(row, colum, numberOfShips);
    }

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

    private int[] decision() {
        int row = this.getPlayersGrid().gameGrid.length;
        int colum = this.getPlayersGrid().gameGrid[0].length;
        double[][] probability = new double[row][colum];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                int u = i > 1 ? i - 2 : 0;
                int d = i < row - 2 ? i + 2 : row - 1;
                int l = j > 1 ? j - 2 : 0;
                int r = j < colum - 2 ? j + 2 : colum - 1;
                int cot = 0;
                for (; u <= d; u++) {
                    if (this.getPlayersGrid().gameGrid[u][j] != "X" && this.getPlayersGrid().gameGrid[u][j] != "%"
                            && u != i) {
                        cot++;
                    }
                }
                for (; l <= r; l++) {
                    if (this.getPlayersGrid().gameGrid[i][d] != "X" && this.getPlayersGrid().gameGrid[i][d] != "%"
                            && l != j) {
                        cot++;
                    }
                }
                probability[i][j] = cot / 8;
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                int u = i > 1 ? i - 2 : 0;
                int d = i < row - 2 ? i + 2 : row - 1;
                int l = j > 1 ? j - 2 : 0;
                int r = j < colum - 2 ? j + 2 : colum - 1;
                if (this.getPlayersGrid().gameGrid[i][j] == "X") {
                    probability[i][j] = Integer.MIN_VALUE;
                    for (int k = u; k <= d; k++) {
                        probability[k][j] += (1 / 3) * Math.abs(k - i);
                    }
                    for (int k = l; k <= r; k++) {
                        probability[i][k] += (1 / 3) * Math.abs(k - j);
                    }
                }
                if (this.getPlayersGrid().gameGrid[i][j] == "%") {
                    probability[i][j] = Integer.MIN_VALUE;
                    for (int k = u; k <= d; k++) {
                        probability[k][j] -= (1 / 3) * Math.abs(k - i);
                    }
                    for (int k = l; k <= r; k++) {
                        probability[i][k] -= (1 / 3) * Math.abs(k - j);
                    }
                }
            }
        }
        int[] result = new int[] { 0, 0 };
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                if (probability[i][j] > probability[result[0]][result[1]]) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }

    private void opponentRound() {
        System.out.println("Oppenent is attacking");
        Random rand = new Random();
        int row = rand.nextInt(this.playerGameGrid.gameGrid.length);
        int colum = rand.nextInt(this.playerGameGrid.gameGrid[0].length);
        // int[] decision = this.decision();
        // int row = decision[0];
        // int colum = decision[1];
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

    public boolean checkVictory() {
        int opponentcount = 0;
        int playercount = 0;
        for (int i = 0; i < this.opponentGameGrid.ships.length; i++) {
            opponentcount += this.opponentGameGrid.ships[i].hits == 3 ? 1 : 0;
        }
        for (int i = 0; i < this.playerGameGrid.ships.length; i++) {
            playercount += this.playerGameGrid.ships[i].hits == 3 ? 1 : 0;
        }
        if (opponentcount == this.opponentGameGrid.ships.length && playercount < this.playerGameGrid.ships.length) {
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

    public void exitGame(String input) {
        if (input.contains("exit")) {
            System.out.println("Exiting game-thank you for playing");
            System.exit(0);
        }
    }

    public GameGrid getPlayersGrid() {
        return this.playerGameGrid;
    }

    public GameGrid getOpponentssGrid() {
        return this.opponentGameGrid;
    }
}
