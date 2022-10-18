package abdn.scnu.cs;

import abdn.scnu.cs.BattleShip;
import java.util.Random;

public class GameGrid extends AbstractGameGrid {

    public GameGrid(int width, int high, int numberOfShips) {
        this.gameGrid = new String[width][high];
        this.ships = new BattleShip[numberOfShips];
        this.initializeGrid();
        this.generateShips(numberOfShips);
    }

    public void initializeGrid() {
        for (int i = 0; i < this.gameGrid.length; i++) {
            for (int j = 0; j < this.gameGrid[i].length; j++) {
                this.gameGrid[i][j] = ".";
            }
        }
    }

    public void generateShips(int numberOfShips) {
        for (int i = 0; i < numberOfShips; i++) {
            BattleShip ship = new BattleShip("Ship " + (i + 1));
            this.ships[i] = ship;
            this.placeShip(ship);
        }
    }

    public void placeShip(BattleShip ship) {
        int x_size = ship.getShipOrientation() == "vertical" ? this.gameGrid.length : this.gameGrid.length - 2;
        int y_size = ship.getShipOrientation() == "vertical" ? this.gameGrid[0].length - 2 : this.gameGrid[0].length;
        Random rand = new Random();
        int x = rand.nextInt(x_size);
        int y = rand.nextInt(y_size);
        int[][] shipCoordinates = new int[3][2];
        int time = 0;
        for (int i = x; i <= x + 2 * (ship.getShipOrientation() == "vertical" ? 0 : 1); i++) {
            for (int j = y; j <= y + 2 * (ship.getShipOrientation() == "vertical" ? 1 : 0); j++) {
                this.gameGrid[i][j] = "*";
                shipCoordinates[time][0] = i;
                shipCoordinates[time][1] = j;
                time++;
            }
        }
        ship.setShipCoordinates(shipCoordinates);
    }

    public static void main(String[] args) {
        GameGrid gameGrid = new GameGrid(10, 10, 4);
        System.out.println(gameGrid.ships);
    }

}
