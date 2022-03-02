package cellsociety.control;

import cellsociety.view.View;

/**
 * handles all errors in controller by calling showError method with appropriate message
 *
 * @author Gordon Kim
 */
public class ErrorHandler {

  private View view;

  public void handleError(String message) {
    view.showError(message);
  }
}
