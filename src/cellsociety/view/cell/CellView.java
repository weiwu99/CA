package cellsociety.view.cell;

import cellsociety.control.Controller;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * abstract class for a view of a cell, framework for all subclasses, extends Polygon
 *
 * @author Gordon Kim
 */

public abstract class CellView extends Polygon {

  protected Color color;
  protected double initialX;
  protected double initialY;

  public CellView(Controller controller, int row, int col, double initialX, double initialY) {
    this.color = Color.web("F3F4F7");
    this.initialX = initialX;
    this.initialY = initialY;
    this.setFill(getColor());
    this.setStrokeWidth(1);
    this.setStroke(Color.BLACK);
    this.setId(row + "," + col);
    this.setOnMouseClicked(e -> controller.incrementCellStatus(row, col));
  }

  public void setColor(int status, CellColorMap colorMap) {
    for (int cellStatus : colorMap.getColorMap().keySet()) {
      if (cellStatus == status) {
        this.color = colorMap.getColor(cellStatus);
      }
    }
    this.setFill(color);
  }

  public Color getColor() {
    return color;
  }
}
