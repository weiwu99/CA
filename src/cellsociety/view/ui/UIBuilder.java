package cellsociety.view.ui;

import cellsociety.view.View;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * UI components for Builder
 *
 * @author Gordon Kim
 */

public class UIBuilder {

  private static final String LANGUAGE_PACKAGE = View.class.getPackageName() + ".resources.";
  private ResourceBundle langResource;

  /**
   * UI builder holds helper methods that create UI components
   *
   * @param langResource is the properties file that determines langauge
   */
  public UIBuilder(ResourceBundle langResource) {
    this.langResource = langResource;
  }

  public void setLanguage(String language) {
    this.langResource = ResourceBundle.getBundle(LANGUAGE_PACKAGE + language);
  }

  /**
   * creates a Horizontal configuration of label and node using id
   *
   * @param id   is the label reference
   * @param node is the node to be added with the label
   * @return HBox with label Node pair
   */
  public Node makeHolderBox(String id, Node node) {
    HBox holder = new HBox();
    Label label = new Label(langResource.getString(id));
    holder.getStyleClass().add("holder");
    holder.getChildren().addAll(label, node);
    return holder;
  }

  /**
   * creates a button with proper label and OnAction values
   *
   * @param id       is the name/title of the button
   * @param response is the action event the button should have
   * @return new button with properly initialized
   */
  public Button makeButton(String id, EventHandler<ActionEvent> response) {
    Button button = new Button();
    button.setOnAction(response);
    button.setText(langResource.getString(id));
    button.setId(id);
    button.getStyleClass().add("button");
    return button;
  }

  /**
   * creates a slider
   *
   * @param id       sets id of this node
   * @param min      minimum for the slider
   * @param max      maximum for the slider
   * @param start    initial value of the slider
   * @param response number value of slider
   * @return Node with slider to select value
   */
  public Node makeSlider(String id, double min, double max, double start,
      Consumer<Number> response) {
    Slider slider = new Slider();
    slider.setMin(min);
    slider.setMax(max);
    slider.setValue(start);
    slider.valueProperty().addListener((o, oldValue, newValue) -> response.accept(newValue));
    slider.getStyleClass().add("slider");
    slider.setId(id);
    return makeHolderBox(id, slider);
  }

  /**
   * Creates a ComboBox menu with Label
   *
   * @param id       used as reference for label
   * @param choices  l of possible choices for dropdown menu
   * @param response calls appropriate function through user input
   * @return HolderBox with label ComboBox pair
   */
  public Node makeCombo(String id, List<String> choices, Consumer<String> response) {
    ComboBox<String> options = new ComboBox<>();
    options.getStyleClass().add("choice-box");
    Map<String, String> lang = new HashMap<>();
    for (String option : choices) {
      lang.put(langResource.getString(option), option);
    }
    options.setItems(FXCollections.observableArrayList(lang.keySet().stream().toList()));
//        options.setValue(langResource.getString(choices.get(0)));
    options.valueProperty()
        .addListener((o, oldValue, newValue) -> response.accept(lang.get(newValue)));
    options.setId(id);
    return makeHolderBox(id, options);
  }

  /**
   * Groups buttons together horizontally
   *
   * @param buttons to be lined up horizontally
   * @return Node HBox with horizontally aligned buttons
   */
  public Node makeButtonBox(Node... buttons) {
    HBox hBox = new HBox();
    for (Node button : buttons) {
      hBox.getChildren().add(button);
    }
    hBox.getStyleClass().add("button-box");
    return hBox;
  }

  /**
   * Creates label using id
   *
   * @param id reference to find Label
   * @return appropriate label using id
   */
  public Node makeLabel(String id) {
    Label label = new Label(langResource.getString(id));
    label.setId(id);
    return label;
  }
}