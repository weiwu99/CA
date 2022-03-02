package cellsociety.view.ui;

import cellsociety.control.*;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ResourceBundle;

/**
 * UI components for Program
 *
 * @author Gordon Kim
 */

public class ProgramUI extends VBox {

  private final List<String> CELL_SHAPES_LIST = List.of("Rectangle", "Triangle", "Hexagon");
  private final List<String> NEIGHBOR_POLICY_LIST = List.of("Complete", "Cardinal", "Vertex");
  private final List<String> EDGE_POLICY_LIST = List.of("Finite", "Toroidal", "Random");
  private final List<String> SIMULATIONS_LIST = List.of(
      "GAMEOFLIFE",
      "SPREADOFFIRE",
      "SEGREGATION",
      "WATOR",
      "PERCOLATION");
  private final String PAUSE = "pause";
  private final String PLAY = "play";
  private final String STEP = "step";
  private final String RESET = "reset";
  private final String UPLOAD_CONFIGURATION = "uploadConfiguration";
  private final String RANDOM_CONFIGURATION = "randomConfiguration";
  private final String ANIMATION_SPEED = "animationSpeed";
  private final String NEIGHBORS = "neighbors";
  private final String GRID_SHAPES = "gridShapes";
  private final String EDGE_POLICIES = "edgePolicy";
  private final String SIMULATIONS = "simulation";

  private UIBuilder builder;

  /**
   * creates UI pertaining to running program
   *
   * @param controller   is the controller that must be maintained throughout
   * @param langResource refers to language document for display
   */
  public ProgramUI(Controller controller, ResourceBundle langResource) {
    builder = new UIBuilder(langResource);

    createAnimationButtons(controller);
    createGridUI(controller);

    this.getStyleClass().add("vbox");
  }

  /**
   * Creates Button box and slider that manipulates the animation through controller
   *
   * @param controller to adjust animation
   */
  private void createAnimationButtons(Controller controller) {
    this.getChildren().add(
        builder.makeButtonBox(
            builder.makeButton(PAUSE, e -> controller.pause()),
            builder.makeButton(PLAY, e -> controller.play()),
            builder.makeButton(STEP, e -> controller.step()),
            builder.makeButton(RESET, e -> controller.reset())
        )
    );
    this.getChildren().add(builder.makeSlider(ANIMATION_SPEED, 0, 5, 1,
        e -> controller.setAnimationRate(e.doubleValue())));
  }

  /**
   * Creates Button box to configure grid and dropdown menus for simulation type, cell
   * shapes,neighbor policy, edge policy
   *
   * @param controller to adjust animation
   */
  private void createGridUI(Controller controller) {
    this.getChildren().addAll(
        builder.makeButtonBox(
            builder.makeButton(UPLOAD_CONFIGURATION, e -> controller.uploadConfiguration()),
            builder.makeButton(RANDOM_CONFIGURATION, e -> controller.initializeRandomGrid())
        ),
        builder.makeCombo(SIMULATIONS, SIMULATIONS_LIST,
            e -> controller.updateModel(Enum.valueOf(Simulation.class, e.toUpperCase()))),
        builder.makeCombo(GRID_SHAPES, CELL_SHAPES_LIST,
            e -> controller.updateCellShape(Enum.valueOf(CellShape.class, e.toUpperCase()))),
        builder.makeCombo(NEIGHBORS, NEIGHBOR_POLICY_LIST,
            e -> controller.updateNeighbors(Enum.valueOf(NeighborPolicy.class, e.toUpperCase()))),
        builder.makeCombo(EDGE_POLICIES, EDGE_POLICY_LIST,
            e -> controller.updateEdgePolicy(Enum.valueOf(EdgePolicy.class, e.toUpperCase())))
    );
  }
}
