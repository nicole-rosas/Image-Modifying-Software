package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents user undoing a move and returning the previous layers state.
 */
public class UndoCommand implements EnhanceCommandInterface {

  /**
   * empty constructor for UndoCommand, does not take in anything.
   */
  public UndoCommand() {
    // Does not need comment.
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    m.undo();
  }
}
