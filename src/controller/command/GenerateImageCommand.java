package controller.command;

import java.util.ArrayList;
import model.MultiLayeredEnhanceInterface;
import model.generateimage.GeneratePresets;

/**
 * Represents the user generating an image for image processing.
 */
public class GenerateImageCommand implements EnhanceCommandInterface {

  private final int width;
  private final int height;
  private final double interval;
  private final GeneratePresets type;

  /**
   * Constructor for GenerateImageCommand that takes in a GeneratePresets, int, int, and double.
   * @param type will determine what type of image will be generated.
   * @param width the width of the generated image.
   * @param height the height of the generated image.
   * @param interval the interval for colors in generated image.
   */
  public GenerateImageCommand(GeneratePresets type, int width, int height, double interval) {
    if (type == null) {
      throw new IllegalArgumentException("Type provided cannot be null.");
    }

    this.width = width;
    this.height = height;
    this.interval = interval;
    this.type = type;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }
    if (type.equals(GeneratePresets.CHECKERBOARD)) {
      m.generateCheckerBoardImage(width, height, interval, new ArrayList<>());
    }
    else {
      throw new IllegalArgumentException("Invalid preset type.");
    }
  }
}
