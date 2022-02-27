package controller.command;

import controller.ChangeType;
import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user changing either the name or the current layer.
 */
public class ChangeCurAndNameCommands implements EnhanceCommandInterface {

  private final ChangeType type;
  private final String input;

  /**
   * Constructor for ChangeCurAndNameCommands that takes in a ChangeType and String.
   *
   * @param type  Changetype that determines if the name of layer or current layer will be changed.
   * @param input will either be new name of layer or the new current layer's name.
   */
  public ChangeCurAndNameCommands(ChangeType type, String input) {
    if (type == null || input == null) {
      throw new IllegalArgumentException("Type and strings provided cannot be null.");
    }
    this.type = type;
    this.input = input;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    switch (type) {
      case CURRENT:
        m.changeCurrentLayer(input);
        break;
      case NAME:
        m.setName(input);
        break;
      default:
        throw new IllegalArgumentException("Not a valid type.");
    }
  }
}
