package controller;

import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;

/**
 * Abstract class for {@code ImportInterface}.
 * Abstracts the method {@code importImage} for {@code JPEGImport} and {@code PNGImport}.
 */
public class ImportBufferAbstract implements ImportInterface {

  @Override
  public InterfaceImages importImage(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename provided cannot be null.");
    }

    BufferedImage buff;

    try {
      buff = ImageIO.read(new File(filename));
    }
    catch (IOException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    int height = buff.getHeight();
    int width = buff.getWidth();
    List<List<IPixels>> image = new ArrayList<>();

    for (int x = 0; x < height; x++) {
      image.add(new ArrayList<>());
      for (int y = 0; y < width; y++) {
        int clr = buff.getRGB(y, x);
        int r = (clr & 0x00ff0000) >> 16;
        int g = (clr & 0x0000ff00) >> 8;
        int b = (clr & 0x000000ff);

        IPixels pixel = new PixelsRGB(r, g, b);
        image.get(x).add(pixel);
      }
    }

    InterfaceImages finalImage = ImageCreator.create(ImageType.MATRIX2D);
    finalImage.initiateImage(image);
    return finalImage;
  }
}
