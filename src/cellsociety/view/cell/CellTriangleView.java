package cellsociety.view.cell;

import cellsociety.control.Controller;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * view for a Triangle shaped cell
 *
 * @author Gordon Kim
 */

public class CellTriangleView extends CellView {

  public CellTriangleView(Controller controller, int row, int col, boolean pointUp, double length,
      double height, double initialX, double initialY) {
    super(controller, row, col, initialX, initialY);
    setCell(pointUp, length, height);
  }

  private void setCell(boolean pointUp, double length, double height) {
    if (pointUp) {
      this.getPoints().setAll(
          initialX, initialY,
          initialX - length / 2, initialY + height,
          initialX + length / 2, initialY + height
      );
    } else {
      this.getPoints().setAll(
          initialX, initialY,
          initialX + length, initialY,
          initialX + length / 2, initialY + height
      );
    }
  }
}
