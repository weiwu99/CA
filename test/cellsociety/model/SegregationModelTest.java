package cellsociety.model;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.simulations.SegregationModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegregationModelTest {

  final int numOfRow = 10;
  final int numOfCol = 10;

  final int EMPTY = 0;
  final int BLUE = 1;
  final int RED = 2;

  final double DEFAULT_COLOR_RATIO = 0.5;
  final double DEFAULT_EMPTY_RATIO = 0.1;

  @Test
  void numberCount() {
    int redCount = 0;
    int blueCount = 0;
    int emptyCount = 0;
    SegregationModel myGame = new SegregationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {

        // don't add the target itself!
        if (myGame.getCellGrid()[r][c].getMyStatus() == RED) {
          redCount++;
        } else if (myGame.getCellGrid()[r][c].getMyStatus() == BLUE) {
          blueCount++;
        } else if (myGame.getCellGrid()[r][c].getMyStatus() == EMPTY) {
          emptyCount++;
        }
      }
    }

    myGame.step();
    myGame.step();

    final double MARGIN = 0.2;
    double redRatio = redCount / (redCount + blueCount + emptyCount);
    double blueRatio = blueCount / (redCount + blueCount + emptyCount);
    double emptyRatio = emptyCount / (redCount + blueCount + emptyCount);

    assert ((redRatio >= DEFAULT_COLOR_RATIO * DEFAULT_EMPTY_RATIO - MARGIN) && (redRatio
        <= DEFAULT_COLOR_RATIO * DEFAULT_EMPTY_RATIO + MARGIN));
    assert ((blueRatio >= DEFAULT_COLOR_RATIO * DEFAULT_EMPTY_RATIO - MARGIN) && (blueRatio
        <= DEFAULT_COLOR_RATIO * DEFAULT_EMPTY_RATIO + MARGIN));
    assert ((emptyRatio >= DEFAULT_EMPTY_RATIO - MARGIN) && (emptyRatio
        <= DEFAULT_EMPTY_RATIO + MARGIN));
  }

  @Test
  void randomnessTest() {
    SegregationModel myGame = new SegregationModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);

    int[][] newGrid1 = new int[numOfRow][numOfCol];
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        newGrid1[r][c] = myGame.getCellGrid()[r][c].getMyStatus();
      }
    }

    myGame = new SegregationModel(numOfRow, numOfCol, CellShape.RECTANGLE, NeighborPolicy.CARDINAL,
        EdgePolicy.FINITE);
    int[][] newGrid2 = new int[numOfRow][numOfCol];
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        newGrid2[r][c] = myGame.getCellGrid()[r][c].getMyStatus();
      }
    }

    boolean isRandom = false;
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        if (newGrid1[r][c] != newGrid2[r][c]) {
          isRandom = true;
        }
      }
    }
    assert (isRandom);
  }
}