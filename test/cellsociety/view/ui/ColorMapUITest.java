package cellsociety.view.ui;

import cellsociety.control.Controller;
import cellsociety.control.SimKey;
import cellsociety.view.CA;
import cellsociety.view.View;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.cell.CellRectangleView;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorMapUITest extends DukeApplicationTest {

  Controller controller;
  CA ca;
  View view;
  CellColorMap cellColorMap;

  @Override
  public void start(Stage stage) {
    controller = new Controller(10, 10);
    view = controller.getView();
    ca = view.getCA();
    cellColorMap = ca.getColorMap();
    startSimulation();
  }

  @Test
  void editColorMapTest() {
    for (int i = 0; i < 3; i++) {
      ColorPicker picker = lookup("#color-picker" + i).query();
      Color expected = Color.RED;
      setValue(picker, expected);
      assertEquals(expected, ca.getColorMap().getColor(i));
    }
  }

  void startSimulation() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "English");
  }
}