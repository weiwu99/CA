package cellsociety.model.simulations;

import cellsociety.control.CellShape;
import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;
import cellsociety.model.layout.Arrangement;
import cellsociety.model.layout.Hexagonal;
import cellsociety.model.layout.Rectangular;
import cellsociety.model.layout.Triangular;

import java.util.List;
import java.util.Map;

/**
 * superclass for all models, sets up structure for each specific subclass
 *
 * @author Gordon Kim
 * @author Luke Turkovich
 * @author David Wu
 */

public abstract class CAModel {

  protected Map<Cell, List<Cell>> cellNeighborMap;

  private Cell[][] cellGrid;
  private int numStates;
  private CellShape cellShape;
  private NeighborPolicy neighborPolicy;
  private EdgePolicy edgePolicy;

  public CAModel(int row, int col, CellShape cellShape, NeighborPolicy neighborPolicy,
      EdgePolicy edgePolicy) {
    this.cellShape = cellShape;
    this.neighborPolicy = neighborPolicy;
    this.edgePolicy = edgePolicy;
    numStates = getNumStates();
    cellGrid = initializeDefaultGrid(row, col);
    cellNeighborMap = updateNeighbors();
  }

  /**
   * Update all cells in the grid for one step
   */
  public void step() {
    updateCellStatus();
    for (Cell cell : cellNeighborMap.keySet()) {
      cell.setMyStatus(cell.getFutureStatus());
    }
  }

  public abstract int getNumStates();

  //TODO: Use arrangements instead of 2d arrays
  //      Hide data structures to backends -> change data structure inside arrangement objects
  abstract Cell[][] initializeDefaultGrid(int row, int col);

  abstract void updateCellStatus();

  abstract void executeRules(Cell currentCell);

  public void reset() {
    cellGrid = initializeDefaultGrid(cellGrid.length, cellGrid[0].length);
    cellNeighborMap = updateNeighbors();
  }

  public Cell[][] getCellGrid() {
    return cellGrid;
  }

  public void setCellGrid(Cell[][] cellGrid) {
    this.cellGrid = cellGrid;
  }

  public int getNumRows() {
    return cellGrid.length;
  }

  public int getNumCols() {
    return cellGrid[0].length;
  }

  public void updateCellGrid(int[][] statusGrid) {
    cellGrid = initializeDefaultGrid(statusGrid.length, statusGrid[0].length);
    cellNeighborMap = updateNeighbors();
    for (int i = 0; i < statusGrid.length; i++) {
      for (int j = 0; j < statusGrid[0].length; j++) {
        cellGrid[i][j].setMyStatus(statusGrid[i][j]);
      }
    }
  }

  public void updateCellShape(CellShape cellShape) {
    this.cellShape = cellShape;
    cellNeighborMap = updateNeighbors();
  }

  public void updateEdgePolicy(EdgePolicy edgePolicy) {
    this.edgePolicy = edgePolicy;
    cellNeighborMap = updateNeighbors();
  }

  public void updateNeighborPolicy(NeighborPolicy neighborPolicy) {
    this.neighborPolicy = neighborPolicy;
    cellNeighborMap = updateNeighbors();
  }

  public void incrementCellStatus(int row, int col) {
    Cell cell = cellGrid[row][col];
    cell.setMyStatus((cell.getMyStatus() + 1) % numStates);
  }

  private Map<Cell, List<Cell>> updateNeighbors() {
    Arrangement arrangement = new Rectangular(cellGrid, neighborPolicy, edgePolicy);
    switch (cellShape) {
      case RECTANGLE -> arrangement = new Rectangular(cellGrid, neighborPolicy, edgePolicy);
      case TRIANGLE -> arrangement = new Triangular(cellGrid, neighborPolicy, edgePolicy);
      case HEXAGON -> arrangement = new Hexagonal(cellGrid, neighborPolicy, edgePolicy);
    }

    return arrangement.getCellNeighborMap();
  }

  public CellShape getCellShape() {
    return cellShape;
  }

  public NeighborPolicy getNeighborPolicy() {
    return neighborPolicy;
  }

  public EdgePolicy getEdgePolicy() {
    return edgePolicy;
  }


}
