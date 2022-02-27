package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user deleting the layer in the model.
 */
public class DeleteLayerCommand implements EnhanceCommandInterface {

  private final String name;

  /**
   * Constructor for DeleteLayerCommand that takes in a string.
   * @param name String which is the name of the layer to be deleted.
   */
  public DeleteLayerCommand(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name provided cannot be null.");
    }
    this.name = name;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    m.removeLayer(name);
  }
}
