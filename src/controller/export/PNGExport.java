package controller.export;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.images.image.InterfaceImages;

/**
 * represents the exporting of a PNG image file.
 */
public class PNGExport extends AbstractExportBuffer {

  /**
   * empty constructor for PNGExport class.
   */
  protected PNGExport() {
    // Exporting function, does not need to store anything.
  }


  @Override
  public void export(InterfaceImages images, String filename) {
    if (images == null || filename == null) {
      throw new IllegalArgumentException("the image and string cannot be null.");
    }

    BufferedImage img = super.getBufferImage(images);

    try {
      File f = new File(filename + ".png");
      ImageIO.write(img, "PNG", f);
    } catch (IOException e) {
      throw new IllegalArgumentException("image cannot be exported. Try again.");
    }
  }
}
