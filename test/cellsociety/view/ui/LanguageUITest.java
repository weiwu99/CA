package cellsociety.view.ui;

import cellsociety.control.Controller;
import cellsociety.view.CA;
import cellsociety.view.View;
import cellsociety.view.cell.CellColorMap;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;


public class LanguageUITest extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {

  }

  @Test
  void chooseEnglishTest() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "English");

    Button playButton = lookup("#play").queryButton();
    String expected = "Play";
    assertEquals(expected, playButton.getText());
  }

  @Test
  void chooseFrenchTest() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "French");

    Button playButton = lookup("#play").queryButton();
    String expected = "Jouer";
    assertEquals(expected, playButton.getText());
  }

  @Test
  void chooseSpanishTest() {
    ComboBox<String> options = lookup("#language").query();
    clickOn(options);
    select(options, "Spanish");

    Button playButton = lookup("#play").queryButton();
    String expected = "Jugar";
    assertEquals(expected, playButton.getText());
  }
}
