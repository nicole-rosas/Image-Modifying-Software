import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import model.MultiLayeredEnhanceInterface;
import model.MultiLayerEnhanceModel;
import model.ImageEnhanceBasicModel;

import model.layers.InterfaceLayers;
import model.layers.Layer2DMatrix;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the methods inside MultiLayerEnhanceModel class.
 */
public class MultiLayerEnhanceModelTest {

  MultiLayeredEnhanceInterface multiE;
  MultiLayeredEnhanceInterface multiF;
  ImageEnhanceBasicModel model;

  @Before
  public void setup() {
    model = new ImageEnhanceBasicModel();
    multiE = new MultiLayerEnhanceModel();
  }

  @Test
  public void testChangeName() {
    multiE.addLayer("");
    assertEquals("", multiE.getName());
    multiE.setName("OOD");
    assertEquals("OOD", multiE.getName());
  }

  @Test
  public void changeImage() {
    InterfaceImages blue = ImageCreator.create(ImageType.MATRIX2D);
    IPixels pixel = new PixelsRGB(0, 0, 255);
    List<IPixels> listOfB = new ArrayList<>();
    listOfB.add(pixel);
    listOfB.add(pixel);
    List<List<IPixels>> bSquare = new ArrayList<>();
    bSquare.add(listOfB);
    bSquare.add(listOfB);
    blue.initiateImage(bSquare);

    multiE.addLayer("i");
    multiE.setImage(blue);
    assertEquals(blue, multiE.getImage());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaWithNullLayer() {
    multiE.transformSepia();
  }

  @Test
  public void testSepia() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("hi");
    multiE.setImage(image);
    multiE.transformSepia();

    InterfaceImages imageSepia = multiE.getImage();

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

  @Test(expected = IllegalArgumentException.class)
  public void testGrayscaleWithNullLayer() {
    multiE.transformSepia();
  }

  @Test
  public void testGreyscale() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("hello");
    multiE.setImage(image);

    multiE.transformGreyscale();

    InterfaceImages imageSepia = multiE.getImage();

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
  public void testSharpenWithNullLayer() {
    multiE.convolutionSharpen();
  }

  @Test
  public void testSharpen() {
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

    multiE.addLayer("hii");
    multiE.setImage(image);

    multiE.convolutionSharpen();

    InterfaceImages sharpenImage = multiE.getImage();

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

  @Test(expected = IllegalArgumentException.class)
  public void testBlurWithNullLayer() {
    multiE.convolutionBlur();
  }

  @Test
  public void testBlur() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);

    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("o");
    multiE.setImage(image);

    multiE.convolutionBlur();

    InterfaceImages blurImage = multiE.getImage();

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

  @Test(expected = IllegalArgumentException.class)
  public void cannotGenerateBoardNullLayers() {
    multiE.generateCheckerBoardImage(5, 5, 1.0, new ArrayList<>());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setLayersButNullInvalid() {
    multiE.setIntoLayers(null);
  }

  @Test
  public void checkLayers() {
    assertEquals(0, multiE.getNumLayers());
    List<InterfaceLayers> newList = new ArrayList<>();
    newList.add(new Layer2DMatrix());
    multiE.setIntoLayers(newList);
    assertEquals(1, multiE.getNumLayers());
  }

  @Test(expected = IllegalArgumentException.class)
  public void cantAddNullLayer() {
    multiE.addLayer(null);
  }

  @Test
  public void testLayersAdding() {
    assertEquals(0, multiE.getNumLayers());
    multiE.addLayer("hello");
    assertEquals(1, multiE.getNumLayers());
  }

  @Test(expected = IllegalArgumentException.class)
  public void cantRemoveNullStringInvalid() {
    multiE.removeLayer(null);
  }

  @Test
  public void RemoveLayerBcName() {
    multiE.addLayer("bye");
    assertEquals(1, multiE.getNumLayers());
    multiE.removeLayer("bye");
    assertEquals(0, multiE.getNumLayers());
  }

  @Test
  public void changeTheCurrent() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("1");
    InterfaceLayers layer1 = new Layer2DMatrix();
    layer1.setName("1");
    layer1.setImageInLayer(image);
    multiE.setImage(image);

    multiE.addLayer("2");
    InterfaceLayers layer2 = new Layer2DMatrix();
    layer2.setName("2");
    layer2.setImageInLayer(image);
    multiE.setImage(image);

    assertEquals(layer1, multiE.getCurrentLayer());
    multiE.changeCurrentLayer("1");
    assertEquals(layer1, multiE.getCurrentLayer());
  }

  @Test
  public void testVisibility() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    InterfaceLayers layerX = new Layer2DMatrix();
    layerX.setName("xyz");
    layerX.setImageInLayer(image);
    multiE.addLayer("xyz");
    multiE.setImage(image);

    InterfaceLayers layerA = new Layer2DMatrix();
    layerA.setName("abc");
    multiE.addLayer("abc");
    layerA.setImageInLayer(image);

    assertEquals(layerX, multiE.getTopMostVisibleLayer());
    multiE.changeVisibility("abc", false);
    assertEquals(layerX, multiE.getTopMostVisibleLayer());
  }

