package cellsociety.view.cell;

import cellsociety.control.Controller;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * view for a Hexagon shaped cell
 *
 * @author Gordon Kim
 */

public class CellHexagonView extends CellView {

  public CellHexagonView(Controller controller, int row, int col, double length, double height,
      double initialX, double initialY) {
    super(controller, row, col, initialX, initialY);
    setCell(length, height);
  }

  private void setCell(double length, double height) {
    this.getPoints().setAll(
        initialX, initialY,
        initialX + length / 2, initialY - height,
        initialX + length * 3 / 2, initialY - height,
        initialX + length + length, initialY,
        initialX + length * 3 / 2, initialY + height,
        initialX + length / 2, initialY + height
    );

  }
}
