package view;

import java.io.IOException;

/**
 * Interface for the view model of Image Processing.
 * Will output the text view of Image Processing.
 */
public interface InterfaceImageEnhanceView {

  /**
   * Gets the current state of the model. The name of each layer, the visibility of each layer,
   * and the layers itself should be displayed.
   * @throws IOException if the text render fails to get the model.
   */
  public void renderCurrentState() throws IOException;

  /**
   * Prints out the current state of the model and the list of commands available to the user.
   * @param message the text that will be output as text to the user.
   * @throws IOException if the text render fails to read message.
   */
  public void renderMessage(String message) throws IOException;
}
