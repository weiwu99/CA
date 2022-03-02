package cellsociety.view.grid;

import cellsociety.control.Controller;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.cell.CellHexagonView;

/**
 * view for a Hexagon shaped grid
 *
 * @author Gordon Kim
 */

public class GridHexagonView extends GridView {

  public GridHexagonView(Controller controller, CellColorMap colorMap) {
    super(controller);
    initializeGridView(controller, colorMap);
  }

  @Override
  void initializeGridView(Controller controller, CellColorMap colorMap) {
    double length = (double) 2 * WIDTH / (3 * col + 1);
    double height = (double) HEIGHT / (2 * row + 1);
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        double initialX = calculateInitialX(i, j, length);
        double initialY = calculateInitialY(i, j, height);
        CellHexagonView cell = new CellHexagonView(controller, i, j, length, height, initialX,
            initialY);
        cellGrid[i][j] = cell;
        this.getChildren().add(cell);
      }
    }
    this.getStyleClass().add("grid");
  }

  private double calculateInitialX(int row, int col, double length) {
    return (double) col * length * 3 / 2;
  }

  private double calculateInitialY(int row, int col, double height) {
    double y;
    if (col % 2 == 0) {
      y = (double) row * height * 2;
    } else {
      y = (double) row * height * 2 + height;
    }
    return y;
  }
}
