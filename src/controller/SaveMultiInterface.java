package controller;

import java.util.List;
import model.layers.InterfaceLayers;

/**
 * Interface to save layers from model.
 */
public interface SaveMultiInterface {

  /**
   * saves the list of layers with the string which saves the location of the file so that
   * it keeps the order of layers intact.
   * @param loLayers list of InterfaceLayers to be saved
   * @param locationName string of a file that stores the location of the file.
   */
  public void saveMultiLayersImage(List<InterfaceLayers> loLayers, String locationName);
}
