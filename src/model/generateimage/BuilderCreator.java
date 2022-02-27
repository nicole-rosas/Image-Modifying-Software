package model.generateimage;

/**
 * creates an image to generate.
 */
public class BuilderCreator {

  /**
   * creates a new image to generate depending on the given {@Code GeneratePresets}.
   * @param presets type of image to create.
   * @return new image.
   */
  public static ImageBuilderInterface create(GeneratePresets presets) {
    if (presets == null) {
      throw new IllegalArgumentException("presets is null!");
    }
    if (presets == GeneratePresets.CHECKERBOARD) {
      return new CheckerBoardBuilder();
    }
    throw new IllegalArgumentException("Invalid preset type.");
  }
}
