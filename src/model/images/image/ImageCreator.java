package model.images.image;

/**
 * factory class that creates a new image class.
 */
public class ImageCreator {

  /**
   * creates a new image class that depends on the given {@code ImageType}.
   * @param type {@code ImageType} of image.
   * @return new image class.
   * @throws IllegalArgumentException is the {@code ImageType} is null or invalid.
   */
  public static InterfaceImages create(ImageType type) {
    if (type == null) {
      throw new IllegalArgumentException("ImageType is null!!");
    }
    if (type == ImageType.MATRIX2D) {
      return new Image2DMatrix();
    }
    throw new IllegalArgumentException("Illegal Image creation type.");
  }
}
