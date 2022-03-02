package cellsociety.model.layout;

import cellsociety.control.EdgePolicy;
import cellsociety.control.NeighborPolicy;
import cellsociety.model.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * Triangular interpretation of Arrangement
 *
 * @author Luke Turkovich
 * @author Gordon Kim
 */

public class Triangular extends Arrangement {

  /**
   * creates new triangular arrangement based on specified parameters
   *
   * @param cellGrid       is the row, col representation of cells
   * @param neighborPolicy the current neighbor policy
   * @param edgePolicy     the current edge policy
   */
  public Triangular(Cell[][] cellGrid, NeighborPolicy neighborPolicy, EdgePolicy edgePolicy) {
    super(cellGrid, neighborPolicy, edgePolicy);
  }

  @Override
  /**
   * specific implementation of addNeighborsCardinal for a triangular arrangement
   */
  public void addNeighborsCardinal(Cell cell) {
    int cellRow = cell.getRow();
    int cellCol = cell.getCol();

    addNeighborCell(cell, cellRow, cellCol + 1);
    addNeighborCell(cell, cellRow, cellCol - 1);

    if (pointsUp(cellRow, cellCol)) {
      addNeighborCell(cell, cellRow + 1, cellCol);
    } else {
      addNeighborCell(cell, cellRow - 1, cellCol);
    }
  }

  @Override
  /**
   * specific implementation of addNeighborVertex for a triangular arrangement
   */
  public void addNeighborsVertex(Cell cell) {
    int cellRow = cell.getRow();
    int cellCol = cell.getCol();

    int x = pointsUp(cellRow, cellCol) ? 1 : -1;

    addNeighborCell(cell, cellRow - x, cellCol);
    addNeighborCell(cell, cellRow - x, cellCol + 1);
    addNeighborCell(cell, cellRow - x, cellCol - 1);

    addNeighborCell(cell, cellRow, cellCol - 2);
    addNeighborCell(cell, cellRow + x, cellCol - 2);
    addNeighborCell(cell, cellRow + x, cellCol - 1);

    addNeighborCell(cell, cellRow + x, cellCol + 2);
    addNeighborCell(cell, cellRow + x, cellCol + 1);
    addNeighborCell(cell, cellRow + x, cellCol + 2);

  }

  /**
   * determines if a cell at row, col points up or not
   *
   * @param row indicates row of cell in question
   * @param col indicates col of cell in question
   * @return
   */
  private boolean pointsUp(int row, int col) {
    return row % 2 != col % 2;
  }
}
