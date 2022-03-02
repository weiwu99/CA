package cellsociety.view.ui;

import cellsociety.control.Controller;
import cellsociety.control.SimKey;
import cellsociety.view.CA;
import cellsociety.view.View;
import cellsociety.view.cell.CellColorMap;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class RunInfoUITest extends DukeApplicationTest {

  private static final List<SimKey> KEYS = List.of(SimKey.Title, SimKey.Author, SimKey.Description);

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
  void editAuthorTest() {
    String expected = "author";
    SimKey key = SimKey.Author;
    TextField textField = lookup("#RunInfo" + key).query();
    clickOn(textField).write(expected);
    assertEquals(expected, textField.getText());
  }

  void startSimulation() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "English");
  }
}
