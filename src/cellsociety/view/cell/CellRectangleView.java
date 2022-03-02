package cellsociety.view.cell;

import cellsociety.control.Controller;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * view for a Rectangle shaped cell
 *
 * @author Gordon Kim
 */

public class CellRectangleView extends CellView {

  public CellRectangleView(Controller controller, int row, int col, double width, double height,
      double initialX, double initialY) {
    super(controller, row, col, initialX, initialY);
    setCell(width, height);
  }

  private void setCell(double width, double height) {
    this.getPoints().setAll(
        initialX, initialY,
        initialX + width, initialY,
        initialX + width, initialY + height,
        initialX, initialY + height);
  }
}
