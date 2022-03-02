package cellsociety.model.simulations;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;

import java.util.List;

/**
 * backend model for Percolation CA simulation
 *
 * @author David Wu
 * @author Henry Huynh
 * @author Luke Turkovich
 * @author Gordon Kim
 */

public class PercolationModel extends CAModel {

  // Let 0 = blocked, 1 = open, 2 = full
  private static final int NUM_STATES = 3;

  private UnionFinder myFinder;

  private final int numOfElements;

  private final int FULL = 2;
  private final int OPEN = 1;
  private final int BLOCKED = 0;

  private final int TOP_NODE = 0;
  private final int BOTTOM_NODE = 1;

  // Account for TOP_NODE and BOTTOM_NODE
  private final int NUMBER_OF_OUTER_NODE = 2;


  /**
   * Constructor of the Percolation model
   *
   * @param row row number for myGrid
   * @param col column number for myGrid
   */
  public PercolationModel(int row, int col, CellShape cellShape, NeighborPolicy neighborPolicy,
      EdgePolicy edgePolicy) {
    super(row, col, cellShape, neighborPolicy, edgePolicy);
    numOfElements = row * col + NUMBER_OF_OUTER_NODE;
    myFinder = new UnionFinder(numOfElements);
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
        cellGrid[r][c] = new Cell(r, c, BLOCKED); // initially all blocked
      }
    }
    return cellGrid;
  }

  @Override
  public void updateCellGrid(int[][] statusGrid) {
    super.updateCellGrid(statusGrid);
    int row = statusGrid.length;
    int col = statusGrid[0].length;
    myFinder = new UnionFinder(row * col + NUMBER_OF_OUTER_NODE);
  }

  @Override
  void updateCellStatus() {

    for (int r = 0; r < getNumRows(); r++) {
      for (int c = 0; c < getNumCols(); c++) { // assume rectangle 2-d matrix
        Cell currentCell = getCellGrid()[r][c]; //TODO: getGridCell(r, c)

        executeRules(currentCell);
      }
    }
  }

  /**
   * Check and execute the rules for the game
   *
   * @param currentCell the cell we are examining at the moment Note that for this model we have it
   *                    percolate if it is near left edge
   */
  @Override
  void executeRules(Cell currentCell) {
    int myRow = currentCell.getRow();
    int myCol = currentCell.getCol();

    openCellUnionFind(currentCell, myRow, myCol);

    currentCell.setFutureStatus(
        currentCell.getMyStatus() == BLOCKED ? BLOCKED : (isFull(myRow, myCol) ? FULL : OPEN));
  }

  private void openCellUnionFind(Cell currentCell, int myRow, int myCol) {
    if (currentCell.getMyStatus() != BLOCKED) {
      connectToEndNode(currentCell, myRow, myCol, 0, TOP_NODE);
      connectToEndNode(currentCell, myRow, myCol, getNumRows() - 1, BOTTOM_NODE);

      List<Cell> currentNeighbors = cellNeighborMap.get(currentCell);
      connectToNeighbors(myRow, myCol, currentNeighbors);
    }
  }

  private void connectToNeighbors(int myRow, int myCol, List<Cell> currentNeighbors) {
    for (Cell neighbor : currentNeighbors) {

      int colCoordinate = neighbor.getCol();
      int rowCoordinate = neighbor.getRow();

      if (neighbor.getMyStatus() != BLOCKED) {
        myFinder.union(myRow * getNumCols() + myCol + NUMBER_OF_OUTER_NODE,
            rowCoordinate * getNumCols() + colCoordinate + NUMBER_OF_OUTER_NODE);
      }
    }
  }

  private void connectToEndNode(Cell currentCell, int myRow, int myCol, int edgeIndex, int node) {
    if (currentCell.getMyStatus() == OPEN && (myRow == edgeIndex)) {
      currentCell.setFutureStatus(FULL);
      myFinder.union(myRow * getNumCols() + myCol + NUMBER_OF_OUTER_NODE, node);
    }
  }

  /**
   * Returns true if and only if site (row, col) is FULL
   *
   * @param row row index in range [0,N-1]
   * @param col column index in range [0,N-1]
   */
  public boolean isFull(int row, int col) {
    return myFinder.isConnected(row * getNumCols() + col + NUMBER_OF_OUTER_NODE, TOP_NODE);
  }


  /**
   * Returns true if the simulated percolation actually percolates. What it means to percolate could
   * depend on the system being simulated, but returning true typically means there's a connected
   * path from top-to-bottom.
   *
   * @return true iff the simulated system percolates
   */
  public boolean isPercolated() {
    return myFinder.isConnected(TOP_NODE, BOTTOM_NODE);
  }

}
