package model.generateimage;

import java.awt.Color;
import java.util.List;

/**
 * Interface to build image file.
 */
public interface ImageBuilderInterface {

  /**
   * sets the width of the image to build.
   * @param x width of image.
   */
  public void setWidth(int x);

  /**
   * sets the height of the image to build.
   * @param x height of image.
   */
  public void setHeight(int x);

  /**
   * sets the color palette of the image to build.
   * @param colors color palette of image.
   */
  public void setColor(List<Color> colors);

  /**
   * resets all changes to image and makes the black and white checker board.
   */
  public void reset();

  /**
   * sets the interval of when to change color.
   * @param interval interval to switch color.
   */
  public void setInterval(Double interval);

  /**
   * returns the current updated image.
   * @return new changed image.
   */
  public IGenerateImage generatePresetClass();
}