  @Test
  public void testGetVisibleLayer() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    InterfaceLayers layerX = new Layer2DMatrix();
    layerX.setName("xyz");
    layerX.setImageInLayer(image);
    multiE.addLayer("xyz");
    multiE.setImage(image);

    assertEquals(layerX, multiE.getTopMostVisibleLayer());
  }

  @Test
  public void getTopImageVisible() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    InterfaceLayers o = new Layer2DMatrix();
    o.setName("o");
    o.setImageInLayer(image);
    multiE.addLayer("o");
    multiE.setImage(image);
    assertEquals(image, multiE.getTopVisLayerImage());
  }

  @Test
  public void TestCurrentLayer() {
    multiE.addLayer("j");
    InterfaceLayers j = new Layer2DMatrix();
    j.setName("j");

    assertEquals(j, multiE.getCurrentLayer());
  }

  @Test
  public void countsNumOfLayers() {
    assertEquals(0, multiE.getNumLayers());
    multiE.addLayer("a");
    multiE.addLayer("g");
    multiE.addLayer("e");
    assertEquals(3, multiE.getNumLayers());
  }

  @Test
  public void testsSwitchingPos() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("a");
    InterfaceLayers a = new Layer2DMatrix();
    a.setName("a");
    a.setImageInLayer(image);
    multiE.setImage(image);

    multiE.addLayer("g");
    InterfaceLayers g = new Layer2DMatrix();
    g.setName("g");
    g.setImageInLayer(image);
    multiE.changeCurrentLayer("g");
    multiE.setImage(image);

    multiE.addLayer("e");
    InterfaceLayers e = new Layer2DMatrix();
    e.setName("e");
    e.setImageInLayer(image);
    multiE.changeCurrentLayer("e");
    multiE.setImage(image);

    assertEquals(Arrays.asList(a, g, e), multiE.getAllLayers());
    multiE.switchLayerPosition("g", 0);
    assertEquals(Arrays.asList(g, a, e), multiE.getAllLayers());
  }

  @Test
  public void getsListOfLayers() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("f");
    multiE.setImage(image);
    multiE.addLayer("r");
    multiE.changeCurrentLayer("r");
    multiE.setImage(image);

    InterfaceLayers l1 = new Layer2DMatrix();
    l1.setName("f");
    l1.setImageInLayer(image);
    InterfaceLayers l2 = new Layer2DMatrix();
    l2.setName("r");
    l2.setImageInLayer(image);

    List<InterfaceLayers> ll = multiE.getAllLayers();

    assertEquals(Arrays.asList(l1, l2), ll);
  }

  @Test
  public void testUndoAMove() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("p");
    multiE.setImage(image);
    multiE.setName("p");
    multiE.undo();
    assertEquals("p", multiE.getName());
    multiE.setName("n");
    assertEquals("n", multiE.getName());
    multiE.undo();
    assertEquals("p", multiE.getName());
  }

  @Test
  public void testRemoveImage() {
    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);
    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);

    multiE.addLayer("r");
    multiE.setName("r");
    multiE.setImage(image);
    assertEquals(image, multiE.getImage());
    multiE.removeImage();
    assertEquals(true, multiE.getCurrentLayer().isImageEmpty());
  }
}
