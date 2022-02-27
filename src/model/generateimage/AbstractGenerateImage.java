package model.generateimage;

import model.images.image.InterfaceImages;

/**
 * Abstract of IGenerateImage class.
 */
public abstract class AbstractGenerateImage implements IGenerateImage {

  @Override
  public abstract InterfaceImages generateImage();
}