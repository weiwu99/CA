package cellsociety.control;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class RunInfoControllerTest extends DukeApplicationTest {


  private static final String BASE_PATH = "/data/";

  private Controller controller;
  private RunInfoController r;

  @Override
  public void start(Stage stage) {
    controller = new Controller(10, 10);
    r = new RunInfoController();
  }

  @Test
  void testConstructor() {
    assertNotNull(r);
  }

  @Test
  void testGetSet() {
    r.set(SimKey.Type, "Percolation");
    assertEquals(r.getInfo(SimKey.Type), "Percolation");
    r.set(SimKey.Author, "Luke Turkovich");
    assertEquals(r.getInfo(SimKey.Author), "Luke Turkovich");
  }

  @Test
  void testSetSimSettings() {
    assertDoesNotThrow(() -> r.setSimSettings(new File(BASE_PATH + "game_of_life/blinkers.sim")));
    assertEquals(r.getInfo(SimKey.Type), "GameOfLife");
    assertEquals(r.getInfo(SimKey.Author), "John Conway");
  }

  @Test
  void testGetCSVFile() {
    assertNotNull(r.getCSVFile());
  }

  @Test
  void testSaveRunInfo() {
    assertDoesNotThrow(() -> r.saveRunInfo(Simulation.PERCOLATION, controller.getStatusGrid()));
  }
}
