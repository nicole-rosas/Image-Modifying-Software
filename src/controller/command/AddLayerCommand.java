package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user adding a layer to the model.
 */
public class AddLayerCommand implements EnhanceCommandInterface {

  private final String name;

  /**
   * constructor for AddLayer that takes in a string that will be the name of the layer.
   *
   * @param name String that will name the layer.
   */
  public AddLayerCommand(String name) {
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
    m.addLayer(name);
  }
}
