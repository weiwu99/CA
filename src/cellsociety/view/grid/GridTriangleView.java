package cellsociety.view.grid;

import cellsociety.control.Controller;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.cell.CellTriangleView;

/**
 * view for a Triangle shaped grid
 *
 * @author Gordon Kim
 */

public class GridTriangleView extends GridView {

  public GridTriangleView(Controller controller, CellColorMap colorMap) {
    super(controller);
    initializeGridView(controller, colorMap);
  }

  @Override
  void initializeGridView(Controller controller, CellColorMap colorMap) {
    double length =
        col % 2 == 0 ? (double) WIDTH / (col / 2 + 0.5) : (double) WIDTH / ((col + 1) / 2);
    double height = (double) HEIGHT / row;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        boolean pointUp = i % 2 != j % 2;
        double initialX = calculateInitialX(i, j, length);
        double initialY = calculateInitialY(i, j, height);
        CellTriangleView cell = new CellTriangleView(controller, i, j, pointUp, length, height,
            initialX, initialY);
        cellGrid[i][j] = cell;
        this.getChildren().add(cell);
      }
    }
    this.getStyleClass().add("grid");
  }

  private double calculateInitialX(int row, int col, double length) {
    if (col == 0) {
      if (row % 2 == 1) {
        return length / 2;
      }
      return 0;
    }
    if (row % 2 == 0) {
      return (col + 1) / 2 * length;
    } else {
      return (col) / 2 * length + length / 2;
    }
  }

  private double calculateInitialY(int row, int col, double height) {
    return (double) row * height;
  }
}
