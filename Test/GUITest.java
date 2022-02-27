import controller.MockControllerView;
import controller.guicontroller.GUIController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import model.MultiLayerEnhanceModel;
import model.MultiLayeredEnhanceInterface;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import model.layers.InterfaceLayers;
import model.layers.Layer2DMatrix;
import org.junit.Before;
import org.junit.Test;
import view.MockView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the setup of the GUI view.
 */
public class GUITest {
  MockView mockView;
  MockControllerView mockController;

  @Before
  public void setup() throws Exception {
    mockView = new MockView();
  }

  @Test
  public void testControllerBlur() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);
    model.addLayer("hi");
    model.setImage(image);

    con.onBlurListener();

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
  public void testControllerSepia() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    List<List<IPixels>> imagePreProcessed = new ArrayList<>();
    IPixels pixel = new PixelsRGB(16, 16, 16);
    List<IPixels> row = new ArrayList<>();
    row.add(pixel);
    row.add(pixel);
    row.add(pixel);
    imagePreProcessed.add(row);

    InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
    image.initiateImage(imagePreProcessed);
    model.addLayer("hi");
    model.setImage(image);

    con.onSepiaListener();
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
  public void testControllerImport() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    String dots = "P3\n# Created by GIMP version 2.10.24 PNM plug-in\n2 2\n255\n"
        + "255\n0\n0\n255\n0\n0\n255\n0\n0\n255\n0\n0";

    try {
      FileWriter fileWriter = new FileWriter("test.ppm");
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.print(dots);
      printWriter.close();
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Cannot write to file.");
    }

    model.addLayer("layer1");
    con.onImport(new File("test.ppm"));

    InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
    List<List<IPixels>> loloPixels = new ArrayList<>();
    List<IPixels> row = new ArrayList<>();
    IPixels red = new PixelsRGB(255, 0, 0);
    row.add(red);
    row.add(red);
    loloPixels.add(row);
    loloPixels.add(row);
    expectedImage.initiateImage(loloPixels);

    assertEquals(model.getImage(), expectedImage);

    File f = new File("test");
    f.delete();
  }

  @Test
  public void testControllerSave() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    StringBuilder log = new StringBuilder();

    InterfaceImages img = ImageCreator.create(ImageType.MATRIX2D);
    List<List<IPixels>> loloPixels = new ArrayList<>();
    IPixels red = new PixelsRGB(255, 0, 0);
    List<IPixels> row = new ArrayList<>();
    row.add(red);
    row.add(red);

    loloPixels.add(row);
    loloPixels.add(row);
    img.initiateImage(loloPixels);

    model.addLayer("layer1");
    model.setImage(img);
    model.addLayer("layer2");
    model.changeCurrentLayer("layer2");
    model.setImage(img);

    File f = new File("test1111");
    assertTrue(f.mkdir());
    con.onSaveLayer(f);

    File index = new File("test1111");
    String[] entries = index.list();

    assertEquals(3, entries.length);

    for (String s : entries) {
      File currentFile = new File(index.getPath(), s);
      assertTrue(currentFile.delete());
    }

    assertTrue(index.delete());
  }

  @Test
  public void testControllerGenerate() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hello");

    con.generateCheckerBoardImage(-1, -1, -1.0, "black", "black");

    assertEquals(1, model.getNumLayers());
  }

  @Test
  public void testControllerAdd() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    con.onAddLayer();

    InterfaceLayers layer = new Layer2DMatrix();
    layer.setName("new layer");
    layer.setVisibility(true);
    assertEquals(model.getCurrentLayer(), layer);
  }

  @Test
  public void testControllerChangeCur() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hi");
    model.addLayer("hello");
    InterfaceLayers curLayer = new Layer2DMatrix();
    curLayer.setName("hi");
    curLayer.setVisibility(true);
    assertEquals(curLayer, model.getCurrentLayer());
    con.onChangeCurLayer("hello");

    curLayer = new Layer2DMatrix();
    curLayer.setName("hello");
    curLayer.setVisibility(true);
    assertEquals(curLayer, model.getCurrentLayer());
  }

  @Test
  public void testControllerChangeName() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hi");
    assertEquals("hi", model.getName());
    con.onChangeName("name");
    assertEquals("name", model.getName());
  }

  @Test
  public void testControllerHide() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hi");
    assertTrue(model.getCurrentLayer().getVisibilityStatus());
    con.onHide("hi");
    assertFalse(model.getCurrentLayer().getVisibilityStatus());


  }

  @Test
  public void testControllerVisible() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hi");
    model.changeVisibility("hi", false);
    assertFalse(model.getCurrentLayer().getVisibilityStatus());
    con.onVisible("hi");
    assertTrue(model.getCurrentLayer().getVisibilityStatus());
  }

  @Test
  public void testControllerUndo() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hi");
    model.setName("hi");
    model.setName("bye");
    con.onUndo();
    assertEquals("hi", model.getName());
  }

  @Test
  public void testControllerRemoveImage() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hello");

    InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
    List<List<IPixels>> loloPixels = new ArrayList<>();
    List<IPixels> row = new ArrayList<>();
    IPixels red = new PixelsRGB(255, 0, 0);
    row.add(red);
    row.add(red);
    loloPixels.add(row);
    loloPixels.add(row);
    expectedImage.initiateImage(loloPixels);

    model.setImage(expectedImage);

    assertFalse(model.getCurrentLayer().isImageEmpty());
    con.onRemoveImage();
    assertTrue(model.getCurrentLayer().isImageEmpty());
  }

  @Test
  public void testControllerDeleteLayer() {
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    GUIController con = new GUIController(model);

    model.addLayer("hi");
    model.addLayer("hello");
    InterfaceLayers layer = new Layer2DMatrix();
    layer.setName("hi");
    layer.setVisibility(true);
    assertEquals(layer, model.getCurrentLayer());
    assertEquals(2, model.getNumLayers());
    con.onDeleteLayer();

    assertEquals(1, model.getNumLayers());
  }

  @Test
  public void testControllerRunScript() {
    Appendable out = new StringBuilder();
    StringBuilder log = new StringBuilder();
    MultiLayeredEnhanceInterface model = new MultiLayerEnhanceModel();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out, log);

    String command = "add hi # will ignore everything\n add in #hi\n save out";

    try {
      FileWriter fileWriter = new FileWriter("test.txt");
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.print(command);
      printWriter.close();
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Cannot write to file.");
    }

    File f = new File("test.txt");
    mockController.onRunScript(f);

    assertEquals(log.toString(), "add hi add in save out ");

    f.delete();
  }

  @Test
  public void testBlurEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireBlurEvent();
    assertEquals("handle blur event", out.toString());
  }

  @Test
  public void testSharpenEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireSharpenEvent();
    assertEquals("handle sharpen event", out.toString());
  }

  @Test
  public void testGreyscaleEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireGreyscaleEvent();
    assertEquals("handle greyscale event", out.toString());
  }

  @Test
  public void testSepiaEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireSepiaEvent();
    assertEquals("handle sepia event", out.toString());
  }

  @Test
  public void testImportEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireImportEvent();
    assertEquals("handle import event", out.toString());
  }

  @Test
  public void testExportEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireExportEvent();
    assertEquals("handle export event", out.toString());
  }

  @Test
  public void testSaveEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireSaveEvent();
    assertEquals("handle save event", out.toString());
  }

  @Test
  public void testLoadEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireLoadEvent();
    assertEquals("handle load layer event", out.toString());
  }

  @Test
  public void testGenerateEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireGenerateEvent();
    assertEquals("handle generate event", out.toString());
  }

  @Test
  public void testAddLayerEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireAddLayerEvent();
    assertEquals("handle add layer event", out.toString());
  }

  @Test
  public void testChangeCurEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireChangeCurEvent();
    assertEquals("handle change current event", out.toString());
  }

  @Test
  public void testChangeNameEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireChangeNameEvent();
    assertEquals("handle change name event", out.toString());
  }

  @Test
  public void testChangeLayerUpEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireChangeLayerUpEvent();
    assertEquals("handle change layer up event", out.toString());
  }

  @Test
  public void testChangeLayerDownEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireChangeLayerDownEvent();
    assertEquals("handle change layer down event", out.toString());
  }

  @Test
  public void testHideEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireHideEvent();
    assertEquals("handle hide event", out.toString());
  }

  @Test
  public void testVisibleEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireVisibleEvent();
    assertEquals("handle visible event", out.toString());
  }

  @Test
  public void testUndoEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireUndoEvent();
    assertEquals("handle undo event", out.toString());
  }

  @Test
  public void testRemoveEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireRemoveEvent();
    assertEquals("handle remove event", out.toString());
  }

  @Test
  public void testDeleteEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireDeleteEvent();
    assertEquals("handle delete event", out.toString());
  }

  @Test
  public void testRunScriptEvent() {
    Appendable out = new StringBuilder();
    mockController = new MockControllerView(mockView, new MultiLayerEnhanceModel(), out);
    mockView.fireRunScript();
    assertEquals("handle run script event", out.toString());
  }
}
