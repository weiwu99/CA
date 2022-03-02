package cellsociety.model.layout;

import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;

import java.util.*;

// THOUGHTS:
// Arrangement class maybe shouldn't need to hold anything and only be used once in the model
// so the model will hold the Map<Cell, List<Cell>> but it will be calculated through Arrangement
// When input changes the policies model will call the Arrangement method to recreate the map

/**
 * Abstract class that sets up format for an arrangement of cells (with dynamic neighbors)
 *
 * @author Luke Turkovich
 * @author Gordon Kim
 */

public abstract class Arrangement {

  protected Map<Cell, List<Cell>> cellNeighborMap;
  protected Cell[][] cellGrid;
  protected NeighborPolicy neighborPolicy;
  protected EdgePolicy edgePolicy;

  /**
   * initializes cells to hold
   *
   * @param cellGrid is the grid of cells in the model
   */
  public Arrangement(Cell[][] cellGrid, NeighborPolicy neighborPolicy, EdgePolicy edgePolicy) {
    this.cellGrid = cellGrid;
    this.neighborPolicy = neighborPolicy;
    this.edgePolicy = edgePolicy;
    initializeNeighbors(cellGrid);
  }

  /**
   * initializes all neighbors for every cell in grid
   *
   * @param cellGrid holds row, col representation of all cells
   */
  void initializeNeighbors(Cell[][] cellGrid) {
    cellNeighborMap = new HashMap<>();
    for (Cell[] row : cellGrid) {
      for (Cell cell : row) {
        List<Cell> neighbors = new ArrayList<>();
        cellNeighborMap.put(cell, neighbors);
        switch (neighborPolicy) {
          case COMPLETE -> addNeighborsComplete(cell);
          case CARDINAL -> addNeighborsCardinal(cell);
          case VERTEX -> addNeighborsVertex(cell);
        }
      }
    }
  }

  /**
   * adds all cardinal neighbors for a cell
   *
   * @param cell is the cell to add neighbors to
   */
  abstract void addNeighborsCardinal(Cell cell);

  /**
   * adds all vertex neighbors for a cell
   *
   * @param cell is the cell to add neighbors to
   */
  abstract void addNeighborsVertex(Cell cell);

  /**
   * adds complete (vertex and cardinal) set of neighbors to cell
   *
   * @param cell is the cell we want to add all neighbors to
   */
  public void addNeighborsComplete(Cell cell) {
    addNeighborsCardinal(cell);
    addNeighborsVertex(cell);
  }


  /**
   * adds cell at appropriate row and col to neighbors list for cell
   *
   * @param cell        is the cell we want to add neighbors to
   * @param neighborRow row of neighbor
   * @param neighborCol col of neighbor
   */
  protected void addNeighborCell(Cell cell, int neighborRow, int neighborCol) {
    //Restricted edge policy
    int row;
    int col;

    switch (edgePolicy) {
      case FINITE:
        if (neighborRow < 0 || neighborRow >= cellGrid.length) {
          return;
        }
        if (neighborCol < 0 || neighborCol >= cellGrid[0].length) {
          return;
        }
        cellNeighborMap.get(cell).add(cellGrid[neighborRow][neighborCol]);
        break;
      case TOROIDAL:
        row = neighborRow < 0 ? neighborRow + cellGrid.length : neighborRow % cellGrid.length;
        col = neighborCol < 0 ? neighborCol + cellGrid[0].length : neighborCol % cellGrid[0].length;
        cellNeighborMap.get(cell).add(cellGrid[row][col]);
        break;
      case RANDOM:
        if (cell.getRow() <= 0 || cell.getRow() >= cellGrid.length - 1) {
          row = (int) (Math.random() * cellGrid.length);
        } else {
          row = neighborRow;
        }
        if (cell.getCol() <= 0 || cell.getCol() >= cellGrid[0].length - 1) {
          col = (int) (Math.random() * cellGrid[0].length);
        } else {
          col = neighborCol;
        }
        cellNeighborMap.get(cell).add(cellGrid[row][col]);
        break;
    }
  }

  /**
   * adds newCell to cells
   *
   * @param newCell is what we are adding to the arrangement
   */
  public void addCell(Cell newCell) {
    cellNeighborMap.put(newCell, new ArrayList<>());
  }

  /**
   * adds newCell and its neighbors to cells
   *
   * @param newCell   is what we are adding to the arrangement
   * @param neighbors holds all cells that are adjacent to newCell
   */
  public void addCell(Cell newCell, List<Cell> neighbors) {
    cellNeighborMap.put(newCell, neighbors);
  }

  /**
   * gets the cells for this arrangement
   *
   * @return Set containing all cells
   */
  public Set<Cell> getCells() {
    return cellNeighborMap.keySet();
  }

  /**
   * gets a cell's neighbors
   *
   * @param cell specifies which cell to get neighbors for
   * @return List of specified cell's neighbors
   */
  public List<Cell> getNeighbors(Cell cell) {
    return cellNeighborMap.get(cell);
  }

  /**
   * get all neighbors for an entire grid
   *
   * @return map of all cells to list of its neighbors
   */
  public Map<Cell, List<Cell>> getCellNeighborMap() {
    return cellNeighborMap;
  }
}
