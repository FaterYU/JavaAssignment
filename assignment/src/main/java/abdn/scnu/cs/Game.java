package abdn.scnu.cs;

import java.util.Random;

public class Game implements GameControls {
    PlayerGameGrid playerGameGrid;
    OpponentGameGrid opponentGameGrid;

    public Game(int width, int high, int numberOfShips) {
        this.playerGameGrid = new PlayerGameGrid(width, high, numberOfShips);
        this.opponentGameGrid = new OpponentGameGrid(width, high, numberOfShips);

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

    private void opponentRound() {
        System.out.println("Oppenent is attacking");
        Random rand = new Random();
        int row = rand.nextInt(this.playerGameGrid.gameGrid.length);
        int colum = rand.nextInt(this.playerGameGrid.gameGrid[0].length);
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

    // public static void main(String[] args) {
    // Game game = new Game(4, 4, 1);

    // find out where opponent's ship is
    // int coordinates[][] = ((OpponentGameGrid)
    // game.getOpponentssGrid()).ships[0].shipCoordinates;
    // game.playRound(coordinates[0][0] + "," + coordinates[0][1]);
    // game.playRound("0,0");
    // game.playRound("0,1");
    // game.playRound("1,0");
    // }
}
