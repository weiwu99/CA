package cellsociety.view.ui;

import cellsociety.view.cell.CellColorMap;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * UI components for ColorMap
 *
 * @author Gordon Kim
 */

public class ColorMapUI extends GridPane {

  private final String STATUS = "status";

  /**
   * creates UI pertaining to cell color
   *
   * @param colorMap     is used to alter cell Colors during simulation
   * @param langResource refers to language document for display
   */
  public ColorMapUI(CellColorMap colorMap, ResourceBundle langResource) {
    Label statusLabel = new Label(langResource.getString(STATUS));
    statusLabel.setId(STATUS);
    statusLabel.getStyleClass().add("label");
    this.add(statusLabel, 0, 0);
    for (int i = 0; i < 3; i++) {
      int finalI = i;
      this.add(new Label(Integer.toString(i)), 0, i + 1);
      this.add(makeColorPicker(i, colorMap.getColor(i), e -> colorMap.add(finalI, e)), 1, i + 1);
    }

    this.getStyleClass().add("vbox");
  }

  /**
   * Creates color picker that determines color for cell status
   *
   * @param id       is the color picker id
   * @param color    is the default color
   * @param response updates CellColorMap on user input
   * @return color picker
   */
  private Node makeColorPicker(int id, Color color, Consumer<Color> response) {
    ColorPicker cp = new ColorPicker(color);
    cp.setOnAction(e -> response.accept(cp.getValue()));
    cp.getStyleClass().add("color-picker");
    cp.setId("color-picker" + id);
    return cp;
  }
}
