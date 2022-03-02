package cellsociety.view.cell;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * maps colors to hex values
 *
 * @author Gordon Kim
 */

public class CellColorMap {

  private final static List<Color> DEFAULT_COLORS = List.of(
      Color.web("#F3F4F7"),
      Color.web("#020406"),
      Color.web("#00A5E0"),
      Color.web("#588157")
  );
  private Map<Integer, Color> colorMap;

  public CellColorMap() {
    colorMap = new HashMap<>();
    for (int i = 0; i < DEFAULT_COLORS.size(); i++) {
      colorMap.put(i, DEFAULT_COLORS.get(i));
    }
  }

  public void add(int status, Color color) {
    colorMap.put(status, color);
  }

  public Map<Integer, Color> getColorMap() {
    return colorMap;
  }

  public Color getColor(int status) {
    return colorMap.get(status);
  }
}