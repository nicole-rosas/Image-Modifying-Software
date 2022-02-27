package model.images.image;

import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * an Image is represented as a list of list of pixels that will
 * be used by image processing program.
 */
class Image2DMatrix implements InterfaceImages {

  private List<List<IPixels>> image;

  /**
   * Default constructor. Does not take in a List of List of Model.Images.
   */
  protected Image2DMatrix() {
    this(new ArrayList<>());
  }

  /**
   * constructor that takes in list of list of Model.Images.
   * @param image list of list of Model.Images.
   */
  Image2DMatrix(List<List<IPixels>> image) {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    List<List<IPixels>> newImage = new ArrayList<>();

    for (int x = 0; x < image.size(); x++) {
      newImage.add(new ArrayList<>());
      for (int y = 0; y < image.get(x).size(); y++) {
        IPixels newPixel = image.get(x).get(y).copyPixel();
        newImage.get(x).add(newPixel);
      }
    }

    this.image = newImage;
  }

  @Override
  public void initiateImage(List<List<IPixels>> image) {
    if (image == null) {
      throw new IllegalArgumentException("List Of List of Pixels cannot be null.");
    }

    List<List<IPixels>> newImage = new ArrayList<>();

    for (int x = 0; x < image.size(); x++) {
      newImage.add(new ArrayList<>());
      for (int y = 0; y < image.get(x).size(); y++) {
        IPixels newPixel = image.get(x).get(y).copyPixel();
        newImage.get(x).add(newPixel);
      }
    }

    this.image = newImage;
  }

  @Override
  public InterfaceImages filterImage(List<List<Double>> matrix, int min, int max) {
    if (image.isEmpty()) {
      throw new IllegalArgumentException("Cannot perform operation on empty image.");
    }

    InterfaceImages newImage;
    List<List<IPixels>> newListListOfPixels = new ArrayList<>();

    if (matrix == null) {
      throw new IllegalArgumentException("Matrix provided cannot be null.");
    }
    if (min < 0 || max > 255) {
      min = 0;
      max = 255;
    }

    int center = matrix.get(0).size() / 2;
    int imageHeight = image.size();

    // Apply filtering for each pixel of the image
    for (int rows = 0; rows < imageHeight; rows++) {
      newListListOfPixels.add(new ArrayList<>());
      for (int column = 0; column < image.get(rows).size(); column++) {
        // Operation on each pixel.
        // Operation on each value of the matrix
        int sumR = 0;
        int sumG = 0;
        int sumB = 0;

        int imageWidth = image.get(rows).size();

        for (int matrixRow = 0; matrixRow < matrix.size(); matrixRow++) {
          for (int matrixColumn = 0; matrixColumn < matrix.get(matrixRow).size(); matrixColumn++) {
            int relativePosRow = matrixRow - center + rows;
            int relativePosCol = matrixColumn - center + column;

            if (relativePosRow >= 0 && relativePosRow < imageHeight
                && relativePosCol >= 0 && relativePosCol < imageWidth) {
              IPixels pixelToAdd = image.get(relativePosRow).get(relativePosCol);
              Double matrixCalculateCur = matrix.get(matrixRow).get(matrixColumn);

              sumR += pixelToAdd.getRed() * matrixCalculateCur;
              sumG += pixelToAdd.getGreen() * matrixCalculateCur;
              sumB += pixelToAdd.getBlue() * matrixCalculateCur;
            }
          }
        }

        IPixels newPixel = new PixelsRGB(sumR, sumG, sumB);
        newPixel.clampAll(min, max);

        newListListOfPixels.get(rows).add(newPixel);
      }
    }

    newImage = new Image2DMatrix(newListListOfPixels);

    return newImage;
  }

  @Override
  public InterfaceImages transformImage(List<List<Double>> matrix, int min, int max) {
    if (image.isEmpty()) {
      throw new IllegalArgumentException("Cannot perform operation on empty image.");
    }

    if (matrix == null) {
      throw new IllegalArgumentException("Matrix provided cannot be null.");
    }

    if (min < 0 || max > 255) {
      min = 0;
      max = 255;
    }

    if (min > max) {
      min = max;
    } else if (max < min) {
      max = min;
    }

    InterfaceImages newImage;
    List<List<IPixels>> newListListOfPixels = new ArrayList<>();

    for (int rows = 0; rows < image.size(); rows++) {
      newListListOfPixels.add(new ArrayList<>());
      for (int column = 0; column < image.get(rows).size(); column++) {
        int sumR = 0;
        int sumG = 0;
        int sumB = 0;

        IPixels pixelToChange = image.get(rows).get(column);

        sumR = this.matrixMultiply(pixelToChange, matrix, 0);
        sumG = this.matrixMultiply(pixelToChange, matrix, 1);
        sumB = this.matrixMultiply(pixelToChange, matrix, 2);

        IPixels newPixel = new PixelsRGB(sumR, sumG, sumB);
        newPixel.clampAll(min, max);

        newListListOfPixels.get(rows).add(newPixel);
      }
    }

    newImage = new Image2DMatrix(newListListOfPixels);

    return newImage;
  }

