package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user changing the visibility of the given the layer.
 */
public class ChangeVisCommand implements EnhanceCommandInterface {

  private final String name;
  private final String vis;

  /**
   * Constructor for ChangeVisCommand that takes in string and string.
   * @param name name of the layer whose visibility is gonna change.
   * @param vis new state of the layer's visibility.
   */
  public ChangeVisCommand(String name, String vis) {
    if (name == null || vis == null) {
      throw new IllegalArgumentException("Name and visibility settings cannot be null.");
    }

    this.name = name;
    this.vis = vis;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    boolean isVisible;

    if (vis.equalsIgnoreCase("visible")) {
      isVisible = true;
    }
    else if (vis.equalsIgnoreCase("hidden")) {
      isVisible = false;
    }
    else {
      throw new IllegalArgumentException("Visible setting input is incorrect.");
    }

    m.changeVisibility(name, isVisible);
  }
}
