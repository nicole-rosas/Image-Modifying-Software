package controller.export;

import model.images.image.InterfaceImages;

/**
 * interface for exporting image files in ppm format.
 */
public interface ExportInterface {

  /**
   * exports the given image with filename in ppm format.
   * @param images list of list of pixels that will be exported.
   * @param filename file name of the given image.
   */
  public void export(InterfaceImages images, String filename);
}
