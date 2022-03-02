package cellsociety.control;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class CSVContainerTest extends DukeApplicationTest {

  private Controller controller;
  private CSVContainer c;

  @Override
  public void start(Stage stage) {
    controller = new Controller(10, 10);
    int[][] status = controller.getStatusGrid();
    c = new CSVContainer(status.length, status[0].length, status);
  }

  @Test
  void testConstructor() {
    assertNotNull(c);
  }

  @Test
  void testGetCSVStatus() {
    assertNotNull(c.getCSVStatus());
  }
}
