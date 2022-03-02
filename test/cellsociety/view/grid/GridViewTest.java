package cellsociety.view.grid;

import cellsociety.control.Controller;
import cellsociety.view.CA;
import cellsociety.view.View;
import cellsociety.view.cell.CellColorMap;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GridViewTest extends DukeApplicationTest {

  Controller controller;
  CA ca;
  View view;
  CellColorMap cellColorMap;

  @Override
  public void start(Stage stage) {
    controller = new Controller(10, 10);
    view = new View(controller);
    ca = new CA(controller);
    cellColorMap = ca.getColorMap();
  }

  @Test
  void updateGridViewTest() {
    controller.initializeRandomGrid();
    Color expected = cellColorMap.getColor(controller.getStatusGrid()[0][0]);
    assertEquals(expected, ca.getGridView().cellGrid[0][0].getColor());
  }
}