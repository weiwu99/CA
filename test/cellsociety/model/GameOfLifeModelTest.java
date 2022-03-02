
package cellsociety.model;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.layout.Arrangement;
import cellsociety.model.simulations.GameOfLifeModel;
import cellsociety.model.simulations.PercolationModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeModelTest {

  final int numOfRow = 10;
  final int numOfCol = 8;


  final int DEAD = 0;
  final int ALIVE = 0;

  @Test
  void getMyGridNull() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    Cell[][] myCellGrid = myGame.getCellGrid();
    assertNotNull(myCellGrid);
  }

  @Test
  void getMyGrid() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    Cell[][] myGrid = myGame.getCellGrid();
    boolean error = false;
    for (int r = 0; r < myGrid.length; r++) {
      for (int c = 0; c < myGrid[0].length; c++) {

        // don't add the target itself!
        if (myGrid[r][c].getMyStatus() != DEAD) {
          error = true;
        }
      }
    }
    assertFalse(error);
  }

  @Test
  void getNumRows() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    Cell[][] myGrid = myGame.getCellGrid();
    assertEquals(myGrid.length, numOfRow);
  }

  @Test
  void getNumCols() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    Cell[][] myGrid = myGame.getCellGrid();
    assertEquals(myGrid[0].length, numOfCol);
  }

  @Test
  void getCellShape() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.TRIANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    assertEquals(myGame.getCellShape(), CellShape.TRIANGLE);
  }

  @Test
  void getNeighborPolicy() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.HEXAGON,
        NeighborPolicy.COMPLETE, EdgePolicy.RANDOM);
    assertEquals(myGame.getNeighborPolicy(), NeighborPolicy.COMPLETE);
  }

  @Test
  void getEdgePolicy() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.HEXAGON,
        NeighborPolicy.VERTEX, EdgePolicy.TOROIDAL);
    assertEquals(myGame.getEdgePolicy(), EdgePolicy.TOROIDAL);
  }

  @Test
  void updateCellShape() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.TRIANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    myGame.updateCellShape(CellShape.HEXAGON);
    assertEquals(myGame.getCellShape(), CellShape.HEXAGON);
    myGame.updateCellShape(CellShape.TRIANGLE);
    assertEquals(myGame.getCellShape(), CellShape.TRIANGLE);
  }

  @Test
  void updateNeighborPolicy() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.HEXAGON,
        NeighborPolicy.COMPLETE, EdgePolicy.RANDOM);
    myGame.updateNeighborPolicy(NeighborPolicy.VERTEX);
    assertEquals(myGame.getNeighborPolicy(), NeighborPolicy.VERTEX);
  }

  @Test
  void updateEdgePolicy() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.HEXAGON,
        NeighborPolicy.VERTEX, EdgePolicy.TOROIDAL);
    myGame.updateEdgePolicy(EdgePolicy.RANDOM);
    assertEquals(myGame.getEdgePolicy(), EdgePolicy.RANDOM);
  }

  @Test
  void reset() {
    final int WRONG_VALUE = 11;
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        myGame.getCellGrid()[r][c].setMyStatus(WRONG_VALUE);
      }
    }

    myGame.reset();
    boolean error = false;
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        // don't add the target itself!
        if (myGame.getCellGrid()[r][c].getMyStatus() != 0) {
          error = true;
        }
      }
    }
    assertFalse(error);
  }

  @Test
  void setCellGrid() {
    final int NEW_VALUE = 2;
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    Cell[][] newGrid = new Cell[numOfRow][numOfCol];
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        newGrid[r][c] = new Cell(r, c, NEW_VALUE);
      }
    }

    myGame.setCellGrid(newGrid);
    boolean error = false;
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        // don't add the target itself!
        if (myGame.getCellGrid()[r][c].getMyStatus() != NEW_VALUE) {
          error = true;
        }
      }
    }
    assertFalse(error);
  }

  @Test
  void updateCellGrid() {
    final int NEW_VALUE = 2;

    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    int[][] statusGrid = new int[numOfRow][numOfCol];
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        statusGrid[r][c] = NEW_VALUE;
      }
    }

    myGame.updateCellGrid(statusGrid);

    boolean error = false;
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        // don't add the target itself!
        if (myGame.getCellGrid()[r][c].getMyStatus() != NEW_VALUE) {
          error = true;
        }
      }
    }
    assertFalse(error);
  }

  /**
   * This is the testing matrix: 0 1 0 1 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 1 1 1 0 0 1
   * 0 0 1 1 0 0 0 0 0 0 1 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0
   * <p>
   * And we are checking status value at these cells labeled as "?": 0 ? 0 1 0 0 0 0 0 ? 0 1 1 ? 0 0
   * 0 0 0 0 0 0 0 0 ? 1 1 0 1 1 1 0 0 1 ? 0 1 ? 0 0 0 0 0 0 1 0 0 0 0 ? 0 0 0 0 0 0 0 0 0 0 0 0 0
   * 0
   **/

  @Test
  void step() {
    GameOfLifeModel myGame = new GameOfLifeModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.COMPLETE, EdgePolicy.FINITE);
    Cell[][] myGrid = myGame.getCellGrid();

    // zero cell rule: test [6,1]
    myGrid[6][1].setMyStatus(ALIVE);

    // single cell rule: test [0,1] and [1,1]
    myGrid[0][1].setMyStatus(ALIVE);

    // two cells rule: test [1, 4] and [3, 0]
    myGrid[0][3].setMyStatus(ALIVE);
    myGrid[1][3].setMyStatus(ALIVE);
    myGrid[1][4].setMyStatus(ALIVE);

    // three cells rule: test [4, 2]
    myGrid[3][1].setMyStatus(ALIVE);
    myGrid[4][1].setMyStatus(ALIVE);
    myGrid[3][2].setMyStatus(ALIVE);

    // four and above cells rule: test[4, 5]
    myGrid[3][4].setMyStatus(ALIVE);
    myGrid[3][5].setMyStatus(ALIVE);
    myGrid[3][6].setMyStatus(ALIVE);
    myGrid[4][4].setMyStatus(ALIVE);
    myGrid[5][4].setMyStatus(ALIVE);
    myGrid[4][5].setMyStatus(ALIVE);

    myGame.step();

    assertEquals(DEAD, myGrid[6][1].getMyStatus());

    assertEquals(DEAD, myGrid[0][1].getMyStatus());
    assertEquals(DEAD, myGrid[1][1].getMyStatus());

    assertEquals(ALIVE, myGrid[1][4].getMyStatus());
    assertEquals(DEAD, myGrid[3][0].getMyStatus());

    assertEquals(ALIVE, myGrid[4][2].getMyStatus());

    assertEquals(DEAD, myGrid[4][5].getMyStatus());
  }

}
