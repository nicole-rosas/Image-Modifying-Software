package controller;

import controller.command.EnhanceCommandInterface;
import controller.LoadIm2dMatrix;
import controller.LoadMultiInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user loading an already made layer in to a model.
 */
public class LoadLayerCommand implements EnhanceCommandInterface {
  private final String filename;

  /**
   * constructor for LoadLayerCommand that takes in a string.
   * @param filename will be the file name in model.
   */
  public LoadLayerCommand(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("File name provided cannot be null.");
    }

    this.filename = filename;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model given cannot be null.");
    }

    LoadMultiInterface loadModel = new LoadIm2dMatrix();
    m.setIntoLayers(loadModel.importLayer(filename));
  }
}
