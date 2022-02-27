package controller.export;

import java.awt.image.BufferedImage;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;

/**
 * Abstract class for {@code ExportInterface}.
 * Abstracts the method {@code getBufferImage} for {@code JPEGExport} and {@code PNGExport}.
 */
public abstract class AbstractExportBuffer implements ExportInterface {

  /**
   * returns the given {@code InterfaceImages} as a BufferedImage
   * that will be used to export {@code JPEGExport} and {@code PNGExport}.
   * @param images an {@code InterfaceImages} that will be made into a BufferedImage.
   * @return images as a BufferedImage.
   */
  protected BufferedImage getBufferImage(InterfaceImages images) {
    if (images == null) {
      throw new IllegalArgumentException("Images provided cannot be null.");
    }

    int width = images.getWidth();
    int height = images.getHeight();

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        IPixels pixel = images.getPixel(x, y);
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();

        int col = (r << 16) | (g << 8) | b;
        img.setRGB(y, x, col);
      }
    }

    return img;
  }

  @Override
  public abstract void export(InterfaceImages images, String filename);
}
