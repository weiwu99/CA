package cellsociety.model;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.simulations.PercolationModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PercolationModelTest {

  final int numOfRow = 10;
  final int numOfCol = 14;

  final int OPEN = 1;
  final int FULL = 2;

  @Test
  void isPercolatedSinceStart() {
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    assertFalse(myGame.isPercolated());
  }

  @Test
  void stepNotPercolated() {
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == 3 && r != 5) {
          myGame.getCellGrid()[r][c].setMyStatus(OPEN);
        }
      }
    }
    myGame.step();
    assertFalse(myGame.isPercolated());
  }

  @Test
  void stepPercolated() {
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == 3) {
          myGame.getCellGrid()[r][c].setMyStatus(OPEN);
        }
      }
    }
    myGame.step();
    assert (myGame.isPercolated());
  }

  @Test
  void checkFull() {
    boolean isFull = true;
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == 3) {
          myGame.getCellGrid()[r][c].setMyStatus(OPEN);
        }
      }
    }
    myGame.step();

    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == 3 && myGame.getCellGrid()[r][c].getMyStatus() != FULL) {
          isFull = false;
        }
      }
    }
    assert (isFull);

  }

  @Test
  void diagonalCardinalPercolation() {
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == r) {
          myGame.getCellGrid()[r][c].setMyStatus(OPEN);
        }
      }
    }
    myGame.step();
    assertFalse(myGame.isPercolated());
  }

  @Test
  void diagonalVertexPercolation() {
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.VERTEX, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == r) {
          myGame.getCellGrid()[r][c].setMyStatus(OPEN);
        }
      }
    }
    myGame.step();
    assert (myGame.isPercolated());
  }

  @Test
  void bottomPercolation() {
    PercolationModel myGame = new PercolationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        if (c == 3 || (c == 5 && r == myGame.getNumRows() - 1) || (c == 5
            && r == myGame.getNumRows() - 2)) {
          myGame.getCellGrid()[r][c].setMyStatus(OPEN);
        }
      }
    }
    myGame.step();
    assert (myGame.isPercolated());
  }
}