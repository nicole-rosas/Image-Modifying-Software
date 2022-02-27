package model;

import java.util.List;
import model.images.image.InterfaceImages;
import model.layers.InterfaceLayers;

/**
 * Interface for MultiLayerModelState that has getter methods that will be used
 * for the view.
 */
public interface MultiLayerModelState extends ImageEnhanceModelInterface {

  @Override
  public InterfaceImages getImage();

  @Override
  public String getName();

  /**
   * returns a list of layers that are in the current state of the image processing file.
   * @return list of layers that were created in the model state.
   */
  public List<InterfaceLayers> getAllLayers();

  /**
   * Returns the layer that is the most visible on the top.
   * @return an InterfaceLayers that is the top most visible.
   */
  public InterfaceLayers getTopMostVisibleLayer();

  /**
   * Returns the image from the top most visible layer.
   * @return an InterfaceImages that is of the top most visible layer.
   */
  public InterfaceImages getTopVisLayerImage();

  /**
   * Returns the current layer the user is working on.
   * @return an InterfaceLayer tha is the current layer the user is on.
   */
  public InterfaceLayers getCurrentLayer();

  /**
   * returns the number of layers currently in the image processing.
   * @return integer that represents the number of layers.
   */
  public int getNumLayers();

  public int getCurrentLayerPos();
}
