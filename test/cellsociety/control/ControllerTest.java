package cellsociety.control;


import cellsociety.Main;
import cellsociety.model.Cell;
import cellsociety.model.simulations.CAModel;
import cellsociety.model.simulations.GameOfLifeModel;
import cellsociety.view.View;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest extends DukeApplicationTest {

  private static final String BASE_PATH = "/data/";

  public static final int ROWS = 10;
  public static final int COLS = 10;
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
  private CAModel model;

  @Override
  public void start(Stage stage) {
    controller = new Controller(ROWS, COLS);
    runInfo = new RunInfoController();
    errorHandler = new ErrorHandler();
    simulation = DEFAULT_SIMULATION;
    model = new GameOfLifeModel(ROWS, COLS, DEFAULT_CELL_SHAPE, DEFAULT_NEIGHBOR_POLICY,
        DEFAULT_EDGE_POLICY);
    view = new View(controller);
  }

  @Test
  void testConstructor() {
    assertNotNull(runInfo);
    assertNotNull(errorHandler);
    assertNotNull(simulation);
    assertNotNull(model);
    assertNotNull(view);
  }

  @Test
  void testStep() {
    int[][] myOldGrid = controller.getStatusGrid();
    GameOfLifeModel model2 = new GameOfLifeModel(ROWS, COLS, DEFAULT_CELL_SHAPE,
        DEFAULT_NEIGHBOR_POLICY, DEFAULT_EDGE_POLICY);
    model2.updateCellGrid(myOldGrid);
    controller.step();
    model2.step();
    Cell[][] cells = model2.getCellGrid();
    int[][] model2_statusGrid = new int[cells.length][cells[0].length];
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        model2_statusGrid[i][j] = cells[i][j].getMyStatus();
      }
    }
    assertArrayEquals(controller.getStatusGrid(), model2_statusGrid);
  }

  @Test
  void testGetStatusGrid() {
    int[][] myStatusGrid = controller.getStatusGrid();

    Cell[][] cells = model.getCellGrid();
    int[][] statusGrid = new int[cells.length][cells[0].length];
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        statusGrid[i][j] = cells[i][j].getMyStatus();
      }
    }
    assertArrayEquals(myStatusGrid, statusGrid);
  }

  @Test
  void testInitializeRandomGrid() {
    Cell[][] cellGrid1 = model.getCellGrid();
    controller.initializeRandomGrid();
    model.updateCellGrid(controller.getStatusGrid());
    Cell[][] cellGrid2 = model.getCellGrid();
    assertNotEquals(cellGrid1, cellGrid2);
  }

  @Test
  void testUpdateNeighbors() {
    model.updateNeighborPolicy(NeighborPolicy.VERTEX);
    assertEquals(model.getNeighborPolicy(), NeighborPolicy.VERTEX);
  }

  @Test
  void testUpdateEdgePolicy() {
    model.updateEdgePolicy(EdgePolicy.RANDOM);
    assertEquals(model.getEdgePolicy(), EdgePolicy.RANDOM);
  }

  @Test
  void testGetUpdateCellShape() {
    model.updateCellShape(CellShape.HEXAGON);
    assertEquals(model.getCellShape(), CellShape.HEXAGON);
    model.updateCellShape(CellShape.TRIANGLE);
    assertEquals(model.getCellShape(), CellShape.TRIANGLE);
  }

  @Test
  void testUpdateModel() {
    Controller c1 = new Controller(ROWS, COLS);
    Controller c2 = new Controller(ROWS, COLS);
    c1.updateModel(Simulation.PERCOLATION);
    c2.updateModel(Simulation.PERCOLATION);
    assertEquals(c1, c2);
    c1.updateModel(Simulation.GAMEOFLIFE);
    c2.updateModel(Simulation.WATOR);
    assertNotEquals(c1, c2);
  }

  @Test
  void testReset() {
    controller = new Controller(ROWS, COLS);
    controller.reset();
    int[][] initialStatus = controller.getStatusGrid();
    controller.reset();
    int[][] newStatus = controller.getStatusGrid();
    assertEquals(initialStatus, newStatus);
  }

  @Test
  void testGetSetRunInfo() {
    controller.setRunInfo(SimKey.Type, "TestType");
    assertEquals(controller.getRunInfo(SimKey.Type), "TestType");
    controller.setRunInfo(SimKey.Author, "Luke Turkovich");
    assertEquals(controller.getRunInfo(SimKey.Author), "Luke Turkovich");
  }

  @Test
  void testUploadConfiguration() {
    Controller c = new Controller(ROWS, COLS);
    assertDoesNotThrow(() -> c.uploadConfiguration());
  }

  @Test
  void testInitializeGridFromFileBlinkers() {
    Controller c = new Controller(ROWS, COLS);
    assertDoesNotThrow(
        () -> c.initializeGridFromFile(new File(BASE_PATH + "game_of_life/blinkers.csv")));
    c.initializeGridFromFile(new File(BASE_PATH + "game_of_life/blinkers.csv"));
    int[][] blinkers = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    assertArrayEquals(blinkers, c.getStatusGrid());
  }

  @Test
  void testInitializeGridFromFileGlider() {
    Controller c = new Controller(ROWS, COLS);
    assertDoesNotThrow(
        () -> c.initializeGridFromFile(new File(BASE_PATH + "game_of_life/glider.csv")));
    c.initializeGridFromFile(new File(BASE_PATH + "game_of_life/glider.csv"));
    int[][] glider = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        , {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    assertArrayEquals(glider, c.getStatusGrid());
  }

  @Test
  void testSaveRunInfo() {
    assertDoesNotThrow(
        () -> runInfo.saveRunInfo(Simulation.SEGREGATION, controller.getStatusGrid()));
    assertEquals(runInfo.getInfo(SimKey.Type), Simulation.SEGREGATION);
  }

  @Test
  void testPause() {
    assertDoesNotThrow(() -> controller.pause());
  }

  @Test
  void testPlay() {
    assertDoesNotThrow(() -> controller.play());
  }

  @Test
  void testSetAnimationRate() {
    assertDoesNotThrow(() -> controller.setAnimationRate(5.0));
    assertDoesNotThrow(() -> controller.setAnimationRate(-5.0));
  }

  @Test
  void testChangeTheme() {
    assertDoesNotThrow(() -> controller.changeTheme("Duke"));
  }

  @Test
  void testIncrementCellStatus() {
    Controller c = new Controller(ROWS, COLS);
    int[][] initialStatus = c.getStatusGrid();
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        controller.incrementCellStatus(i, j);
      }
    }
    int[][] newStatus = c.getStatusGrid();
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        assertEquals(initialStatus[i][j] + 1, newStatus[i][j]);
      }
    }
  }

  @Test
  void testChangeLanguage() {
    assertDoesNotThrow(() -> controller.changeLanguage("English"));
  }

}
