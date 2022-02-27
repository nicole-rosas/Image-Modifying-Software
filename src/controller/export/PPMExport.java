package controller.export;

import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * represents the exporting of a ppm image file.
 */
class PPMExport implements ExportInterface {

  /**
   * constructor of PPMExport class.
   */
  protected PPMExport() {
    // Function, does not need to store anything.
  }

  @Override
  public void export(InterfaceImages images, String filename) {
    if (images == null || filename == null) {
      throw new IllegalArgumentException("invalid images or filename");
    }

    StringBuilder finalBuild = new StringBuilder();
    finalBuild.append("P3");
    finalBuild.append("\n");

    int width = images.getWidth();
    int height = images.getHeight();

    finalBuild.append(width).append(" ").append(height).append("\n").append(255);

    for (int rows = 0; rows < height; rows++) {
      for (int cols = 0; cols < width; cols++) {
        finalBuild.append("\n");

        IPixels pixel = images.getPixel(rows, cols);
        finalBuild.append(pixel.getRed()).append(" ");
        finalBuild.append(pixel.getGreen()).append(" ");
        finalBuild.append(pixel.getBlue());
      }
    }

    String text = finalBuild.toString();

    try {
      FileWriter fileWriter = new FileWriter(filename + ".ppm");
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.print(text);
      printWriter.close();
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Cannot write to file " + filename);
    }

  }
}
