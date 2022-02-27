package model;

import java.util.List;
import model.layers.InterfaceLayers;

/**
 * Interface of multi layered processing file that will be imported and edited.
 */
public interface MultiLayeredEnhanceInterface extends ImageEnhanceModelInterface,
    MultiLayerModelState {

  /**
   * Takes in the given list of layers and deep copies them to be in the current model.
   * @param loLayers list of layers to be used.
   */
  public void setIntoLayers(List<InterfaceLayers> loLayers);

  /**
   * adds a new empty layer as the topmost layer with the name as the given string.
   * @param name String that will be name of the new layer.
   */
  public void addLayer(String name);

  /**
   * deletes the layer with the given name from the multi layered processing file.
   * @param name String of the layer that will be removed.
   */
  public void removeLayer(String name);

  /**
   * makes the layer with the given name the current layer to be edited and filtered.
   * @param name String of the layer that will be currently changed.
   */
  public void changeCurrentLayer(String name);

  /**
   * Changes the visibility of the layer with the given String to the given boolean.
   * @param name String of the layer whose visibility will change.
   * @param visible decided whether the layer will be visible or not.
   */
  public void changeVisibility(String name, boolean visible);

  /**
   * changes the position of the given layer to the given integer.
   * @param name String of the layer name
   * @param place new place of where the image
   */
  public void switchLayerPosition(String name, int place);

  /**
   * undoes the previous change to the model and returns the old state of the model.
   */
  public void undo();

  /**
   * removes the current image in the layer.
   */
  public void removeImage();
}
