package cellsociety.model.layout;

import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Hexagonal interpretation of Arrangement
 *
 * @author Luke Turkovich
 * @author Gordon Kim
 */

public class Hexagonal extends Arrangement {

  /**
   * creates new hexagonal arrangement based on specified parameters
   *
   * @param cellGrid       is the row, col representation of cells
   * @param neighborPolicy the current neighbor policy
   * @param edgePolicy     the current edge policy
   */
  public Hexagonal(Cell[][] cellGrid, NeighborPolicy neighborPolicy, EdgePolicy edgePolicy) {
    super(cellGrid, neighborPolicy, edgePolicy);
  }

  @Override
  /**
   * specific implementation of addNeighborsCardinal for a hexagonal arrangement
   */
  public void addNeighborsCardinal(Cell cell) {
    int cellRow = cell.getRow();
    int cellCol = cell.getCol();
    addNeighborCell(cell, cellRow - 1, cellCol);
    addNeighborCell(cell, cellRow + 1, cellCol);
    addNeighborCell(cell, cellRow, cellCol - 1);
    addNeighborCell(cell, cellRow, cellCol + 1);
    addNeighborCell(cell, cellRow + 1, cellCol - 1);
    addNeighborCell(cell, cellRow + 1, cellCol + 1);
  }

  @Override
  /**
   * specific implementation of addNeighborsVertex for hexagonal arrangement (doesn't exist - no vertex neighbors)
   */
  public void addNeighborsVertex(Cell cell) {

  }
}
