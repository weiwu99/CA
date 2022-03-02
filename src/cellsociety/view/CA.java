package cellsociety.view;

import cellsociety.control.CellShape;
import cellsociety.control.Controller;
import cellsociety.view.cell.CellColorMap;
import cellsociety.view.grid.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * view of a particular simulation
 *
 * @author Luke Turkovich
 * @author Gordon Kim
 */

public class CA {

  private static final String DEFAULT_RESOURCE_PACKAGE = "cellsociety.view.resources.";
  private Controller controller;
  private GridView gridView;
  private Timeline animation;
  private CellColorMap colorMap;
  private int stepCount;

  /**
   * default constructor for CA initializes animation, stepCount, and gridView
   */
  public CA(Controller controller) {
    this.controller = controller;
    this.animation = new Timeline();
    this.colorMap = new CellColorMap();
    this.gridView = new GridRectangleView(controller, colorMap);
    this.stepCount = 0;
    startAnimation();
  }

  /**
   * starts animation for the CA
   */
  public void startAnimation() {
    if (animation != null) {
      animation.stop();
    }
    animation.setCycleCount(Animation.INDEFINITE);
    animation.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), e -> controller.step()));
  }

  /**
   * calls controller's step method, updates step count, updates display
   */
  public void step() {
    stepCount++;
    updateGridStatus();
  }

  public CellColorMap getColorMap() {
    return colorMap;
  }

  /**
   * @return current animation (Timeline)
   */
  public Timeline getAnimation() {
    return animation;
  }

  /**
   * @return current stepCount (int)
   */
  public int getStepCount() {
    return stepCount;
  }

  /**
   * @return current gridView (GridView)
   */
  public GridView getGridView() {
    return gridView;
  }

  /**
   * Update Grid Shape and orientation based on user input
   */
  public void updateCellShape() {
    //TODO: use reflection
    switch (controller.getCellShape()) {
      case RECTANGLE -> gridView = new GridRectangleView(controller, colorMap);
      case TRIANGLE -> gridView = new GridTriangleView(controller, colorMap);
      case HEXAGON -> gridView = new GridHexagonView(controller, colorMap);
    }
    updateGridStatus();
  }

  public void updateGridStatus() {
    gridView.updateGridView(controller, colorMap);
  }

  public void resetStepCount() {
    stepCount = 0;
  }
}