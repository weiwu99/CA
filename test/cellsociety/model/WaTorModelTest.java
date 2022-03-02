package cellsociety.model;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.simulations.WaTorModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaTorModelTest {

  final int numOfRow = 10;
  final int numOfCol = 10;

  private final int FISH = 1;
  private final int SHARK = 2;

  @Test
  void step() {
    WaTorModel myGame = new WaTorModel(numOfRow, numOfCol, CellShape.RECTANGLE,
        NeighborPolicy.CARDINAL, EdgePolicy.FINITE);
    int sharkCount = 0;
    int fishCount = 0;

    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        if (myGame.getCellGrid()[r][c].getMyStatus() == SHARK) {
          sharkCount++;
        } else if (myGame.getCellGrid()[r][c].getMyStatus() == FISH) {
          fishCount++;
        }
      }
    }

    myGame.step();

    int sharkCount2 = 0;
    int fishCount2 = 0;
    for (int r = 0; r < myGame.getNumRows(); r++) {
      for (int c = 0; c < myGame.getNumCols(); c++) {
        if (myGame.getCellGrid()[r][c].getMyStatus() == SHARK) {
          sharkCount2++;
        } else if (myGame.getCellGrid()[r][c].getMyStatus() == FISH) {
          fishCount2++;
        }
      }
    }

    assert (sharkCount2 >= sharkCount && fishCount2 <= fishCount);
  }
}