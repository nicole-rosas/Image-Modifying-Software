import static org.junit.Assert.assertEquals;

import model.ImageEnhanceBasicModel;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests methods in {@code ImageEnhanceBasicModel} class.
 */
public class ModelTest {

  ImageEnhanceBasicModel model;

  @Before
  public void setup() {
    model = new ImageEnhanceBasicModel();
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidBcNullName() {
    model.setName(null);
  }

  @Test
  public void setNameToImage() {
    InterfaceImages greenPic = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pix = new PixelsRGB(0, 255, 0);
    List<IPixels> greens = new ArrayList<>();
    greens.add(pix);
    greens.add(pix);
    List<List<IPixels>> img = new ArrayList<>();
    img.add(greens);
    greenPic.initiateImage(img);
    model.setImage(greenPic);
    model.setName("");
    assertEquals("image", model.getName());
  }

  @Test
  public void setNameToGreen() {
    InterfaceImages greenPic = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pix = new PixelsRGB(0, 255, 0);
    List<IPixels> greens = new ArrayList<>();
    greens.add(pix);
    greens.add(pix);
    List<List<IPixels>> img = new ArrayList<>();
    img.add(greens);
    greenPic.initiateImage(img);
    model.setImage(greenPic);
    model.setName("Green");
    assertEquals("Green", model.getName());
  }

  @Test
  public void testGetName() {
    InterfaceImages greenPic = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pix = new PixelsRGB(0, 255, 0);
    List<IPixels> greens = new ArrayList<>();
    greens.add(pix);
    greens.add(pix);
    List<List<IPixels>> img = new ArrayList<>();
    img.add(greens);
    greenPic.initiateImage(img);
    model.setImage(greenPic);
    model.setName("Green");
    assertEquals("Green", model.getName());
  }

  @Test
  public void checkTransform() {
    InterfaceImages greenPic = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pix = new PixelsRGB(0, 255, 0);
    List<IPixels> greens = new ArrayList<>();
    greens.add(pix);
    greens.add(pix);
    List<List<IPixels>> img = new ArrayList<>();
    img.add(greens);
    greenPic.initiateImage(img);
    model.setImage(greenPic);

    InterfaceImages before = model.getImage();

    assertEquals(pix, before.getPixel(0, 0));
    assertEquals(pix, before.getPixel(0, 1));


  }

  @Test
  public void checkConvolutionBlur() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);

    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    model.setImage(image);

    model.convolutionBlur();

    InterfaceImages blurImage = model.getImage();

    List<List<IPixels>> expectedImagePre = new ArrayList<>();
    IPixels pixel6 = new PixelsRGB(6, 6, 6);
    IPixels pixel8 = new PixelsRGB(8, 8, 8);
    List<IPixels> expectedRow = new ArrayList<>();
    expectedRow.add(pixel6);
    expectedRow.add(pixel8);
    expectedRow.add(pixel6);
    expectedImagePre.add(expectedRow);

    InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
    expectedImage.initiateImage(expectedImagePre);

    assertEquals(expectedImage, blurImage);
  }

  @Test
  public void checkConvolutionSharpen() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);

    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    model.setImage(image);

    model.convolutionSharpen();

    InterfaceImages sharpenImage = model.getImage();

    List<List<IPixels>> expectedImagePre = new ArrayList<>();

    List<IPixels> expectedRow = new ArrayList<>();

    IPixels pixel6 = new PixelsRGB(18, 18, 18);
    IPixels pixel10 = new PixelsRGB(22, 22, 22);
    IPixels pixel8 = new PixelsRGB(20, 20, 20);

    expectedRow.add(pixel6);
    expectedRow.add(pixel10);
    expectedRow.add(pixel8);
    expectedRow.add(pixel10);
    expectedRow.add(pixel6);

    expectedImagePre.add(expectedRow);

    InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
    expectedImage.initiateImage(expectedImagePre);

    assertEquals(expectedImage, sharpenImage);
  }

  @Test
  public void checkTransformSepia() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    model.setImage(image);

    model.transformSepia();

    InterfaceImages imageSepia = model.getImage();

    List<List<IPixels>> expectedImagePre = new ArrayList<>();

    List<IPixels> expectedRow = new ArrayList<>();

    IPixels pixel6 = new PixelsRGB(21, 19, 14);
    expectedRow.add(pixel6);
    expectedRow.add(pixel6);
    expectedRow.add(pixel6);

    expectedImagePre.add(expectedRow);

    InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
    expectedImage.initiateImage(expectedImagePre);

    assertEquals(expectedImage, imageSepia);
  }

  @Test
  public void checkTransformGreyscale() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    model.setImage(image);

    model.transformGreyscale();

    InterfaceImages imageSepia = model.getImage();

    List<List<IPixels>> expectedImagePre = new ArrayList<>();

    List<IPixels> expectedRow = new ArrayList<>();

    IPixels pixel6 = new PixelsRGB(16, 16, 16);
    expectedRow.add(pixel6);
    expectedRow.add(pixel6);
    expectedRow.add(pixel6);

    expectedImagePre.add(expectedRow);

    InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
    expectedImage.initiateImage(expectedImagePre);

    assertEquals(expectedImage, imageSepia);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullImageCantSet() {
    model.setImage(null);
  }

  @Test
  public void testSetImage() {
    InterfaceImages greenPic = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pix = new PixelsRGB(0, 255, 0);
    List<IPixels> greens = new ArrayList<>();
    greens.add(pix);
    greens.add(pix);
    List<List<IPixels>> img = new ArrayList<>();
    img.add(greens);
    greenPic.initiateImage(img);
    model.setImage(greenPic);

    assertEquals(true, model.getImage().getPixel(0, 0).equals(pix));
    assertEquals(true, model.getImage().getPixel(0, 1).equals(pix));
  }

  @Test
  public void testGetImage() {
    InterfaceImages greenPic = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pix = new PixelsRGB(0, 255, 0);
    List<IPixels> greens = new ArrayList<>();
    greens.add(pix);
    greens.add(pix);
    List<List<IPixels>> img = new ArrayList<>();
    img.add(greens);
    greenPic.initiateImage(img);
    model.setImage(greenPic);

    assertEquals(greenPic.getPixel(0, 0), model.getImage().getPixel(0, 0));
    assertEquals(greenPic.getPixel(0, 1), model.getImage().getPixel(0, 0));
  }
}
