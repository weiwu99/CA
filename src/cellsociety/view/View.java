package cellsociety.view;

import cellsociety.control.Controller;
import cellsociety.view.ui.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * high level display class that handles most of front end output
 *
 * @author Gordon Kim
 * @author Luke Turkovich
 */

public class View {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      View.class.getPackageName() + ".resources.";
  private static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String DEFAULT_STYLSHEET = STYLE_PACKAGE + "style.css";
  private static final String LIGHT_MODE = "LightMode";
  private static final String DARK_MODE = "DarkMode";
  private static final String DUKE_MODE = "DukeMode";
  private static final String LIGHT_MODE_STYLESHEET = STYLE_PACKAGE + "lightMode.css";
  private static final String DARK_MODE_STYLESHEET = STYLE_PACKAGE + "darkMode.css";
  private static final String DUKE_MODE_STYLESHEET = STYLE_PACKAGE + "dukeMode.css";
  private static final List<String> STYLESHEET_LIST = List.of(LIGHT_MODE, DARK_MODE, DUKE_MODE);
  private static final Map<String, String> STYLESHEETS = Map.of(
      LIGHT_MODE, LIGHT_MODE_STYLESHEET,
      DARK_MODE, DARK_MODE_STYLESHEET,
      DUKE_MODE, DUKE_MODE_STYLESHEET
  );
  private static final List<String> LANGUAGES_LIST = List.of("English", "Spanish", "French");
  private static final String ERROR_THEME_CHANGE = "Error modifying some colors of theme";
  private final String STEP = "step";

  private final String CELL_SOCIETY_ERROR = "Cell Society Error";
  private final int WIDTH = 1200;
  private final int HEIGHT = 900;

  private Controller controller;
  private GridPane root;
  private Scene scene;
  private ProgramUI programUI;
  private ColorMapUI colorMapUI;
  private WindowUI windowUI;
  private RunInfoUI runInfoView;
  private CA ca;
  private Label stepCountLabel;
  private ResourceBundle languageResource;
  private Stage stage;

  /**
   * creates a new view
   *
   * @param controller is controller which must be maintained throughout all runtime
   */
  public View(Controller controller) {
    languageResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
    this.controller = controller;
    ca = new CA(controller);
    stage = new Stage();
    createLanguageWindow();
  }

  private void createLanguageWindow() {
    Scene languageScene = new Scene(new LanguageUI(controller, languageResource, LANGUAGES_LIST),
        WIDTH, HEIGHT);
    stage.setScene(languageScene);
    stage.show();
  }

  /**
   * creates basic UI for all CA simulations
   *
   * @return scene with properly setup UI, grid, and buttons
   */
  private Scene setUpDisplay() {
    root = new GridPane();
    root.getStyleClass().add("container");

    stepCountLabel = new Label(languageResource.getString(STEP) + ": 0");
    programUI = new ProgramUI(controller, languageResource);
    colorMapUI = new ColorMapUI(ca.getColorMap(), languageResource);
    windowUI = new WindowUI(controller, STYLESHEET_LIST, languageResource);
    runInfoView = new RunInfoUI(controller, languageResource);

    root.add(ca.getGridView(), 0, 0, 1, 6);
    root.add(windowUI, 1, 1);
    root.add(stepCountLabel, 1, 2);
    root.add(programUI, 1, 3);
    root.add(colorMapUI, 1, 4);
    root.add(runInfoView, 1, 5);

    scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLSHEET).toExternalForm());
    scene.getStylesheets().add(getClass().getResource(LIGHT_MODE_STYLESHEET).toExternalForm());
    return scene;
  }

  /**
   * increments step count by one, updates label
   */
  public void step() {
    ca.step();
    updateStepCountLabel();
  }

  public void pause() {
    ca.getAnimation().pause();
  }

  public void play() {
    ca.getAnimation().play();
  }

  public void setAnimationRate(double rate) {
    ca.getAnimation().setRate(rate);
  }

  public void reset() {
    ca.updateGridStatus();
    ca.resetStepCount();
    updateStepCountLabel();
  }

  private void createWindow() {
    stage.setScene(setUpDisplay());
    stage.show();
  }

  /**
   * pops up an alert to notify user of error
   *
   * @param message displays this specific error that occurred
   */
  public void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(CELL_SOCIETY_ERROR);
    alert.setContentText(message);
    alert.showAndWait();
    //alert.setId(message);
  }

  public void updateGridStatus() {
    ca.updateGridStatus();
  }

  public void updateCellShape() {
    root.getChildren().remove(ca.getGridView());
    ca.updateCellShape();
    root.add(ca.getGridView(), 0, 0, 1, 1000);
  }

  public void updateRunInfo() {
    runInfoView.updateInfoGrid();
  }

  public void changeTheme(String theme) {
    try {
      scene.getStylesheets().remove(1);
      scene.getStylesheets().add(getClass().getResource(STYLESHEETS.get(theme)).toExternalForm());
    } catch (Exception e) {
      showError(ERROR_THEME_CHANGE);
    }
  }

  private void updateStepCountLabel() {
    stepCountLabel = new Label(languageResource.getString(STEP) + ": " + ca.getStepCount());
  }

  public void changeLanguage(String language) {
    languageResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    createWindow();
  }

  public CA getCA() {
    return ca;
  }
}