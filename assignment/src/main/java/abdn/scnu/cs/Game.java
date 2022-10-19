package abdn.scnu.cs;

import java.util.Random;
import java.util.Scanner;

public class Game implements GameControls {
    PlayerGameGrid playerGameGrid;
    OpponentGameGrid opponentGameGrid;

    public Game(int width, int high, int numberOfShips) {
        this.playerGameGrid = new PlayerGameGrid(width, high, numberOfShips);
        this.opponentGameGrid = new OpponentGameGrid(width, high, numberOfShips);

    }

    public void playRound(String input) {
        String coordinates[] = input.split(",");
        int row = Integer.parseInt(coordinates[0]);
        int colum = Integer.parseInt(coordinates[1]);
        for (int i = 0; i < this.opponentGameGrid.ships.length; i++) {
            if (this.opponentGameGrid.ships[i].checkAttack(row, colum)) {
                this.opponentGameGrid.gameGrid[row][colum] = "X";
                System.out.println("HIT " + this.opponentGameGrid.ships[i].name + "!!!");
            } else {
                this.opponentGameGrid.gameGrid[row][colum] = "%";
                System.out.println("MISS!!!");
            }
        }
        this.opponentGameGrid.printGrid();
        this.opponentRound();
    }

    private void opponentRound() {
        Random rand = new Random();
        int row = rand.nextInt(this.playerGameGrid.gameGrid.length);
        int colum = rand.nextInt(this.playerGameGrid.gameGrid[0].length);
        for (int i = 0; i < this.playerGameGrid.ships.length; i++) {
            this.playerGameGrid.gameGrid[row][colum] = this.playerGameGrid.ships[i].checkAttack(row, colum) ? "X" : "%";
        }
    }

    public boolean checkVictory() {
        int hitscount = 0;
        for (int i = 0; i < this.opponentGameGrid.ships.length; i++) {
            hitscount += this.opponentGameGrid.ships[i].hits == 3 ? 1 : 0;
        }
        if (hitscount == this.opponentGameGrid.ships.length) {
            System.out.println("You have won!");
            return true;
        } else {
            System.out.println("You have lost!");
            return false;
        }
    }

    public void exitGame(String input) {
        System.out.println("Exiting game-thank you for playing");
        System.exit(0);
    }

    public AbstractGameGrid getPlayersGrid() {
        return this.playerGameGrid;
    }

    public AbstractGameGrid getOpponentssGrid() {
        return this.opponentGameGrid;
    }

    public static void main(String[] args) {
        Game game = new Game(4, 4, 1);

        // find out where opponent's ship is
        int coordinates[][] = ((OpponentGameGrid) game.getOpponentssGrid()).ships[0].shipCoordinates;

        game.playRound(coordinates[0][0] + "," + coordinates[0][1]);
    }
}
