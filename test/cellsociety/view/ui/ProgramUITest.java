package cellsociety.view.ui;

import cellsociety.control.Controller;
import cellsociety.view.CA;
import cellsociety.view.View;
import cellsociety.view.cell.CellColorMap;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


public class ProgramUITest extends DukeApplicationTest {

  private final String PAUSE = "pause";
  private final String PLAY = "play";
  private final String STEP = "step";
  private final String RESET = "reset";
  private final String UPLOAD_CONFIGURATION = "uploadConfiguration";
  private final String RANDOM_CONFIGURATION = "randomConfiguration";
  private final String ANIMATION_SPEED = "animationSpeed";

  Controller controller;
  CA ca;
  View view;
  CellColorMap cellColorMap;

  @Override
  public void start(Stage stage) {
    controller = new Controller(10, 10);
    view = controller.getView();
    ca = view.getCA();
    cellColorMap = ca.getColorMap();
    startSimulation();
  }

  @Test
  void pauseButtonTest() {
    ca.getAnimation().play();
    Button pauseButton = lookup("#" + PAUSE).queryButton();
    clickOn(pauseButton);
    Animation.Status expected = Animation.Status.PAUSED;
    assertEquals(expected, ca.getAnimation().getStatus());
  }

  @Test
  void playButtonTest() {
    ca.getAnimation().pause();
    Button playButton = lookup("#" + PLAY).queryButton();
    clickOn(playButton);
    Animation.Status expected = Animation.Status.RUNNING;
    assertEquals(expected, ca.getAnimation().getStatus());
  }

  @Test
  void stepButtonTest() {
    int initialStep = ca.getStepCount();
    Button stepButton = lookup("#" + STEP).queryButton();
    clickOn(stepButton);
    int expected = initialStep + 1;
    assertEquals(expected, ca.getStepCount());
  }

  @Test
  void resetButtonTest() {
    Button resetButton = lookup("#" + RESET).queryButton();
    clickOn(resetButton);
    assertEquals(0, ca.getStepCount());
  }

  void startSimulation() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "English");
  }
}
