package controller.command;

import model.MultiLayeredEnhanceInterface;
import model.processes.KernelType;

/**
 * Represents the user filtering a layer's image for either blur or sharpen.
 */
public class FilterCommand implements EnhanceCommandInterface {
  KernelType type;

  /**
   * Constructor for FilterCommand that takes in a KernelType that will determine what filter
   * to be used.
   * @param type KernelType that will filter the image.
   */
  public FilterCommand(KernelType type) {
    if (type == null) {
      throw new IllegalArgumentException("Type provided cannot be null.");
    }

    this.type = type;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    switch (type) {
      case BLUR:
        m.convolutionBlur();
        break;
      case SHARPEN:
        m.convolutionSharpen();
        break;
      default:
        throw new IllegalArgumentException("The type provided is not correct.");
    }
  }
}
