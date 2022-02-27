package controller.importing;

import controller.ImportInterface;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Mock of PPMImportMock to check for tests.
 */
public class PPMImportMock implements ImportInterface {


  /**
   * Reads the image file in PPM format and returns as a matrix.
   *
   * @param ppmFormat path of the image file.
   * @return the 2DMatrix of image file.
   */
  @Override
  public InterfaceImages importImage(String ppmFormat) {
    if (ppmFormat == null) {
      throw new IllegalArgumentException("Filename provided cannot be null.");
    }
    List<List<IPixels>> image = new ArrayList<>();

    StringBuilder builder = new StringBuilder();

    Scanner sc = new Scanner(ppmFormat);

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
