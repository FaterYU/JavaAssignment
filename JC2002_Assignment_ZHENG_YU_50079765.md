# Report

## Task

### Task 1

Staus: **completed**

Outlining: create `BattleShip.java` and extends `AbstractBattleShip` class. In constructor, use Random object to defind the `shipOrientation` and init other params. Write the member functions which defind in `AbstractBattleShip`. Finish the `checkAttack` method. Check all possible situation. Defind a array `shipCoordinatesRecord` to record the coordinate which has been hit.

### Task 2

Staus: **completed**

Outlining: Create `GameGrid.java` anad extends `AbstarctGameGrid`. Create the `initializeGrid()`. Finish the `generateShips()`. Finish the `placeShip()`. Create `PlayerGameGrid.java` and `OpponentGameGrid.java`, extends `GameGrid`. Defind `printGrid()` in them.

### Task 3

Staus: **completed**

Outlining: Create `Game.java` and implement `GameControls`.create player's and opponent's grid and implement getter methods of them. Implement `exitGame()` method. Implement `checkVictory()`. Implement `playRound()` to make robot smart.

### Task 4

Staus: **completed**

Outlining: Create `Rungame.java` and start it by create a `Game` object. Use params to get the height, hight and the number of ships. Use while loop function to make sure rounds are loop. Use regex `^[0-9]+,[0-9]+$` to match corret input and use `exitGame()` function which in game object to check the `exit`.

### Task 5

Staus: **completed**

Outlining: 

## Fast Start

Switch to the JAVA project root directory `assignment`

- package the project

```shell
mvn package
```

- run the jar with you params which are **width**, **height** and **number of ships**

```shell
java -jar .\target\assignment-1.0-SNAPSHOT.jar 5 5 3
```
