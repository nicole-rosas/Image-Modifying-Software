package controller.export;

/**
 * Factory class that creates a new export class.
 */
public class ExportModelCreator {

  /**
   * create a new export class depending on the {@note ExportType} parameter.
   *
   * @param filetype image file type.
   * @return an export class.
   * @throws IllegalArgumentException is the {@code ExportType} is null or invalid.
   */
  public static ExportInterface create(ExportType filetype) throws IllegalArgumentException {
    if (filetype == null) {
      throw new IllegalArgumentException("filetype is null!");
    }

    switch (filetype) {
      case PPM:
        return new PPMExport();
      case PNG:
        return new PNGExport();
      case JPEG:
        return new JPEGExport();
      default:
        throw new IllegalArgumentException("invalid filetype!");
    }
  }
}
