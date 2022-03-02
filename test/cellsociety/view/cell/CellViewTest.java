package cellsociety.view.cell;

import cellsociety.control.Controller;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


public class CellViewTest extends DukeApplicationTest {

  CellView cell;
  CellColorMap colorMap;
  Controller controller;

  @Override
  public void start(Stage stage) {
    colorMap = new CellColorMap();
  }

  @Test
  void setColorTest() {
    int status = 1;
    Color expected = colorMap.getColor(1);
    cell.setColor(status, colorMap);
    assertEquals(expected, cell.getColor());
  }
}
