package cellsociety.model.simulations;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * backend model for Segregation CA simulation
 *
 * @author David Wu
 * @author Gordon Kim
 * @author Henry Huynh
 */

public class SegregationModel extends CAModel {

  private final int NUM_STATES = 3;

  private final int RED = 2;
  private final int BLUE = 1;
  private final int EMPTY = 0;

  private double myProbability;
  private double colorRatio;
  private double emptyRatio;

  private final double DEFAULT_THRESHOLD = 0.5;
  private final double DEFAULT_COLOR_RATIO = 0.5;
  private final double DEFAULT_EMPTY_RATIO = 0.1;

  /**
   * Constructor of the GameOfLife model
   *
   * @param row row number for myGrid
   * @param col column number for myGrid
   */
  public SegregationModel(int row, int col, CellShape cellShape, NeighborPolicy neighborPolicy,
      EdgePolicy edgePolicy) {
    super(row, col, cellShape, neighborPolicy, edgePolicy);

    myProbability = DEFAULT_THRESHOLD;
    colorRatio = DEFAULT_COLOR_RATIO;
    emptyRatio = DEFAULT_EMPTY_RATIO;
  }

  @Override
  public int getNumStates() {
    return NUM_STATES;
  }

  @Override
  Cell[][] initializeDefaultGrid(int row, int col) {
    Cell[][] cellGrid = new Cell[row][col];
    for (int r = 0; r < row; r++) {
      for (int c = 0; c < col; c++) {
        cellGrid[r][c] = new Cell(r, c, Math.random() < DEFAULT_EMPTY_RATIO ? EMPTY
            : (Math.random() < DEFAULT_COLOR_RATIO ? RED : BLUE));
      }
    }
    return cellGrid;
  }

  /**
   * Update the status for each cell next round
   */
  @Override
  public void updateCellStatus() {
    List<Cell> leavingCells = new ArrayList<>();

    for (Cell cell : cellNeighborMap.keySet()) {
      executeSegregationRules(cell, leavingCells);
    }

    updateMigrantStatus(leavingCells);
  }

  private void updateMigrantStatus(List<Cell> leavingCells) {
    List<Cell> migrants = new ArrayList<>();

    migrants.addAll(leavingCells);
    Collections.shuffle(migrants);

    int index = 0;
    for (Cell leaver : leavingCells) {
      leaver.setFutureStatus(migrants.get(index).getMyStatus());
      index++;
    }
  }

  @Override
  public void executeRules(Cell cell) {
  }

  /**
   * Check and execute the rules for the game
   *
   * @param currentCell the cell we are examining at the moment
   */
  private void executeSegregationRules(Cell currentCell, List<Cell> leavingCells) {
    if (isLeaving(currentCell)) {
      leavingCells.add(currentCell);
    } else {
      currentCell.setFutureStatus(currentCell.getMyStatus());
    }
  }

  /**
   * Decide if the cell wants to move to other places
   *
   * @param currentCell the cell we are currently executing
   * @return The decision whether to leave or not
   */
  private boolean isLeaving(Cell currentCell) {
    double sameNeighbors = 0;
    double numberOfNeighbors = 0;

    for (Cell neighbor : cellNeighborMap.get(currentCell)) {
      if (neighbor.getMyStatus() == currentCell.getMyStatus()) {
        sameNeighbors++;
      }
      if (neighbor.getMyStatus() != EMPTY) {
        numberOfNeighbors++;
      }
    }
    double ratio = sameNeighbors / numberOfNeighbors;
    return currentCell.getMyStatus() == EMPTY || ratio < myProbability;
  }
}
