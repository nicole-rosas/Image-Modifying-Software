package model;

import model.images.image.InterfaceImages;
import java.awt.Color;
import java.util.List;

/**
 * Interface of image file that is imported and will be edited.
 */
public interface ImageEnhanceModelInterface {

  /**
   * gets the InterfaceImages in the model.
   * @return a InterfaceImages
   */
  public InterfaceImages getImage();

  /**
   * gets the name of the layer/image.
   * @return String which represents the name of layer/image.
   */
  public String getName();

  /**
   * names the image file with the given string.
   * @param name new file name
   */
  public void setName(String name);


  /**
   * sets image to the given {@code InterfaceImages}.
   * @param image image to deep copy from.
   */
  public void setImage(InterfaceImages image);

  /**
   * Transforms image file and changes the colors depending
   * on the given {@code TransformType}.
   */
  public void transformSepia();

  /**
   * Changes the clarity of image depending on the given {@code KernelType}.
   */
  public void convolutionBlur();

  /**
   * Transforms image file and changes the colors depending
   * on the given {@code TransformType}.
   */
  public void transformGreyscale();

  /**
   * Changes the clarity of image depending on the given {@code KernelType}.
   */
  public void convolutionSharpen();

  /**
   * When this method is called, creates an image that will be stored into the model's current
   * editing image.
   * @param width of the generated image.
   * @param height of the generated image.
   * @param interval interval of colors. After this specified interval, (scaled by the generator
   *                 class), the generator will select the next color in sequence.
   * @param color the list of colors (in sequence) to be used.
   */
  public void generateCheckerBoardImage(int width, int height,
      Double interval, List<Color> color);

}
