package controller.command;

import controller.command.EnhanceCommandInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents switching a layer with given name to a given place.
 */
public class SwitchLayerPosCommand implements EnhanceCommandInterface {

  private final String name;
  private final int place;

  /**
   * Constructor of SwitchLayerPosCommand and takes in a String and int.
   * @param name String name of the file
   * @param place where the layer will be moved.
   */
  public SwitchLayerPosCommand(String name, int place) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }

    this.name = name;
    this.place = place;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }
    m.switchLayerPosition(name, place);
  }
}
