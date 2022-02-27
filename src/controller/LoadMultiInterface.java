package controller;

import java.util.List;
import model.layers.InterfaceLayers;

/**
 * Loads a multiple layered image process from a file to the model.
 */
public interface LoadMultiInterface {

  /**
   * imports the multiple layers from the file with a given filename.
   * @param filename String name of the file with the layers
   * @return list of layers saved in the file.
   */
  public List<InterfaceLayers> importLayer(String filename);
}
