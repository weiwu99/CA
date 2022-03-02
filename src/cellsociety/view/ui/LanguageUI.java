package cellsociety.view.ui;

import cellsociety.control.Controller;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ResourceBundle;

/**
 * UI components for Language
 *
 * @author Gordon Kim
 */

public class LanguageUI extends VBox {

  private final String LANGUAGE = "language";

  /**
   * Creates initial popup where user chooses language
   *
   * @param controller   handles new Language input and passes it to view
   * @param langResource refers to language document for display
   * @param languages    List of available languages
   */
  public LanguageUI(Controller controller, ResourceBundle langResource, List<String> languages) {
    UIBuilder builder = new UIBuilder(langResource);
    this.getChildren().add(
        builder.makeCombo(LANGUAGE, languages, e -> changeLanguage(controller, e))
    );
  }

  /**
   * Clears language Dropdown and initializes view with new language
   *
   * @param controller handles new Language input and passes it to view
   * @param language   is user chosen language
   */
  private void changeLanguage(Controller controller, String language) {
    this.getChildren().clear();
    controller.changeLanguage(language);
  }
}
