package abdn.scnu.cs;

import java.util.ArrayList;
import java.util.Random;

public class BattleShip extends AbstractBattleShip {
    private ArrayList<int[]> shipCoordinatesRecord = new ArrayList<int[]>();

    // constructor
    public BattleShip(String name) {
        this.name = name;
        this.setHits(0);
        Random rand = new Random();
        this.shipOrientation = rand.nextBoolean() ? "vertical" : "horizontal";
    }

    // check whether ship is attacked
    public boolean checkAttack(int row, int colum) {
        if (this.getHits() == 3) {
            return false;
        }
        int[] piont = new int[2];
        piont[0] = row;
        piont[1] = colum;
        for (int i = 0; i < this.shipCoordinates.length; i++) {
            if (this.shipCoordinates[i][0] == row && this.shipCoordinates[i][1] == colum) {
                for (int j = 0; j < this.shipCoordinatesRecord.size(); j++) {
                    if (this.shipCoordinatesRecord.get(j)[0] == row && this.shipCoordinatesRecord.get(j)[1] == colum) {
                        return false;
                    }
                }
                this.shipCoordinatesRecord.add(piont);
                this.hits++;
                return true;
            }
        }
        return false;
    }

    // get ship name
    public String getName() {
        return this.name;
    }

    // get the number of hits
    public int getHits() {
        return this.hits;
    }

    // get orientation of ship
    public String getShipOrientation() {
        return this.shipOrientation;
    }

    // set the number of hits
    public void setHits(int hits) {
        this.hits = hits;
    }

    // get coordinates of ship
    public int[][] getShipCoordinates() {
        return this.shipCoordinates;
    }

    // set coordinates of ship
    public void setShipCoordinates(int[][] shipCoordinates) {
        this.shipCoordinates = shipCoordinates;
    }

}
