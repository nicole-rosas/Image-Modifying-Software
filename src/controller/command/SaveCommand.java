package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.SaveIm2DMatrix;
import controller.SaveMultiInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents saving the model as a command for the user to use.
 */
public class SaveCommand implements EnhanceCommandInterface {

  private final String location;

  /**
   * constructor for SaveCommand that takes in a String Location.
   * @param location location of where the image file/layer is saved.
   */
  public SaveCommand(String location) {
    if (location == null) {
      throw new IllegalArgumentException("Location provided cannot be null.");
    }

    this.location = location;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    SaveMultiInterface saveModel = new SaveIm2DMatrix();
    saveModel.saveMultiLayersImage(m.getAllLayers(), location);
  }
}
