package model.images.pixels;

/**
 * interface for pixel in an image file.
 */
public interface IPixels {
  /**
   * returns the red value of pixel.
   * @return integer of red value
   */
  public int getRed();

  /**
   * returns the green value of pixel.
   * @return integer of green value
   */
  public int getGreen();

  /**
   * returns the blue value of pixel.
   * @return integer of blue value
   */
  public int getBlue();

  /**
   * ensures the color value is in between or equal to the min or max.
   * @param min minimum value the color can be.
   * @param max maximum value the color can be.
   */
  public void clampAll(int min, int max);

  /**
   * deep copy of the pixel's red, green, and blue value.
   * @return copy of the pixel.
   */
  public IPixels copyPixel();

  @Override
  public boolean equals(Object o);

  @Override
  public int hashCode();
}
