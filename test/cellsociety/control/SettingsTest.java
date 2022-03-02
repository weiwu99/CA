package cellsociety.control;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsTest extends DukeApplicationTest {


  private static final String BASE_PATH = "/data/";

  private Controller controller;
  private Settings s;

  @Override
  public void start(Stage stage) {
    controller = new Controller(10, 10);
    s = new Settings();
  }

  @Test
  void testConstructor() {
    assertNotNull(s);
  }

  @Test
  void testReadAllDataCSV() {
    assertDoesNotThrow(() -> s.readAllDataCSV(new File(BASE_PATH + "game_of_life/blinkers.csv")));
    assertNotNull(s.readAllDataCSV(new File(BASE_PATH + "game_of_life/blinkers.csv")));
  }

  @Test
  void testCreateFile() {
    assertDoesNotThrow(() -> s.createFile(Simulation.WATOR, "WaTorFile", ".csv"));
    assertNotNull(s.createFile(Simulation.WATOR, "WaTorFile", ".csv"));
  }

  @Test
  void testDataToSim() {
    assertDoesNotThrow(() -> s.writeDataToSim(new File("test"), "cellsociety"));
  }

  @Test
  void testWriteDatatoCSV() {
    assertDoesNotThrow(() -> s.writeDataToCSV(new File("output"), controller.getStatusGrid()));
  }
}
