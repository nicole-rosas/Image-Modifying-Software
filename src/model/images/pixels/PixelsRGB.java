package model.images.pixels;

import java.util.Objects;

/**
 * The Pixels class that implements the IPixels interface.
 *
 */
public class PixelsRGB implements IPixels {
  private int red;
  private int green;
  private int blue;

  /**
   * constructor of pixel which has three values.
   * @param red red value of pixel
   * @param green green value of pixel
   * @param blue blue value of pixel
   */
  public PixelsRGB(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.clampAll(0, 255);
  }

  @Override
  public void clampAll(int min, int max) {
    this.red = clampIndividual(red, min, max);
    this.green = clampIndividual(green, min, max);
    this.blue = clampIndividual(blue, min, max);
  }

  /**
   * limits color from going past max or falling behind min.
   * @param color represents the actual color value.
   * @param min minimum value the given color value can be.
   * @param max maximum value the given color value can be
   * @return returns either the min, max, or the color value.
   */
  private int clampIndividual(int color, int min, int max) {
    if (min < 0 || min > 255 || max < 0 || max > 255) {
      throw new IllegalArgumentException("Min or max is out of range.");
    }

    if (min > max) {
      min = max;
    }

    if (color < min) {
      return min;
    }
    else if (color > max) {
      return max;
    }

    else {
      return color;
    }
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public IPixels copyPixel() {
    return new PixelsRGB(this.red, this.green, this.blue);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof PixelsRGB)) {
      return false;
    }

    PixelsRGB that = (PixelsRGB) o;

    return this.getRed() == that.getRed()
        && this.getGreen() == that.getGreen()
        && this.getBlue() == that.getBlue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getRed() + this.getGreen() + this.getBlue());
  }
}
