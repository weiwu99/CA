package cellsociety.view.ui;

import cellsociety.control.Controller;
import cellsociety.control.SimKey;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.*;

/**
 * UI components for Run Info
 *
 * @author Gordon Kim
 */

public class RunInfoUI extends GridPane {

  private static final List<SimKey> KEYS = List.of(SimKey.Title, SimKey.Author, SimKey.Description);

  private Controller controller;
  private Map<SimKey, TextField> infoMap;
  private ResourceBundle langResource;
  private UIBuilder builder;

  /**
   * creates UI pertaining to current simulation run information
   *
   * @param controller   is the controller that must be maintained throughout
   * @param langResource refers to language document for display
   */
  public RunInfoUI(Controller controller, ResourceBundle langResource) {
    this.controller = controller;
    this.langResource = langResource;
    builder = new UIBuilder(langResource);
    infoMap = new HashMap<>();
    initializeGrid();
  }

  /**
   * Creates grid of Label-TextField for each SimKey using makeRunInfoTextField
   */
  private void initializeGrid() {
    for (int i = 0; i < KEYS.size(); i++) {
      this.add(builder.makeLabel(KEYS.get(i).toString()), 0, i);
      this.add(makeRunInfoTextField(KEYS.get(i)), 1, i);
    }
    this.add(builder.makeButton("saveRunInfo", e -> controller.saveRunInfo()), 0, KEYS.size());
    this.getStyleClass().add("run-info");
  }

  /**
   * On user input, update infoMap to hold new information
   */
  public void updateInfoGrid() {
    for (SimKey key : KEYS) {
      infoMap.get(key).setText(controller.getRunInfo(key));
    }
  }

  /**
   * Creates Label InputField pair for each SimKey into infoMap
   */
  private TextField makeRunInfoTextField(SimKey key) {
    TextField textField = new TextField();
    infoMap.put(key, textField);
    textField.setOnKeyReleased(e -> controller.setRunInfo(key, textField.getText()));
    textField.setId("RunInfo" + key.toString());
    return textField;
  }
}
