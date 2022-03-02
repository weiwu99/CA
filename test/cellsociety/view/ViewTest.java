
package cellsociety.view;

import cellsociety.control.*;
import cellsociety.model.simulations.GameOfLifeModel;
//import cellsociety.view.programView.GameOfLifeView;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.cell.CellRectangleView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest extends DukeApplicationTest {

  private static final String TITLE = "Game Of Life";
  private GameOfLifeModel model;
  public static final int ROWS = 5;
  public static final int COLS = 5;
  private static final Simulation DEFAULT_SIMULATION = Simulation.GAMEOFLIFE;
  private static final CellShape DEFAULT_CELL_SHAPE = CellShape.RECTANGLE;
  private static final EdgePolicy DEFAULT_EDGE_POLICY = EdgePolicy.FINITE;
  private static final NeighborPolicy DEFAULT_NEIGHBOR_POLICY = NeighborPolicy.COMPLETE;
  private static final String DEFAULT_LANGUAGE = "English";
  private RunInfoController runInfo;
  private Simulation simulation;
  private ErrorHandler errorHandler;
  private View view;
  private Controller controller;
  private CA ca;
  private CellColorMap colorMap;

  @Override
  public void start(Stage stage) {
    controller = new Controller(ROWS, COLS);
    runInfo = new RunInfoController();
    errorHandler = new ErrorHandler();
    simulation = DEFAULT_SIMULATION;
    view = controller.getView();
    ca = view.getCA();
    colorMap = ca.getColorMap();
    startSimulation();
  }


  @Test
    // Tests that cell can color from white to black
  void userColorChangeTo0() {
    CellRectangleView cell = lookup("#0,0").query();
    clickOn(cell);
    clickOn(cell);
    assertEquals(colorMap.getColor(0), cell.getColor());
  }

  @Test
    // Tests that all cells can change color from black to white
  void userColorChangeTo1() {
    outerLoop:
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        CellRectangleView cell = lookup("#" + i + "," + j).query();
        cell.setColor(1, colorMap); // black
        clickOn(cell);
        assertEquals(colorMap.getColor(1), cell.getColor());
        break outerLoop;
      }
    }
  }

  @Test
    // Test that randomConfig works
  void randomConfigTest() {
    Button button = lookup("#randomConfiguration").query();
    clickOn(button);
    CellRectangleView cell = lookup("#" + 0 + "," + 0).query();
    assertEquals(cell.getColor(), colorMap.getColor(controller.getStatusGrid()[0][0]));
  }

  @Test
    // Test that we can change to Percolation and change to third state (percolated)
  void userColorChangeToBlue() {
    ComboBox<String> options = lookup(
        "#simulation").query();// FIXME: Change the simulation here not working
    String expected = "Percolation";
    select(options, expected);

    outerLoop:

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        CellRectangleView cell = lookup("#" + i + "," + j).query();
        cell.setColor(1, colorMap);
        clickOn(cell); // should
        assertEquals(colorMap.getColor(2), cell.getColor());
        break outerLoop;
      }
    }
  }

  @Test
    // Test that we can change to Percolation and change to third state (percolated)
  void userColorChangeFromBlueToWhite() {
    ComboBox<String> options = lookup(
        "#simulation").query();// FIXME: Change the simulation here not working
    String expected = "Percolation";
    select(options, expected);

    outerLoop:

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        CellRectangleView cell = lookup("#" + i + "," + j).query();
        cell.setColor(2, colorMap);
        clickOn(cell); // should
        assertEquals(colorMap.getColor(0), cell.getColor());
        break outerLoop;
      }
    }
  }

  void startSimulation() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "English");
  }
}
