package cellsociety.view.ui;

import cellsociety.Main;
import cellsociety.control.Controller;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.ResourceBundle;

/**
 * UI components for Window
 *
 * @author Gordon Kim
 */

public class WindowUI extends VBox {

  private final String NEW_SIMULATION = "newSimulation";
  private final String THEME = "theme";

  UIBuilder builder;

  /**
   * creates UI pertaining to window separate from running program
   *
   * @param controller   is the controller that must be maintained throughout
   * @param stylesheets  is list of reference to them stylesheets
   * @param langResource refers to language document for display
   */
  public WindowUI(Controller controller, List<String> stylesheets, ResourceBundle langResource) {
    builder = new UIBuilder(langResource);
    createWindowUI(controller, stylesheets);
  }

  private void createWindowUI(Controller controller, List<String> stylesheets) {
    this.getChildren().addAll(
        builder.makeCombo(THEME, stylesheets, e -> controller.changeTheme(e)),
        builder.makeButton(NEW_SIMULATION, e -> createNewSimulation())
    );
    this.getStyleClass().add("vbox");
  }

  /**
   * creates new simulation in a new window
   */
  private void createNewSimulation() {
    new Controller(Main.ROWS, Main.COLS);
  }
}
