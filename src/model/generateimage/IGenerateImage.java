package model.generateimage;

import model.images.image.InterfaceImages;

/**
 * Interface that generates image files.
 */
public interface IGenerateImage {

  /**
   * creates a new image.
   * @return a generated image.
   */
  public InterfaceImages generateImage();
}
