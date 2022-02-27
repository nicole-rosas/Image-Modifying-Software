package model.generateimage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a builder to create a checker board image.
 */
public class CheckerBoardBuilder implements ImageBuilderInterface {

  private IGenerateImage imageGenerator;
  private int width;
  private int height;
  private double alternatingInterval;
  private List<Color> colors;

  /**
   * empty constructor for CheckerBoardBuilder.
   */
  public CheckerBoardBuilder() {
    width = 500;
    height = 500;
    // else, it will go down in order of the list of colors provided.
    // To scale, the pixels will be 100 * alternatingInterval for clarity.
    alternatingInterval = 1;
    colors = new ArrayList<>();
    colors.add(Color.BLACK);
    colors.add(Color.WHITE);
  }

  @Override
  public void reset() {
    width = 500;
    height = 500;
    // else, it will go down in order of the list of colors provided.
    // To scale, the pixels will be 100 * alternatingInterval for clarity.
    alternatingInterval = 1;
    colors = new ArrayList<>();
    colors.add(Color.BLACK);
    colors.add(Color.WHITE);
  }

  @Override
  public void setInterval(Double interval) {
    if (interval > 0) {
      this.alternatingInterval = interval;
    }
  }

  @Override
  public IGenerateImage generatePresetClass() {
    return new CheckerBoardImageGenerator(this.width, this.height, this.alternatingInterval,
        this.colors);
  }

  @Override
  public void setWidth(int x) {
    if (x > 0) {
      this.width = x;
    }
  }

  @Override
  public void setHeight(int x) {
    if (x > 0) {
      this.height = x;
    }
  }

  @Override
  public void setColor(List<Color> colors) {
    if (colors == null) {
      throw new IllegalArgumentException("List of colors cannot be null.");
    }

    if (!colors.isEmpty()) {
      this.colors = new ArrayList<>();
      this.colors.addAll(colors);
    }
  }
}
