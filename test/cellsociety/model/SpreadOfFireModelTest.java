package cellsociety.model;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.simulations.SpreadOfFireModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpreadOfFireModelTest {

  final int numOfRow = 9;
  final int numOfCol = 9;

  // number arb, overlap confusion if properties
  final int BURNING = 2;
  final int TREE = 1;
  final int EMPTY = 0;

  @Test
  void catchFire() {
    SpreadOfFireModel myGame = new SpreadOfFireModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    myGame.step();

    assertEquals(myGame.getCellGrid()[numOfRow / 2][numOfCol / 2].getMyStatus(), EMPTY);
  }

  @Test
  void initializeDefaultGrid() {
    SpreadOfFireModel myGame = new SpreadOfFireModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);

    int[][] defaultGrid = new int[numOfRow][numOfCol];
    int cellStatus = 0;
    for (int r = 0; r < defaultGrid.length; r++) {
      for (int c = 0; c < defaultGrid[0].length; c++) {
        int mid = defaultGrid.length / 2;

        boolean isCenter = (r == mid && c == mid);
        boolean isEmpty =
            r == 0 || c == 0 || r == defaultGrid.length - 1 || c == defaultGrid[0].length - 1;

        cellStatus = isCenter ? BURNING : (isEmpty ? EMPTY : TREE);
        defaultGrid[r][c] = cellStatus;
      }
    }

    boolean error = false;
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        if (myGame.getCellGrid()[r][c].getMyStatus() != defaultGrid[r][c]) {
          error = true;
        }

      }
    }
    assertFalse(error);
  }
}