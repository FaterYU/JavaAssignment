package abdn.scnu.cs;

import java.util.Scanner;

public class RunGame {
    private int width, high, numberOfShips;
    Game game;

    public RunGame(int width, int high, int numberOfShips) {
        this.width = width;
        this.high = high;
        this.numberOfShips = numberOfShips;
    }

    public void initial() {
        Game game = new Game(this.width, this.high, this.numberOfShips);
        System.out.println("Player's grid");
        game.playerGameGrid.printGrid();
        System.out.println("Opponent's grid");
        game.opponentGameGrid.printGrid();
        this.game = game;
    }

    public void gameLoop() {
        Scanner input = new Scanner(System.in);
        while (!this.game.checkVictory()) {
            System.out.println("Please enter the position you wish to attack\n");
            if (input.hasNextLine()) {
                String ch = input.nextLine();
                this.game.exitGame(ch);
                this.game.playRound(ch);
            }
        }
        input.close();
    }

    public static void main(String[] args) {
        RunGame runGame = new RunGame(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        runGame.initial();
        runGame.gameLoop();
    }
}