  /**
   * multiplies given row of given matrix against the RGB and sets it as linear combination.
   * @param pixel that will be edited.
   * @param matrix that edits the pixel.
   * @param index row of matrix.
   * @return new value of linear combination.
   */
  private int matrixMultiply(IPixels pixel, List<List<Double>> matrix, int index) {
    if (pixel == null || matrix == null) {
      throw new IllegalArgumentException("Pixel provided or matrix provided cannot be null.");
    }
    if (index < 0 || index > matrix.size()) {
      throw new IllegalArgumentException("Index cannot be less than 0 "
          + "or greater than matrix size.");
    }

    List<Double> array = matrix.get(index);
    double sum = 0;

    for (int x = 0; x < array.size(); x++) {
      if (x == 0) {
        sum += pixel.getRed() * array.get(x);
      }
      if (x == 1) {
        sum += pixel.getGreen() * array.get(x);
      }
      if (x == 2) {
        sum += pixel.getBlue() * array.get(x);
      }
    }

    return (int) sum;
  }

  @Override
  public InterfaceImages getDeepCopy() {

    if (image.isEmpty()) {
      throw new IllegalArgumentException("Process cannot be done with empty image.");
    }

    InterfaceImages copyImage = new Image2DMatrix();
    List<List<IPixels>> newListOfListOfPixels = new ArrayList<>();

    for (int rows = 0; rows < image.size(); rows++) {
      newListOfListOfPixels.add(new ArrayList<>());
      for (int cols = 0; cols < image.get(rows).size(); cols++) {
        IPixels copyPixel = image.get(rows).get(cols).copyPixel();
        newListOfListOfPixels.get(rows).add(copyPixel);
      }
    }

    copyImage.initiateImage(newListOfListOfPixels);
    return copyImage;
  }

  @Override
  public int getWidth() {
    if (image.isEmpty()) {
      throw new IllegalArgumentException("Cannot get pixel on an empty image.");
    }

    return image.get(0).size();
  }

  @Override
  public int getHeight() {
    if (image.isEmpty()) {
      throw new IllegalArgumentException("Cannot get pixel on an empty image.");
    }

    return image.size();
  }

  @Override
  public IPixels getPixel(int rows, int cols) {
    if (image.isEmpty()) {
      throw new IllegalArgumentException("Cannot get pixel on an empty image.");
    }

    if (rows < 0 || rows > image.size() || cols < 0 || cols > image.get(rows).size()) {
      throw new IllegalArgumentException("Row or column out of bounds.");
    }
    IPixels pixel = image.get(rows).get(cols);
    return pixel.copyPixel();
  }

  @Override
  public InterfaceImages crop(int firstWidth, int firstHeight) {
    List<List<IPixels>> loloPixels = new ArrayList<>();
    InterfaceImages croppedImage = ImageCreator.create(ImageType.MATRIX2D);

    int counterX = 0;
    for (int x = Math.max(0, ((getHeight() - firstHeight) / 2) - 1);
        counterX < Math.min(firstHeight, getHeight()); x++) {
      int counterY = 0;
      loloPixels.add(new ArrayList<>());
      for (int y = Math.max(0, ((getWidth() - firstWidth) / 2) - 1);
          counterY < Math.min(firstWidth, getWidth()); y++) {
        IPixels copyPixel = this.image.get(x).get(y).copyPixel();
        loloPixels.get(counterX).add(copyPixel);
        counterY++;
      }
      counterX++;
    }

    croppedImage.initiateImage(loloPixels);
    return croppedImage;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Image2DMatrix)) {
      return false;
    }

    Image2DMatrix that = (Image2DMatrix) obj;

    if (that.getHeight() != this.getHeight() || that.getWidth() != this.getWidth()) {
      return false;
    }

    for (int x = 0; x < this.getWidth(); x++) {
      for (int y = 0; y < this.getHeight(); y++) {
        if (!this.getPixel(y, x).equals(that.getPixel(y,x))) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    int f = 0;

    for (int x = 0; x < this.getHeight(); x++) {
      for (int y = 0; y < this.getWidth(); y++) {
        IPixels pixel = this.getPixel(x,y);
        int sum = pixel.getBlue() + pixel.getGreen() + pixel.getRed();
        f += sum;
      }
    }

    f = (int) f / (this.getWidth() * this.getHeight());

    return Objects.hash(f);
  }
}
