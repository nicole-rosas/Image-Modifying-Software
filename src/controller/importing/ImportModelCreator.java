package controller.importing;

import controller.ImportInterface;

/**
 * Creates the import model for the given file.
 */
public class ImportModelCreator {

  /**
   * Creates an import model depending on the given {@code ImportType}.
   *
   * @param filetype of given image file
   * @return a new import model.
   * @throws IllegalArgumentException if it is a null or invalid {@code ImportType}.
   */
  public static ImportInterface create(ImportType filetype) throws IllegalArgumentException {
    if (filetype == null) {
      throw new IllegalArgumentException("ImportType is null");
    }
    switch (filetype) {
      case PPM:
        return new PPMImport();
      case PNG:
        return new PNGImport();
      case JPEG:
        return new JPEGImport();
      default:
        throw new IllegalArgumentException("invalid ImportType");
    }
  }
}
