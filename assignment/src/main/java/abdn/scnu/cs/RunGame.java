package abdn.scnu.cs;

import java.util.Scanner;

public class RunGame {
    private int row, colum, numberOfShips;
    Game game;

    // constructor
    public RunGame(int row, int colum, int numberOfShips) {
        this.row = row;
        this.colum = colum;
        this.numberOfShips = numberOfShips;
    }

    // initial game
    public void initial() {
        Game game = new Game(this.row, this.colum, this.numberOfShips);
        System.out.println("Player's grid");
        game.playerGameGrid.printGrid();
        System.out.println("Opponent's grid");
        game.opponentGameGrid.printGrid();
        this.game = game;
    }

    // check input is correct
    public boolean checkInput(String ch) {
        // regex
        String pattern = "^[0-9]+,[0-9]+$";
        if (!ch.matches(pattern)) {
            throw new IOException("Incorrect input");
        } else {
            int row = Integer.parseInt(ch.split(",")[0]);
            int colum = Integer.parseInt(ch.split(",")[1]);
            if (row >= this.row || colum >= this.colum) {
                throw new IOException("Incorrect input");
            } else {
                return true;
            }
        }
    }

    // loop to make sure game continue
    public void gameLoop() {
        Scanner input = new Scanner(System.in);
        while (!this.game.checkVictory()) {
            System.out.println("Please enter the position you wish to attack\n");
            if (input.hasNextLine()) {
                String ch = input.nextLine();
                this.game.exitGame(ch);
                if (this.checkInput(ch)) {
                    this.game.playRound(ch);
                }
            }
        }
        input.close();
    }

    // main
    public static void main(String[] args) {
        RunGame runGame = new RunGame(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        runGame.initial();
        runGame.gameLoop();
    }
}
