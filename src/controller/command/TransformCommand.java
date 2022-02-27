package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;
import model.processes.TransformType;

/**
 * represents the user transforming the current layer's image to be
 * filtered in either sepia or greyscale.
 */
public class TransformCommand implements EnhanceCommandInterface {
  TransformType type;

  /**
   * Constructor of TransformCommands takes in a TransformType.
   * @param type a TransformType
   */
  public TransformCommand(TransformType type) {
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
      case SEPIA: m.transformSepia();
      break;
      case GREYSCALE: m.transformGreyscale();
      break;
      default: throw new IllegalArgumentException("The type provided is not correct.");
    }
  }
}
