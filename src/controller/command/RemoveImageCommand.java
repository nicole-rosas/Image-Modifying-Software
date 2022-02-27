package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user removing image in a layer.
 */
public class RemoveImageCommand implements EnhanceCommandInterface {

  /**
   * empty constructor for RemoveImageCommand class.
   */
  public RemoveImageCommand() {
    // Does not need content.
  }


  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    m.removeImage();
  }
}
