package controller;

import model.images.image.InterfaceImages;

/**
 * {@code ImportInterface} takes in image file.
 */
public interface ImportInterface {

  /**
   * Reads the image file in PPM format and returns as a matrix.
   * @param filename path of the image file.
   * @return the 2DMatrix of image file.
   */
  public InterfaceImages importImage(String filename);
}
