package model.images.image;

import model.images.pixels.IPixels;
import java.util.List;

/**
 * Interface for images which are a sequence of pixels.
 * Model.Images will be processed and edited depending on given method.
 */
public interface InterfaceImages {

  /**
   * given the list of list of IPixels, it updates the image's pixels.
   * @param listOfListOfPixels will change the image's pixels.
   */
  void initiateImage(List<List<IPixels>> listOfListOfPixels);

  /**
   * image's clarity is updated given on the matrix.
   * @param matrix has affect on image's clarity.
   */
  InterfaceImages filterImage(List<List<Double>> matrix, int min, int max);

  /**
   * image's color is updated given on the matrix.
   * @param matrix has affect on image's color.
   */
  InterfaceImages transformImage(List<List<Double>> matrix, int min, int max);

  /**
   * returns a copy of the image file.
   * @return a copied list of list of pixels of the image file.
   */
  public InterfaceImages getDeepCopy();

  /**
   * gets the height of the image.
   * @return integer which represents the height of the image.
   */
  public int getWidth();

  /**
   * gets the width of the image.
   * @return integer which represents the width of the image.
   */
  public int getHeight();

  /**
   * gets the pixel of the image file at the given the row and col.
   * @param rows row of where the pixel is located.
   * @param cols what place the pixel is at the given row
   * @return pixel from image file.
   */
  public IPixels getPixel(int rows, int cols);

  public InterfaceImages crop(int firstWidth, int firstHeight);

  @Override
  public boolean equals(Object c);

  @Override
  public int hashCode();

}
