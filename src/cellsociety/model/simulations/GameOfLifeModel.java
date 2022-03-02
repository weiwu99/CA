package cellsociety.model.simulations;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;

import java.util.List;

/**
 * backend model for GameOfLife CA simulation
 *
 * @author David Wu
 * @author Luke Turkovich
 * @author Gordon Kim
 * @author Henry Huynh
 */

public class GameOfLifeModel extends CAModel {

  private static final int NUM_STATES = 2;

  private final int DEAD = 0;
  private final int ALIVE = 1;

  /**
   * Constructor of the GameOfLife model
   *
   * @param row row number for myGrid
   * @param col column number for myGrid
   */
  public GameOfLifeModel(int row, int col, CellShape cellShape, NeighborPolicy neighborPolicy,
      EdgePolicy edgePolicy) {
    super(row, col, cellShape, neighborPolicy, edgePolicy);
  }

  @Override
  public int getNumStates() {
    return NUM_STATES;
  }

  @Override
  Cell[][] initializeDefaultGrid(int row, int col) {
    Cell[][] cellGrid = new Cell[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        cellGrid[i][j] = new Cell(i, j, DEAD);
      }
    }
    return cellGrid;
  }

  /**
   * Update the status for each cell next round
   */
  @Override
  void updateCellStatus() {
    for (Cell cell : cellNeighborMap.keySet()) {
      executeRules(cell);
    }
  }

  /**
   * Check and execute the rules for the game
   *
   * @param currentCell the cell we are examining at the moment
   */
  @Override
  void executeRules(Cell currentCell) {
    int numNeighborsAlive = 0;
    for (Cell neighbor : cellNeighborMap.get(currentCell)) {
      if (neighbor.getMyStatus() == ALIVE) {
        numNeighborsAlive++;
      }
    }

    currentCell.setFutureStatus(currentCell.getMyStatus() == 1 ?
        (numNeighborsAlive < 2 ? DEAD : numNeighborsAlive < 4 ? ALIVE : DEAD)
        : (numNeighborsAlive == 3 ? ALIVE : DEAD));
  }
}
