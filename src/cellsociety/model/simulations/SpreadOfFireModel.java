package cellsociety.model.simulations;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * backend model for SpreadingOfFire CA simulation
 *
 * @author David Wu
 * @author Luke Turkovich
 * @author Gordon Kim
 * @author Henry Huynh
 */

public class SpreadOfFireModel extends CAModel {

  private final int NUM_STATES = 3;
  private final double DEFAULT_PROBABILITY_CATCH = 0.5;

  private double myProbability;

  // number arb, overlap confusion if properties
  private final int BURNING = 2;
  private final int TREE = 1;
  private final int EMPTY = 0;

  /**
   * Constructor of the GameOfLife model
   *
   * @param row row number for myGrid
   * @param col column number for myGrid
   */
  public SpreadOfFireModel(int row, int col, CellShape cellShape, NeighborPolicy neighborPolicy,
      EdgePolicy edgePolicy) {
    super(row, col, cellShape, neighborPolicy, edgePolicy);
    myProbability = DEFAULT_PROBABILITY_CATCH;
  }

  @Override
  public int getNumStates() {
    return NUM_STATES;
  }

  @Override
  Cell[][] initializeDefaultGrid(int row, int col) {
    Cell[][] cellGrid = new Cell[row][col];
    int cellStatus = 0;
    for (int r = 0; r < cellGrid.length; r++) {
      for (int c = 0; c < cellGrid[0].length; c++) {
        int mid = cellGrid.length / 2;

        boolean isCenter = (r == mid && c == mid);
        boolean isEmpty =
            r == 0 || c == 0 || r == cellGrid.length - 1 || c == cellGrid[0].length - 1;

        cellStatus = isCenter ? BURNING : (isEmpty ? EMPTY : TREE);
        cellGrid[r][c] = new Cell(r, c, cellStatus);
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
    List<Pair<Boolean, Integer>> statusPairs = cellStatusPairs(currentCell);

    for (Pair condition : statusPairs) {
      if (condition.getKey().equals(true)) {
        currentCell.setFutureStatus((int) condition.getValue());
      }
    }
  }

  private List<Pair<Boolean, Integer>> cellStatusPairs(Cell currentCell) {
    boolean isEmpty = currentCell.getMyStatus() == EMPTY;
    boolean isBurning = currentCell.getMyStatus() == BURNING;
    boolean isNothing = !(shouldBurn(currentCell) || isEmpty || isBurning);

    Pair<Boolean, Integer> mightBurnCell = new Pair<>(shouldBurn(currentCell), getBurnStatus());
    Pair<Boolean, Integer> emptyCell = new Pair<>(currentCell.getMyStatus() == EMPTY,
        currentCell.getMyStatus());
    Pair<Boolean, Integer> burningCell = new Pair<>(isBurning, EMPTY);
    Pair<Boolean, Integer> nothingCell = new Pair<>(isNothing, TREE);

    List<Pair<Boolean, Integer>> statusPairs = List.of(mightBurnCell, emptyCell, burningCell,
        nothingCell);
    return statusPairs;
  }

  private int getBurnStatus() {
    return Math.random() < myProbability ? BURNING : TREE;
  }

  private boolean shouldBurn(Cell currentCell) {
    boolean mightBurn = currentCell.getMyStatus() == TREE && hasBurningNeighbors(currentCell);
    return mightBurn;
  }

  private boolean hasBurningNeighbors(Cell currentCell) {
    boolean isBurning = false;
    for (Cell neighbor : cellNeighborMap.get(currentCell)) {
      if (neighbor.getMyStatus() == BURNING) {
        isBurning = true;
      }
    }
    return isBurning;
  }
}
