package cellsociety.view.grid;

import cellsociety.control.Controller;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.cell.CellRectangleView;

/**
 * view for a Rectangle shaped grid
 *
 * @author Gordon Kim
 */

public class GridRectangleView extends GridView {

  public GridRectangleView(Controller controller, CellColorMap colorMap) {
    super(controller);
    initializeGridView(controller, colorMap);
  }

  @Override
  void initializeGridView(Controller controller, CellColorMap colorMap) {
    double width = (double) WIDTH / col;
    double height = (double) HEIGHT / row;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        double initialX = (double) j * width;
        double initialY = (double) i * height;
        CellRectangleView cell = new CellRectangleView(controller, i, j, width, height, initialX,
            initialY);
        cellGrid[i][j] = cell;
        this.getChildren().add(cell);
      }
    }
    this.getStyleClass().add("grid");
  }
}
