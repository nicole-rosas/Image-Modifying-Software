package controller.export;

import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import java.io.IOException;
import java.util.Objects;

/**
 * Mock of ExportPPM class to be used for testing.
 */
public class ExportPPMMock implements ExportInterface {

  final StringBuilder log;

  /**
   * Constructor of ExportPPMMock.
   * @param log string of image
   */
  public ExportPPMMock(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * exports the given image with filename in ppm format.
   * @param images   list of list of pixels that will be exported.
   * @param filename file name of the given image
   * @throws IOException if {@code images} or {@code filename} is null or invalid.
   */
  @Override
  public void export(InterfaceImages images, String filename) {
    if (images == null || filename == null) {
      throw new IllegalArgumentException("invalid images or file name.");
    }

    log.append("P3");
    log.append("\n");

    int width = images.getWidth();
    int height = images.getHeight();

    log.append(width).append(" ").append(height).append("\n").append(255);

    for (int rows = 0; rows < height; rows++) {
      for (int cols = 0; cols < width; cols++) {
        log.append("\n");

        IPixels pixel = images.getPixel(rows, cols);
        log.append(pixel.getRed()).append(" ");
        log.append(pixel.getGreen()).append(" ");
        log.append(pixel.getBlue());
      }
    }
  }
}
