package cellsociety;

/**
 * starting class
 *
 * @author David Wu
 * @author Gordon Kim
 * @author Luke Turkovich
 * @author Henry Huynh
 */

import cellsociety.control.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {

  private static final String DEFAULT_LANGUAGE = "English";
  public static final int ROWS = 10;
  public static final int COLS = 10;

  /**
   * A method to test (and a joke :).
   */
  public double getVersion() {
    return 0.001;
  }

  @Override
  public void start(Stage stage) {
    new Controller(ROWS, COLS);
  }

}
