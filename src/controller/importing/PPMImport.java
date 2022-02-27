package controller.importing;

import controller.ImportInterface;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * represents import model of ppm image file format.
 */
class PPMImport implements ImportInterface {

  /**
   * empty constructor of {@code ppmImport}, does not need to initiate anything.
   */
  protected PPMImport() {
    // Does not need to initiate anything.
  }

  @Override
  public InterfaceImages importImage(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Filename provided cannot be null.");
    }

    Scanner sc;
    List<List<IPixels>> image = new ArrayList<>();

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();

    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Incorrect File specification.");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    for (int i = 0; i < height; i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        IPixels pixel = new PixelsRGB(r, g, b);
        image.get(i).add(pixel);
      }
    }

    InterfaceImages finalImage = ImageCreator.create(ImageType.MATRIX2D);
    finalImage.initiateImage(image);
    return finalImage;
  }
}
