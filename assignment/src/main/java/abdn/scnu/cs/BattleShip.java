package abdn.scnu.cs;

import java.util.Random;

public class BattleShip extends AbstractBattleShip {
    private int[][] shipCoordinatesRecord = new int[3][2];

    public BattleShip(String name) {
        this.name = name;
        this.setHits(0);
        Random rand = new Random();
        this.shipOrientation = rand.nextBoolean() ? "vertical" : "horizontal";
    }

    public boolean checkAttack(int row, int colum) {
        if (this.getHits() == 3) {
            return false;
        }
        for (int i = 0; i < this.shipCoordinates.length; i++) {
            if (this.shipCoordinates[i][0] == row && this.shipCoordinates[i][1] == colum
                    && this.shipCoordinatesRecord[i][0] != row && this.shipCoordinatesRecord[i][1] != colum
                    && this.hits < 3) {
                this.shipCoordinatesRecord[i][0] = row;
                this.shipCoordinatesRecord[i][1] = colum;
                this.hits++;
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public int getHits() {
        return this.hits;
    }

    public String getShipOrientation() {
        return this.shipOrientation;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int[][] getShipCoordinates() {
        return this.shipCoordinates;
    }

    public void setShipCoordinates(int[][] shipCoordinates) {
        this.shipCoordinates = shipCoordinates;
    }
}
