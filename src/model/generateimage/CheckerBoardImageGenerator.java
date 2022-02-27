package model.generateimage;

import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a generator to create a checker board.
 */
public class CheckerBoardImageGenerator extends AbstractGenerateImage {

  private int width;
  private int height;
  private double alternatingInterval;
  private List<Color> colors;

  /**
   * empty constructor for CheckerBoardImageGenerator.
   */
  protected CheckerBoardImageGenerator() {
    width = 500;
    height = 500;
    // else, it will go down in order of the list of colors provided.
    // To scale, the pixels will be 100 * alternatingInterval for clarity.
    alternatingInterval = 1;
    colors = new ArrayList<>();
    colors.add(Color.BLACK);
    colors.add(Color.WHITE);
  }

  protected CheckerBoardImageGenerator(int width, int height, double alternatingInterval,
      List<Color> colors) {
    this.width = width;
    this.height = height;
    this.alternatingInterval = alternatingInterval;
    this.colors = colors;
  }

  @Override
  public InterfaceImages generateImage() {
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    List<List<IPixels>> listOfPixels = new ArrayList<>();

    int turn = 0;
    int intervalSize = (int) (alternatingInterval * Math.ceil(width / 5.0));

    for (int rows = 0; rows < height; rows++) {
      listOfPixels.add(new ArrayList<>());
      turn = 0;

      if (rows % intervalSize == 0 && rows != 0) {
        colors.add(colors.remove(0));
      }

      for (int cols = 0; cols < width; cols++) {

        Color curColor = colors.get(turn);
        if (cols % intervalSize == 0) {
          if (turn + 1 == colors.size()) {
            turn = 0;
          }
          else {
            turn++;
          }

          curColor = colors.get(turn);
        }

        int r = curColor.getRed();
        int g = curColor.getGreen();
        int b = curColor.getBlue();

        IPixels pixel = new PixelsRGB(r, g, b);

        listOfPixels.get(rows).add(pixel);
      }
    }

    image.initiateImage(listOfPixels);

    return image;
  }
}
