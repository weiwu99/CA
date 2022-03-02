package cellsociety.view.grid;

import cellsociety.control.Controller;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.cell.CellView;
import javafx.scene.Group;

/**
 * abstract class for a GridView that provides all implementation structure for subclasses
 *
 * @author Gordon Kim
 */

public abstract class GridView extends Group {

  final static int WIDTH = 700;
  final static int HEIGHT = 700;

  int row;
  int col;
  CellView[][] cellGrid;

  public GridView(Controller controller) {
    initializeGrid(controller.getStatusGrid());
  }

  private void initializeGrid(int[][] statusGrid) {
    this.row = statusGrid.length;
    this.col = statusGrid[0].length;
    this.cellGrid = new CellView[row][col];
  }

  public void updateGridView(Controller controller, CellColorMap colorMap) {
    int[][] statusGrid = controller.getStatusGrid();
    if (statusGrid.length != row || statusGrid[0].length != col) {
      this.getChildren().clear();
      initializeGrid(statusGrid);
      initializeGridView(controller, colorMap);
    }
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        int status = statusGrid[i][j];
        cellGrid[i][j].setColor(status, colorMap);
      }
    }
  }

  abstract void initializeGridView(Controller controller, CellColorMap colorMap);
}
