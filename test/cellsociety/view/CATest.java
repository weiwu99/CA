package cellsociety.view;

import cellsociety.control.Controller;
import cellsociety.view.cell.CellColorMap;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CATest extends DukeApplicationTest {

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
  void startAnimationTest() {
    ca.startAnimation();
    assertNotEquals(0, ca.getAnimation().getKeyFrames().size());
  }

  @Test
  void resetStepCountTest() {
    ca.resetStepCount();
    assertEquals(0, ca.getStepCount());
  }

  @Test
  void stepTest() {
    int init = ca.getStepCount();
    ca.step();
    assertEquals(init + 1, ca.getStepCount());
  }

  void startSimulation() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "English");
  }
}
