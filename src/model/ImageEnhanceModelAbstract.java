package model;

import model.images.image.InterfaceImages;
import java.awt.Color;
import java.util.List;

/**
 * Represents abstract of ImageEnhanceModel that takes in image file and processes it.
 */
public abstract class ImageEnhanceModelAbstract implements ImageEnhanceModelInterface {

  protected InterfaceImages image;
  protected FixedSizeStack<InterfaceImages> savedImages;
  protected String filename;

  /**
   * Constructor of ImageEnhanceModel that takes in no fields and makes the 2DImage empty.
   */
  public ImageEnhanceModelAbstract() {
    image = null;
    savedImages = new FixedSizeStack<>(50);
    filename = "";
  }

  @Override
  public abstract void setName(String name);

  @Override
  public abstract String getName();

  @Override
  public abstract void setImage(InterfaceImages image);

  @Override
  public abstract InterfaceImages getImage();

  @Override
  public abstract void transformSepia();

  @Override
  public abstract void convolutionBlur();

  @Override
  public abstract void transformGreyscale();

  @Override
  public abstract void convolutionSharpen();

  @Override
  public abstract void generateCheckerBoardImage(int width, int height,
      Double interval, List<Color> color);
}
