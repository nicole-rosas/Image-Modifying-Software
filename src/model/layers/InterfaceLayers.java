package model.layers;

import model.images.image.InterfaceImages;

/**
 * Interface for multiple layers in Image Processing MultiLayeredModel.
 */
public interface InterfaceLayers {

  /**
   * Given a string name, sets the name of this layer.
   * @param name string name to be set.
   * @throws IllegalArgumentException if name is null, or empty string.
   */
  public void setName(String name);

  /**
   * Set the visibility of the layer.
   * @param v boolean value representing the layer. True if the layer is visible, false if it
   *          is not.
   */
  public void setVisibility(boolean v);

  /**
   * Given an image, set the given image to the object's image. We will deep copy the image
   * given to us, to avoid mutation.
   * @param image The image to be deep copied and set.
   * @throws IllegalArgumentException if the image given is null.
   */
  public void setImageInLayer(InterfaceImages image);

  /**
   * gets the name of the layer.
   * @return String that represents the name.
   */
  public String getLayerName();

  /**
   * gets the visibility status of the layer.
   * @return if visible or not.
   */
  public boolean getVisibilityStatus();

  /**
   * gets the image in the layer.
   * @return InterfaceImages from the layer
   */
  public InterfaceImages getLayerImage();

  /**
   * deep copies the layer and returns it as a new layer.
   * @return a copied layer
   */
  public InterfaceLayers getDeepCopy();

  @Override
  public boolean equals(Object c);

  @Override
  public int hashCode();

  public boolean isImageEmpty();
}
