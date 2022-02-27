import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import controller.ChangeType;
import controller.CreateDirectoryConcrete;
import controller.ImportInterface;
import controller.InterfaceScripting;
import controller.LoadLayerCommand;
import controller.LoadLayerMock;
import controller.LoadMultiInterface;
import controller.SaveIm2DMock;
import controller.SaveMultiInterface;
import controller.ScriptingMock;
import controller.command.AddLayerCommand;
import controller.command.ChangeCurAndNameCommands;
import controller.command.ChangeVisCommand;
import controller.command.CreateDirCommand;
import controller.command.DeleteLayerCommand;
import controller.command.EnhanceCommandInterface;
import controller.command.ExportFileCommand;
import controller.command.FilterCommand;
import controller.command.GenerateImageCommand;
import controller.command.ImportFileCommand;
import controller.command.ImportFileMock;
import controller.command.LoadLayerCommandMock;
import controller.command.RemoveImageCommand;
import controller.command.RunScriptCommand;
import controller.command.RunScriptMock;
import controller.command.SaveCommand;
import controller.command.SaveCommandMock;
import controller.command.SwitchLayerPosCommand;
import controller.command.TransformCommand;
import controller.command.UndoCommand;
import controller.export.ExportInterface;
import controller.export.ExportModelCreator;
import controller.export.ExportType;
import controller.importing.ImportModelCreator;
import controller.importing.ImportType;
import controller.importing.PPMImportMock;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import model.FixedSizeStack;
import model.MultiLayerEnhanceModel;
import model.MultiLayeredEnhanceInterface;
import model.generateimage.BuilderCreator;
import model.generateimage.CheckerBoardBuilder;
import model.generateimage.GeneratePresets;
import model.generateimage.IGenerateImage;
import model.generateimage.ImageBuilderInterface;
import model.images.image.ImageCreator;
import model.images.image.ImageType;
import model.images.image.InterfaceImages;
import model.images.pixels.IPixels;
import model.images.pixels.PixelsRGB;
import model.layers.InterfaceLayers;
import model.layers.Layer2DMatrix;
import model.processes.IProcesses;
import model.processes.KernelType;
import model.processes.TransformType;
import model.processes.colortransform.TransformCreator;
import model.processes.kernelconvolution.KernelCreator;
import org.junit.Before;
import org.junit.Test;

/**
 * The combined tests for processes.
 */
public class ProcessTests {

  /**
   * Test for the transform enum.
   */
  public static class TransformTypeTest {

    @Test
    public void getMatrixSepia() {
      List<Double> array1 = new ArrayList<>();
      array1.add(0.393);
      array1.add(0.769);
      array1.add(0.189);
      List<Double> array2 = new ArrayList<>();
      array2.add(0.349);
      array2.add(0.686);
      array2.add(0.168);
      List<Double> array3 = new ArrayList<>();
      array3.add(0.272);
      array3.add(0.534);
      array3.add(0.131);

      List<List<Double>> matrix = new ArrayList<>();
      matrix.add(array1);
      matrix.add(array2);
      matrix.add(array3);

      assertEquals(matrix, TransformType.SEPIA.getMatrix());
    }

    @Test
    public void getMatrixGreyscale() {
      List<Double> array1 = new ArrayList<>();
      array1.add(0.2126);
      array1.add(0.7152);
      array1.add(0.0722);

      List<List<Double>> matrix = new ArrayList<>();
      matrix.add(array1);
      matrix.add(array1);
      matrix.add(array1);

      assertEquals(matrix, TransformType.GREYSCALE.getMatrix());
    }
  }

  /**
   * Tests methods in {@code TransformSepia} class.
   */
  public static class TransformSepiaTest {

    IProcesses sepia;
    InterfaceImages img;
    List<List<Double>> matrix;

    @Before
    public void setup() {
      sepia = TransformCreator.create(TransformType.SEPIA);
      img = ImageCreator.create(ImageType.MATRIX2D);
      matrix = new ArrayList<>();
      IPixels pix = new PixelsRGB(126, 39, 222);
      List<IPixels> lop = new ArrayList<>();
      lop.add(pix);
      lop.add(pix);
      lop.add(pix);
      List<List<IPixels>> lolop = new ArrayList<>();
      lolop.add(lop);
      lolop.add(lop);
      lolop.add(lop);
      img.initiateImage(lolop);
      List<Double> listOfDouble = new ArrayList<>();
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullImageCannotApply() {
      sepia.apply(null, matrix, 0, 255);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatrixCannotApply() {
      sepia.apply(img, null, 0, 255);
    }

    @Test
    public void applyToPurpleImage() {
      IPixels pPix = new PixelsRGB(126, 39, 222);
      assertEquals(pPix, img.getPixel(0, 0));
      assertEquals(pPix, img.getPixel(0, 1));
      assertEquals(pPix, img.getPixel(0, 2));
      assertEquals(pPix, img.getPixel(1, 0));
      assertEquals(pPix, img.getPixel(1, 1));
      assertEquals(pPix, img.getPixel(1, 2));
      assertEquals(pPix, img.getPixel(2, 0));
      assertEquals(pPix, img.getPixel(2, 1));
      assertEquals(pPix, img.getPixel(2, 2));

      InterfaceImages image = sepia.apply(img, matrix, 0, 255);

      IPixels wPix = new PixelsRGB(255, 255, 255);
      assertEquals(wPix, image.getPixel(0, 0));
      assertEquals(wPix, image.getPixel(0, 1));
      assertEquals(wPix, image.getPixel(0, 2));
      assertEquals(wPix, image.getPixel(1, 0));
      assertEquals(wPix, image.getPixel(1, 1));
      assertEquals(wPix, image.getPixel(1, 2));
      assertEquals(wPix, image.getPixel(2, 0));
      assertEquals(wPix, image.getPixel(2, 1));
      assertEquals(wPix, image.getPixel(2, 2));
    }
  }

  /**
   * Tests methods in {@code TransformGreyscale} class.
   */
  public static class TransformGreyscaleTest {

    IProcesses grey;
    InterfaceImages img;
    List<List<Double>> matrix;

    @Before
    public void setup() {
      grey = TransformCreator.create(TransformType.GREYSCALE);
      img = ImageCreator.create(ImageType.MATRIX2D);
      matrix = new ArrayList<>();
      IPixels pix = new PixelsRGB(126, 39, 222);
      List<IPixels> lop = new ArrayList<>();
      lop.add(pix);
      lop.add(pix);
      lop.add(pix);
      List<List<IPixels>> lolop = new ArrayList<>();
      lolop.add(lop);
      lolop.add(lop);
      lolop.add(lop);
      img.initiateImage(lolop);
      List<Double> listOfDouble = new ArrayList<>();
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullImageCannotApply() {
      grey.apply(null, matrix, 0, 255);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatrixCannotApply() {
      grey.apply(img, null, 0, 255);
    }

    @Test
    public void applyToPurpleImage() {
      IPixels pPix = new PixelsRGB(126, 39, 222);
      assertEquals(pPix, img.getPixel(0, 0));
      assertEquals(pPix, img.getPixel(0, 1));
      assertEquals(pPix, img.getPixel(0, 2));
      assertEquals(pPix, img.getPixel(1, 0));
      assertEquals(pPix, img.getPixel(1, 1));
      assertEquals(pPix, img.getPixel(1, 2));
      assertEquals(pPix, img.getPixel(2, 0));
      assertEquals(pPix, img.getPixel(2, 1));
      assertEquals(pPix, img.getPixel(2, 2));

      InterfaceImages image = grey.apply(img, matrix, 0, 255);

      IPixels wPix = new PixelsRGB(255, 255, 255);
      assertEquals(wPix, image.getPixel(0, 0));
      assertEquals(wPix, image.getPixel(0, 1));
      assertEquals(wPix, image.getPixel(0, 2));
      assertEquals(wPix, image.getPixel(1, 0));
      assertEquals(wPix, image.getPixel(1, 1));
      assertEquals(wPix, image.getPixel(1, 2));
      assertEquals(wPix, image.getPixel(2, 0));
      assertEquals(wPix, image.getPixel(2, 1));
      assertEquals(wPix, image.getPixel(2, 2));
    }
  }

  /**
   * tests methods in {@code TransformCreator} class.
   */
  public static class TransformCreatorTest {

    IProcesses sepia;
    IProcesses grey;

    @Before
    public void setup() {
      sepia = TransformCreator.create(TransformType.SEPIA);
      grey = TransformCreator.create(TransformType.GREYSCALE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullTransformType() {
      TransformCreator.create(null);
    }

    @Test
    public void sepiaTransformType() {
      assertEquals(sepia.getClass(), TransformCreator.create(TransformType.SEPIA).getClass());
    }

    @Test
    public void greyscaleTransformType() {
      assertEquals(grey.getClass(), TransformCreator.create(TransformType.GREYSCALE).getClass());
    }
  }

  /**
   * Tests the methods inside UndoCommand method.
   */
  public static class UndoCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface undoCommand = new UndoCommand();
      undoCommand.apply(null);
    }

    @Test
    public void validapply() {
      model.addLayer("hi");
      model.setName("hi");
      model.setName("bye");
      EnhanceCommandInterface undoCommand = new UndoCommand();
      undoCommand.apply(model);
      assertEquals("hi", model.getName());
    }
  }

  /**
   * Tests the methods inside GenerateImageCommand class.
   */
  public static class GenerateImageCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new GenerateImageCommand(null, -1, -1, -1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new GenerateImageCommand(GeneratePresets.CHECKERBOARD,
          -1, -1, -1.0);
      cmd.apply(null);
    }

    @Test
    public void generateTest() {
      EnhanceCommandInterface cmd = new GenerateImageCommand(GeneratePresets.CHECKERBOARD,
          -1, -1, -1.0);
      model.addLayer("hello");
      cmd.apply(model);

      assertEquals(1, model.getNumLayers());
    }
  }

  /**
   * Tests the methods inside FilterCommand class.
   */
  public static class FilterCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNull() {
      EnhanceCommandInterface cmd = new FilterCommand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new FilterCommand(KernelType.BLUR);
      cmd.apply(null);
    }

    @Test
    public void blurTest() {
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

      EnhanceCommandInterface cmd = new FilterCommand(KernelType.BLUR);
      cmd.apply(model);

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
    public void sharpenTest() {
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

      model.addLayer("hi");
      model.setImage(image);

      EnhanceCommandInterface cmd = new FilterCommand(KernelType.SHARPEN);
      cmd.apply(model);

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
  }

  /**
   * Tests the methods for {@code ConvolutionSharpen} class.
   */
  public static class ConvolutionSharpenTest {

    IProcesses sharp;
    InterfaceImages img;
    List<List<Double>> matrix;

    @Before
    public void setup() {
      sharp = KernelCreator.create(KernelType.SHARPEN);
      img = ImageCreator.create(ImageType.MATRIX2D);
      matrix = new ArrayList<>();
      IPixels pix = new PixelsRGB(240, 100, 185);
      List<IPixels> lop = new ArrayList();
      lop.add(pix);
      lop.add(pix);
      lop.add(pix);
      List<List<IPixels>> lolop = new ArrayList<>();
      lolop.add(lop);
      lolop.add(lop);
      lolop.add(lop);
      img.initiateImage(lolop);
      List<Double> listOfDouble = new ArrayList<>();
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullImageCannotApply() {
      sharp.apply(null, matrix, 0, 255);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatrixCannotApply() {
      sharp.apply(img, null, 0, 255);
    }

    @Test
    public void applyToPinkImage() {
      IPixels pPix = new PixelsRGB(240, 100, 185);
      assertEquals(pPix, img.getPixel(0, 0));
      assertEquals(pPix, img.getPixel(0, 1));
      assertEquals(pPix, img.getPixel(0, 2));
      assertEquals(pPix, img.getPixel(1, 0));
      assertEquals(pPix, img.getPixel(1, 1));
      assertEquals(pPix, img.getPixel(1, 2));
      assertEquals(pPix, img.getPixel(2, 0));
      assertEquals(pPix, img.getPixel(2, 1));
      assertEquals(pPix, img.getPixel(2, 2));

      InterfaceImages image = sharp.apply(img, matrix, 0, 255);

      IPixels wPix = new PixelsRGB(255, 255, 255);
      assertEquals(wPix, image.getPixel(0, 0));
      assertEquals(wPix, image.getPixel(0, 1));
      assertEquals(wPix, image.getPixel(0, 2));
      assertEquals(wPix, image.getPixel(1, 0));
      assertEquals(wPix, image.getPixel(1, 1));
      assertEquals(wPix, image.getPixel(1, 2));
      assertEquals(wPix, image.getPixel(2, 0));
      assertEquals(wPix, image.getPixel(2, 1));
      assertEquals(wPix, image.getPixel(2, 2));
    }
  }

  /**
   * Tests the methods for {@code ConvolutionBlur} class.
   */
  public static class ConvolutionBlurTest {

    IProcesses blur;
    InterfaceImages img;
    List<List<Double>> matrix;

    @Before
    public void setup() {
      blur = KernelCreator.create(KernelType.BLUR);
      img = ImageCreator.create(ImageType.MATRIX2D);
      matrix = new ArrayList<>();
      IPixels pix = new PixelsRGB(240, 100, 185);
      List<IPixels> lop = new ArrayList<>();
      lop.add(pix);
      lop.add(pix);
      lop.add(pix);
      List<List<IPixels>> lolop = new ArrayList<>();
      lolop.add(lop);
      lolop.add(lop);
      lolop.add(lop);
      img.initiateImage(lolop);
      List<Double> listOfDouble = new ArrayList<>();
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      listOfDouble.add(1.0);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
      matrix.add(listOfDouble);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullImageCannotApply() {
      blur.apply(null, matrix, 0, 255);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatrixCannotApply() {
      blur.apply(img, null, 0, 255);
    }

    @Test
    public void applyToPinkImage() {
      IPixels pPix = new PixelsRGB(240, 100, 185);
      assertEquals(pPix, img.getPixel(0, 0));
      assertEquals(pPix, img.getPixel(0, 1));
      assertEquals(pPix, img.getPixel(0, 2));
      assertEquals(pPix, img.getPixel(1, 0));
      assertEquals(pPix, img.getPixel(1, 1));
      assertEquals(pPix, img.getPixel(1, 2));
      assertEquals(pPix, img.getPixel(2, 0));
      assertEquals(pPix, img.getPixel(2, 1));
      assertEquals(pPix, img.getPixel(2, 2));

      InterfaceImages image = blur.apply(img, matrix, 0, 255);

      IPixels wPix = new PixelsRGB(255, 255, 255);
      assertEquals(wPix, image.getPixel(0, 0));
      assertEquals(wPix, image.getPixel(0, 1));
      assertEquals(wPix, image.getPixel(0, 2));
      assertEquals(wPix, image.getPixel(1, 0));
      assertEquals(wPix, image.getPixel(1, 1));
      assertEquals(wPix, image.getPixel(1, 2));
      assertEquals(wPix, image.getPixel(2, 0));
      assertEquals(wPix, image.getPixel(2, 1));
      assertEquals(wPix, image.getPixel(2, 2));
    }
  }

  /**
   * Tests the methods inside AddLayerCommand class.
   */
  public static class AddLayerCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface addCommand = new AddLayerCommand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface addCommand = new AddLayerCommand("name");
      addCommand.apply(null);
    }

    @Test
    public void validapply() {
      EnhanceCommandInterface addCommand = new AddLayerCommand("layer 1");
      addCommand.apply(model);
      InterfaceLayers layer = new Layer2DMatrix();
      layer.setName("layer 1");
      layer.setVisibility(true);
      assertEquals(model.getCurrentLayer(), layer);
    }
  }

  /**
   * Tests methods in {@code PPMImport} class.
   */
  public static class PPMImportTest {

    InterfaceImages img;
    ImportInterface ppm;

    @Before
    public void setup() {
      img = ImageCreator.create(ImageType.MATRIX2D);
      ppm = ImportModelCreator.create(ImportType.PPM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidBcFilenameIsNull() {
      ppm.importImage(null);
    }

    @Test
    public void importDotsImage() {
      String dots = "P3\n# Created by GIMP version 2.10.24 PNM plug-in\n25 25\n255\n142"
          + "\n85\n165\n130\n136\n168\n111\n179\n172\n96\n201\n174\n104\n190\n173\n123\n"
          + "154\n170\n139\n103\n166\n145\n62\n164\n146\n50\n165\n140\n75\n172\n121\n11"
          + "6\n187\n89\n151\n206\n60\n168\n216\n77\n160\n211\n110\n130\n195\n134\n90\n1"
          + "77\n144\n61\n167\n143\n79\n165\n132\n129\n168\n112\n177\n172\n91\n206\n175\n"
          + "91\n206\n175\n112\n177\n172\n131\n130\n168\n139\n93\n171\n141\n90\n165\n127\n"
          + "145\n169\n104\n190\n173\n86\n212\n176\n95\n201\n174\n118\n164\n171\n136\n113"
          + "\n167\n145\n65\n165\n146\n49\n165\n142\n67\n169\n128\n103\n182\n107\n134\n19"
          + "6\n89\n151\n206\n99\n142\n201\n121\n117\n188\n138\n80\n173\n145\n57\n166\n144"
          + "\n75\n165\n133\n122\n168\n116\n167\n171\n99\n196\n174\n100\n195\n174\n117\n16"
          + "7\n171\n134\n122\n167\n143\n80\n166\n143\n79\n165\n132\n128\n168\n116\n169\n1"
          + "71\n102\n191\n174\n107\n182\n174\n119\n153\n174\n134\n109\n171\n143\n72\n168\n"
          + "147\n47\n164\n144\n57\n167\n137\n83\n174\n128\n103\n182\n121\n116\n187\n125\n"
          + "109\n185\n134\n92\n177\n142\n67\n169\n146\n50\n165\n142\n77\n168\n135\n113\n17"
          + "2\n122\n146\n175\n117\n163\n174\n118\n162\n172\n129\n139\n168\n137\n110\n167\n"
          + "141\n87\n165\n145\n65\n165\n138\n102\n167\n127\n135\n172\n115\n158\n178\n109\n"
          + "160\n184\n109\n150\n188\n122\n123\n184\n134\n90\n176\n144\n60\n167\n148\n45\n"
          + "164\n145\n57\n167\n142\n67\n169\n140\n75\n172\n141\n70\n170\n143\n62\n168\n14"
          + "6\n49\n165\n143\n63\n168\n134\n95\n177\n121\n123\n186\n111\n141\n190\n116\n141"
          + "\n184\n124\n130\n177\n133\n118\n170\n136\n118\n167\n133\n126\n167\n147\n47\n16"
          + "4\n143\n72\n166\n134\n102\n174\n114\n137\n189\n92\n157\n202\n81\n162\n207\n102"
          + "\n141\n199\n126\n107\n184\n143\n68\n170\n147\n49\n165\n14"
          + "8\n43\n164\n146\n47\n165\n146\n48\n165\n146\n48\n165\n147"
          + "\n44\n164\n146\n49\n165\n141\n72\n171\n123\n114\n186\n97\n"
          + "146\n202\n77\n162\n210\n96\n149\n201\n119\n128\n186\n131\n"
          + "122\n172\n125\n149\n170\n114\n172\n171\n147\n44\n164\n144"
          + "\n57\n167\n133\n95\n177\n109\n135\n196\n72\n163\n212\n54\n"
          + "171\n218\n86\n154\n207\n120\n120\n188\n138\n81\n172\n146\n"
          + "55\n165\n147\n44\n164\n147\n43\n164\n147\n43\n164\n147\n4"
          + "3\n164\n147\n43\n164\n146\n49\n165\n137\n82\n174\n116\n12"
          + "5\n192\n77\n160\n211\n46\n173\n219\n77\n161\n211\n113\n13"
          + "5\n191\n125\n137\n175\n114\n173\n172\n96\n201\n174\n147\n"
          + "46\n164\n145\n56\n166\n137\n83\n174\n118\n121\n190\n93\n1"
          + "48\n204\n81\n157\n209\n101\n145\n198\n123\n122\n183\n136\n"
          + "104\n171\n142\n83\n166\n144\n69\n165\n147\n47\n164\n146\n"
          + "46\n165\n146\n46\n165\n147\n46\n164\n146\n49\n165\n141\n7"
          + "2\n171\n122\n114\n186\n97\n145\n202\n77\n160\n211\n97\n14"
          + "6\n202\n119\n126\n185\n128\n133\n172\n114\n173\n172\n96\n"
          + "201\n174\n145\n63\n164\n144\n67\n165\n140\n78\n170\n131\n"
          + "99\n179\n121\n116\n188\n114\n130\n190\n118\n138\n184\n121"
          + "\n145\n176\n123\n150\n171\n131\n133\n168\n137\n108\n167\n"
          + "142\n77\n168\n143\n63\n168\n142\n67\n169\n144\n60\n168\n1"
          + "45\n53\n166\n143\n64\n168\n133\n92\n177\n122\n114\n186\n1"
          + "15\n125\n192\n122\n115\n186\n131\n103\n177\n134\n115\n170"
          + "\n126\n147\n169\n114\n172\n171\n138\n107\n166\n138\n107\n"
          + "166\n140\n95\n166\n140\n84\n169\n139\n82\n171\n134\n104\n"
          + "173\n125\n143\n172\n111\n176\n173\n101\n193\n173\n113\n17"
          + "5\n172\n127\n141\n171\n133\n106\n174\n130\n101\n180\n128\n"
          + "104\n182\n133\n93\n178\n140\n75\n172\n145\n54\n166\n143\n"
          + "63\n168\n141\n72\n171\n137\n82\n174\n141\n72\n171\n142\n7"
          + "0\n168\n143\n82\n165\n138\n108\n166\n133\n125\n167\n121\n"
          + "159\n170\n121\n159\n170\n130\n136\n168\n139\n102\n166\n14"
          + "3\n73\n165\n139\n99\n167\n122\n154\n170\n98\n197\n174\n83"
          + "\n215\n176\n98\n198\n174\n117\n159\n174\n120\n132\n184\n1"
          + "08\n135\n196\n101\n141\n200\n114\n126\n192\n131\n98\n180\n"
          + "143\n64\n169\n147\n47\n165\n146\n49\n165\n146\n49\n165\n1"
          + "46\n49\n165\n147\n46\n164\n145\n58\n165\n144\n70\n166\n14"
          + "2\n78\n166\n100\n195\n174\n100\n195\n174\n117\n167\n171\n"
          + "133\n122\n168\n142\n82\n166\n140\n94\n166\n126\n145\n169\n"
          + "106\n186\n173\n93\n204\n175\n106\n187\n174\n118\n155\n176"
          + "\n110\n141\n191\n83\n156\n208\n69\n164\n213\n95\n147\n203"
          + "\n123\n114\n186\n140\n76\n171\n146\n53\n165\n147\n44\n164"
          + "\n147\n43\n164\n146\n49\n165\n143\n62\n168\n141\n70\n170\n"
          + "140\n77\n172\n142\n70\n169\n91\n206\n175\n91\n206\n175\n1"
          + "10\n178\n172\n128\n136\n172\n135\n99\n172\n136\n99\n172\n"
          + "130\n127\n172\n123\n153\n170\n117\n166\n171\n123\n153\n17"
          + "1\n124\n135\n176\n111\n136\n192\n83\n155\n208\n69\n164\n2"
          + "13\n95\n150\n202\n121\n122\n186\n136\n96\n171\n143\n76\n1"
          + "66\n145\n63\n164\n146\n52\n165\n142\n67\n169\n134\n92\n17"
          + "7\n125\n109\n185\n121\n116\n187\n128\n103\n182\n107\n184\n"
          + "172\n106\n185\n173\n115\n163\n175\n120\n139\n181\n117\n12"
          + "7\n188\n118\n123\n189\n124\n121\n182\n131\n116\n174\n134\n"
          + "117\n168\n138\n103\n168\n135\n101\n172\n124\n112\n185\n10"
          + "8\n133\n196\n100\n144\n199\n111\n141\n190\n122\n136\n179\n"
          + "129\n134\n171\n134\n122\n168\n139\n101\n166\n142\n76\n167"
          + "\n138\n82\n173\n121\n117\n188\n99\n142\n201\n89\n151\n206"
          + "\n107\n134\n196\n127\n141\n169\n125\n144\n171\n121\n140\n"
          + "178\n107\n144\n193\n87\n154\n206\n87\n152\n207\n110\n132\n"
          + "195\n131\n103\n179\n141\n78\n168\n144\n66\n166\n143\n68\n"
          + "168\n137\n84\n174\n131\n100\n180\n125\n117\n182\n122\n138"
          + "\n178\n117\n163\n174\n110\n178\n172\n117\n167\n171\n130\n"
          + "136\n168\n138\n98\n168\n133\n96\n177\n110\n130\n195\n77\n"
          + "160\n211\n60\n168\n216\n89\n151\n206\n141\n88\n166\n137\n"
          + "99\n171\n124\n118\n183\n97\n147\n201\n63\n167\n215\n63\n1"
          + "67\n215\n98\n144\n202\n127\n107\n183\n141\n74\n170\n144\n"
          + "65\n165\n144\n67\n166\n143\n70\n167\n141\n74\n168\n137\n9"
          + "9\n170\n126\n144\n171\n106\n185\n173\n91\n206\n175\n100\n"
          + "195\n174\n121\n159\n170\n136\n111\n168\n135\n94\n175\n116"
          + "\n123\n191\n90\n151\n206\n77\n160\n211\n99\n142\n201\n145"
          + "\n54\n165\n142\n68\n168\n132\n98\n179\n110\n130\n195\n87\n"
          + "152\n207\n87\n152\n207\n110\n130\n195\n131\n100\n179\n139"
          + "\n93\n169\n138\n101\n167\n136\n114\n167\n139\n101\n166\n1"
          + "40\n89\n166\n139\n94\n167\n127\n142\n169\n107\n185\n173\n"
          + "91\n206\n175\n100\n195\n174\n121\n159\n170\n137\n109\n167"
          + "\n139\n84\n171\n129\n103\n181\n116\n123\n191\n110\n130\n1"
          + "95\n121\n117\n188\n147\n43\n164\n145\n54\n166\n138\n79\n1"
          + "73\n128\n104\n182\n119\n119\n189\n119\n119\n189\n128\n105"
          + "\n182\n135\n98\n173\n133\n122\n169\n124\n151\n169\n117\n1"
          + "66\n171\n124\n151\n169\n133\n124\n169\n136\n107\n170\n127"
          + "\n132\n173\n116\n163\n174\n109\n180\n174\n116\n168\n172\n"
          + "130\n136\n168\n141\n93\n166\n144\n65\n166\n141\n73\n171\n"
          + "137\n84\n175\n134\n90\n177\n138\n83\n173\n147\n43\n164\n1"
          + "47\n44\n164\n145\n55\n166\n142\n68\n170\n139\n77\n172\n13"
          + "9\n77\n172\n141\n74\n170\n140\n94\n167\n127\n145\n169\n10"
          + "6\n186\n173\n93\n204\n175\n106\n186\n173\n120\n152\n174\n"
          + "123\n128\n181\n114\n138\n188\n112\n148\n186\n116\n149\n18"
          + "0\n127\n132\n174\n137\n105\n168\n144\n70\n165\n147\n49\n1"
          + "64\n146\n49\n165\n145\n57\n166\n142\n74\n167\n141\n84\n16"
          + "7\n147\n48\n164\n147\n48\n164\n147\n46\n164\n146\n46\n165"
          + "\n146\n49\n165\n146\n49\n165\n145\n56\n165\n140\n97\n166\n"
          + "123\n153\n170\n98\n197\n174\n83\n215\n176\n97\n198\n175\n"
          + "113\n165\n178\n106\n147\n193\n87\n155\n206\n86\n157\n206\n"
          + "108\n140\n194\n129\n110\n179\n141\n76\n168\n146\n51\n165\n"
          + "147\n43\n164\n147\n44\n164\n145\n66\n165\n139\n101\n166\n"
          + "132\n129\n168\n143\n76\n165\n143\n76\n165\n145\n67\n165\n"
          + "146\n55\n164\n146\n47\n165\n146\n46\n165\n146\n53\n164\n1"
          + "42\n84\n165\n129\n137\n168\n113\n175\n171\n101\n193\n174\n"
          + "110\n177\n174\n114\n156\n181\n96\n153\n200\n63\n168\n215\n"
          + "63\n167\n215\n98\n145\n202\n127\n108\n183\n142\n69\n170\n"
          + "146\n46\n165\n147\n43\n164\n146\n50\n164\n143\n79\n165\n1"
          + "32\n128\n168\n116\n168\n171\n133\n125\n167\n133\n125\n167"
          + "\n137\n110\n167\n140\n89\n168\n142\n72\n169\n143\n65\n169"
          + "\n144\n59\n167\n143\n73\n166\n138\n107\n166\n131\n133\n16"
          + "8\n125\n148\n170\n127\n138\n171\n124\n130\n179\n109\n137\n"
          + "194\n87\n152\n207\n87\n152\n207\n110\n130\n195\n132\n96\n"
          + "179\n143\n62\n168\n146\n46\n165\n147\n43\n164\n146\n52\n1"
          + "64\n141\n91\n165\n127\n145\n169\n104\n190\n173\n114\n172\n"
          + "171\n114\n173\n172\n122\n152\n173\n127\n126\n176\n127\n11"
          + "0\n181\n129\n102\n181\n135\n88\n176\n142\n70\n170\n143\n7"
          + "3\n166\n143\n81\n165\n141\n93\n166\n141\n86\n167\n136\n94"
          + "\n173\n128\n105\n182\n119\n119\n189\n119\n119\n189\n128\n"
          + "105\n182\n138\n80\n173\n145\n54\n166\n147\n43\n164\n147\n"
          + "46\n165\n145\n58\n166\n140\n90\n168\n128\n139\n170\n110\n"
          + "179\n173\n96\n201\n174\n95\n201\n175\n107\n178\n176\n111\n"
          + "154\n185\n102\n145\n197\n104\n138\n198\n120\n118\n188\n13"
          + "5\n88\n176\n144\n59\n167\n146\n53\n164\n146\n51\n164\n146"
          + "\n52\n164\n145\n56\n166\n142\n68\n170\n138\n81\n172\n138\n"
          + "86\n172\n140\n82\n170\n143\n70\n166\n146\n54\n164\n146\n4"
          + "8\n165\n144\n61\n168\n139\n77\n172\n132\n101\n176\n126\n1"
          + "30\n176\n120\n152\n174\n95\n201\n174\n94\n202\n175\n104\n"
          + "181\n179\n98\n164\n193\n77\n165\n209\n77\n161\n211\n104\n"
          + "137\n198\n129\n101\n181\n143\n65\n169\n146\n46\n165\n147\n"
          + "43\n164\n147\n43\n164\n147\n43\n164\n145\n55\n165\n142\n8"
          + "2\n166\n139\n100\n167\n136\n112\n167\n139\n103\n166\n142\n"
          + "87\n165\n144\n69\n167\n137\n84\n174\n125\n109\n185\n113\n"
          + "129\n193\n111\n137\n192\n120\n132\n184\n114\n172\n172\n11"
          + "2\n174\n173\n112\n161\n179\n100\n156\n194\n77\n163\n210\n"
          + "77\n160\n211\n104\n137\n198\n129\n101\n181\n143\n65\n169\n"
          + "146\n46\n165\n147\n43\n164\n147\n43\n164\n147\n45\n164\n1"
          + "44\n72\n165\n136\n113\n167\n126\n146\n169\n119\n163\n170\n"
          + "123\n154\n170\n132\n127\n168\n138\n96\n169\n130\n102\n180"
          + "\n108\n133\n196\n83\n155\n208\n83\n156\n208\n108\n137\n195";
      img = ppm.importImage("dots.ppm");
      ImportInterface mock = new PPMImportMock();
      InterfaceImages imageMock = mock.importImage(dots);
      assertEquals(img, imageMock);
    }
  }

  /**
   * Tests the methods inside RemoveImageCommand class.
   */
  public static class RemoveImageCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new RemoveImageCommand();
      cmd.apply(null);
    }

    @Test
    public void deleteImageTest() {
      EnhanceCommandInterface cmd = new RemoveImageCommand();
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
      cmd.apply(model);
      assertTrue(model.getCurrentLayer().isImageEmpty());
    }
  }

  /**
   * Tests methods for {@code PixelsRGB} class.
   */
  public static class PixelsRGBTest {

    PixelsRGB aPixel;

    @Before
    public void setup() {
      aPixel = new PixelsRGB(10, 230, 128);
    }

    @Test
    public void clampsOnlyRedValue() {
      assertEquals(10, aPixel.getRed());
      assertEquals(230, aPixel.getGreen());
      assertEquals(128, aPixel.getBlue());
      aPixel.clampAll(100, 255);
      assertEquals(100, aPixel.getRed());
      assertEquals(230, aPixel.getGreen());
      assertEquals(128, aPixel.getBlue());
    }

    @Test
    public void clampsRedAndGreen() {
      assertEquals(10, aPixel.getRed());
      assertEquals(230, aPixel.getGreen());
      assertEquals(128, aPixel.getBlue());
      aPixel.clampAll(93, 210);
      assertEquals(93, aPixel.getRed());
      assertEquals(210, aPixel.getGreen());
      assertEquals(128, aPixel.getBlue());
    }

    @Test
    public void getsRedOfAPixel() {
      int redValue = 10;
      assertEquals(redValue, aPixel.getRed());
    }

    @Test
    public void getsGreenOfAPixel() {
      int greenValue = 230;
      assertEquals(greenValue, aPixel.getGreen());
    }

    @Test
    public void getsBlueOfAPixel() {
      int blueValue = 128;
      assertEquals(blueValue, aPixel.getBlue());
    }

    @Test
    public void getsCopyOfAPixel() {
      IPixels copyAPixel = aPixel.copyPixel();
      assertEquals(aPixel.getRed(), copyAPixel.getRed());
      assertEquals(aPixel.getGreen(), copyAPixel.getGreen());
      assertEquals(aPixel.getBlue(), copyAPixel.getBlue());
      assertEquals(true, aPixel.equals(copyAPixel));
    }

    @Test
    public void notSameObjectToAPixel() {
      int anInt = 30;
      assertEquals(false, aPixel.equals(anInt));
    }

    @Test
    public void notSameColorValues() {
      PixelsRGB notAPixel = new PixelsRGB(44, 14, 200);
      assertEquals(false, aPixel.equals(notAPixel));
    }

    @Test
    public void sameColorValue() {
      PixelsRGB sameColors = new PixelsRGB(10, 230, 128);
      assertEquals(true, aPixel.equals(sameColors));
    }

    @Test
    public void equalsToItself() {
      assertEquals(true, aPixel.equals(aPixel));
    }

    @Test
    public void correctHashCode() {
      assertEquals(Objects.hash(aPixel.getRed() + aPixel.getGreen() + aPixel.getBlue()),
          aPixel.hashCode());
    }
  }

  /**
   * Tests methods in the ChangeCurAndNameCommands class.
   */
  public static class ChangeCurAndNameCommandsTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new ChangeCurAndNameCommands(null, "hi");
      EnhanceCommandInterface cmd2 = new ChangeCurAndNameCommands(ChangeType.NAME, null);
    }

    @Test
    public void changeName() {
      model.addLayer("hi");
      assertEquals("hi", model.getName());
      EnhanceCommandInterface cmd = new ChangeCurAndNameCommands(ChangeType.NAME, "name");
      cmd.apply(model);
      assertEquals("name", model.getName());
    }

    @Test
    public void changeCurrent() {
      model.addLayer("hi");
      model.addLayer("hello");
      InterfaceLayers curLayer = new Layer2DMatrix();
      curLayer.setName("hi");
      curLayer.setVisibility(true);
      assertEquals(curLayer, model.getCurrentLayer());
      EnhanceCommandInterface cmd =
          new ChangeCurAndNameCommands(ChangeType.CURRENT, "hello");
      cmd.apply(model);

      curLayer = new Layer2DMatrix();
      curLayer.setName("hello");
      curLayer.setVisibility(true);
      assertEquals(curLayer, model.getCurrentLayer());
    }
  }

  /**
   * Tests the methods inside ChangeVisCommand class.
   */
  public static class ChangeVisCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructors() {
      EnhanceCommandInterface cmd = new ChangeVisCommand(null, "hi");
      EnhanceCommandInterface cmd2 = new ChangeVisCommand("hi", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new ChangeVisCommand("hi", "hidden");
      cmd.apply(null);
    }

    @Test
    public void hideTest() {
      model.addLayer("hi");
      assertTrue(model.getCurrentLayer().getVisibilityStatus());
      EnhanceCommandInterface cmd = new ChangeVisCommand("hi", "hidden");
      cmd.apply(model);
      assertFalse(model.getCurrentLayer().getVisibilityStatus());
    }

    @Test
    public void visibleTest() {
      model.addLayer("hi");
      model.changeVisibility("hi", false);
      assertFalse(model.getCurrentLayer().getVisibilityStatus());
      EnhanceCommandInterface cmd = new ChangeVisCommand("hi", "visible");
      cmd.apply(model);
      assertTrue(model.getCurrentLayer().getVisibilityStatus());
    }
  }

  /**
   * Tests the methods inside CreateDirCommand class.
   */
  public static class CreateDirCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new CreateDirCommand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new CreateDirCommand("layers");
      cmd.apply(null);
    }

    @Test
    public void runCreateDir() {
      File file = new File("testFolder");
      if (file.exists()) {
        file.delete();
      }

      boolean bool = file.mkdirs();

      assertTrue(bool);
    }
  }

  /**
   * Tests the methods inside LoadLayerCommand class.
   */
  public static class LoadLayerCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new LoadLayerCommand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new LoadLayerCommand("hi");
      cmd.apply(null);
    }

    @Test
    public void loadLayerTest() {
      InterfaceLayers layer1 = new Layer2DMatrix();
      InterfaceLayers layer2 = new Layer2DMatrix();
      layer1.setName("layer 1");
      layer2.setName("layer 2");
      InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
      List<List<IPixels>> loLoPixels = new ArrayList<>();
      List<IPixels> loPixels = new ArrayList<>();
      loPixels.add(new PixelsRGB(255, 0, 0));
      loPixels.add(new PixelsRGB(255, 0, 0));

      loLoPixels.add(loPixels);
      loLoPixels.add(loPixels);

      image.initiateImage(loLoPixels);
      layer1.setImageInLayer(image);

      String files1 = "layer 1\nvisible\n0\n2\n2\n255\n0\n0\n255\n0\n"
          + "0\n255\n0\n0\n255\n0\n0";
      String files2 = "layer 2\nvisible\n0\n\n0\n0";

      List<InterfaceLayers> expected = new ArrayList<>();
      expected.add(layer1);
      expected.add(layer2);

      EnhanceCommandInterface cmd = new LoadLayerCommandMock(files2, files1);
      cmd.apply(model);
      List<InterfaceLayers> actual = model.getAllLayers();

      assertEquals(actual.size(), expected.size());
      assertEquals(actual.get(0), expected.get(0));
      assertEquals(actual.get(1), expected.get(1));
    }
  }

  /**
   * Tests the methods inside TransformCommand class.
   */
  public static class TransformCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidConstructor() {
      EnhanceCommandInterface cmd = new TransformCommand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new TransformCommand(TransformType.GREYSCALE);
      cmd.apply(null);
    }

    @Test
    public void sepiaTest() {
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

      EnhanceCommandInterface cmd = new TransformCommand(TransformType.SEPIA);
      cmd.apply(model);

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
  }

  /**
   * Tests the methods inside SwitchLayerPosCommand class.
   */
  public static class SwitchLayerPosCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface addCommand = new SwitchLayerPosCommand(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface switchCommand = new SwitchLayerPosCommand("", 0);
      switchCommand.apply(null);
    }

    @Test
    public void validapply() {
      model.addLayer("o");
      InterfaceLayers l1 = new Layer2DMatrix();
      l1.setName("o");

      model.addLayer("n");
      InterfaceLayers l2 = new Layer2DMatrix();
      l2.setName("n");

      assertEquals(Arrays.asList(l1, l2), model.getAllLayers());
      EnhanceCommandInterface switchCommand = new SwitchLayerPosCommand("o", 1);
      switchCommand.apply(model);
      assertEquals(Arrays.asList(l2, l1), model.getAllLayers());
    }
  }

  /**
   * Tests the methods inside Layer2dMatrix class.
   */
  public static class Layer2DMatrixTest {

    Layer2DMatrix layer;
    Layer2DMatrix madeL;
    InterfaceImages blue;

    @Before
    public void setup() {
      blue = ImageCreator.create(ImageType.MATRIX2D);
      IPixels pixel = new PixelsRGB(0, 0, 255);
      List<IPixels> listOfB = new ArrayList<>();
      listOfB.add(pixel);
      listOfB.add(pixel);
      List<List<IPixels>> bSquare = new ArrayList<>();
      bSquare.add(listOfB);
      bSquare.add(listOfB);
      blue.initiateImage(bSquare);

      layer = new Layer2DMatrix();
      madeL = new Layer2DMatrix("henri", true, blue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNameInvalid() {
      layer.setName(null);
    }

    @Test
    public void testSetName() {
      assertEquals("untitled layer", layer.getLayerName());
      layer.setName("Four");
      assertEquals("Four", layer.getLayerName());
    }

    @Test
    public void testChangeToInvisible() {
      assertEquals(true, layer.getVisibilityStatus());
      layer.setVisibility(false);
      assertEquals(false, layer.getVisibilityStatus());
    }

    @Test
    public void setRedImage() {
      assertEquals(null, layer.getLayerImage());

      InterfaceImages img = ImageCreator.create(ImageType.MATRIX2D);
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      layer.setImageInLayer(img);
      assertEquals(img, layer.getLayerImage());
    }

    @Test
    public void testNameOfLayer() {
      assertEquals("henri", madeL.getLayerName());
    }

    @Test
    public void testIfLayerIsVisible() {
      assertEquals(true, madeL.getVisibilityStatus());
    }

    @Test
    public void testImageLayer() {
      assertEquals(blue, madeL.getLayerImage());
    }

    @Test
    public void deepCopyOfImage() {
      InterfaceImages copy = ImageCreator.create(ImageType.MATRIX2D);
      IPixels pixel = new PixelsRGB(0, 0, 255);
      List<IPixels> listOfB = new ArrayList<>();
      listOfB.add(pixel);
      listOfB.add(pixel);
      List<List<IPixels>> bSquare = new ArrayList<>();
      bSquare.add(listOfB);
      bSquare.add(listOfB);
      copy.initiateImage(bSquare);
      assertEquals(true, blue.equals(copy));
      InterfaceLayers l = new Layer2DMatrix();
      l.setName("henri");
      l.setImageInLayer(copy);
      assertEquals(l, madeL.getDeepCopy());
    }

    @Test
    public void equalsDifObject() {
      assertEquals(false, madeL.equals("image"));
    }

    @Test
    public void equalsNullImageAndFullImage() {
      assertEquals(false, layer.equals(madeL));
    }

    @Test
    public void equalsFullImageAndNullImage() {
      assertEquals(false, madeL.equals(layer));
    }

    @Test
    public void checkDeepCopyEquals() {
      assertEquals(true, madeL.equals(madeL));
    }

    @Test
    public void testHashCodeOfEmptyLayer() {
      assertEquals(1478, layer.hashCode());
    }

    @Test
    public void layersIsEmpty() {
      assertEquals(true, layer.isImageEmpty());
    }

    @Test
    public void layerHasImage() {
      assertEquals(false, madeL.isImageEmpty());
    }

  }

  /**
   * Tests the methods inside LoadIm2dMatrix class.
   */
  public static class LoadIm2dMatrixTest {

    LoadMultiInterface mock;

    @Before
    public void setup() {
      LoadMultiInterface mock = new LoadLayerMock();
    }

    @Test
    public void loadLayer() {
      InterfaceLayers layer1 = new Layer2DMatrix();
      InterfaceLayers layer2 = new Layer2DMatrix();
      layer1.setName("layer 1");
      layer2.setName("layer 2");
      InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);
      List<List<IPixels>> loLoPixels = new ArrayList<>();
      List<IPixels> loPixels = new ArrayList<>();
      loPixels.add(new PixelsRGB(255, 0, 0));
      loPixels.add(new PixelsRGB(255, 0, 0));

      loLoPixels.add(loPixels);
      loLoPixels.add(loPixels);

      image.initiateImage(loLoPixels);
      layer1.setImageInLayer(image);

      String files1 = "layer 1\nvisible\n0\n2\n2\n255\n0\n0\n255\n0\n"
          + "0\n255\n0\n0\n255\n0\n0";
      String files2 = "layer 2\nvisible\n0\n\n0\n0";

      List<InterfaceLayers> expected = new ArrayList<>();
      expected.add(layer1);
      expected.add(layer2);

      mock = new LoadLayerMock(files2, files1);
      List<InterfaceLayers> actual = mock.importLayer("random");

      assertEquals(actual.size(), expected.size());
      assertEquals(actual.get(0), expected.get(0));
      assertEquals(actual.get(1), expected.get(1));
    }
  }

  /**
   * Testing for the kernel functions.
   */
  public static class KernelTypeTest {

    @Test
    public void getMatrixOfBlur() {
      List<Double> array1 = new ArrayList<>();
      array1.add(1.0 / 16.0);
      array1.add(1.0 / 8.0);
      array1.add(1.0 / 16.0);
      List<Double> array2 = new ArrayList<>();
      array2.add(1.0 / 8.0);
      array2.add(1.0 / 4.0);
      array2.add(1.0 / 8.0);
      List<Double> array3 = new ArrayList<>();
      array3.add(1.0 / 16.0);
      array3.add(1.0 / 8.0);
      array3.add(1.0 / 16.0);

      List<List<Double>> matrix = new ArrayList<>();
      matrix.add(array1);
      matrix.add(array2);
      matrix.add(array3);

      assertEquals(matrix, KernelType.BLUR.getMatrix());
    }

    @Test
    public void getMatrixOfSharpen() {
      List<Double> array1 = new ArrayList<>();
      array1.add(-1.0 / 8.0);
      array1.add(-1.0 / 8.0);
      array1.add(-1.0 / 8.0);
      array1.add(-1.0 / 8.0);
      array1.add(-1.0 / 8.0);
      List<Double> array2 = new ArrayList<>();
      array2.add(-1.0 / 8.0);
      array2.add(1.0 / 4.0);
      array2.add(1.0 / 4.0);
      array2.add(1.0 / 4.0);
      array2.add(-1.0 / 8.0);
      List<Double> array3 = new ArrayList<>();
      array3.add(-1.0 / 8.0);
      array3.add(1.0 / 4.0);
      array3.add(1.0);
      array3.add(1.0 / 4.0);
      array3.add(-1.0 / 8.0);

      List<List<Double>> matrix = new ArrayList<>();
      matrix.add(array1);
      matrix.add(array2);
      matrix.add(array3);
      matrix.add(array2);
      matrix.add(array1);

      assertEquals(matrix, KernelType.SHARPEN.getMatrix());
    }
  }

  /**
   * Tests the methods inside ImportFileCommand class.
   */
  public static class ImportFileCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new ImportFileCommand(null, "hi");
      EnhanceCommandInterface cmd2 = new ImportFileCommand(ImportType.JPEG, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new ImportFileCommand(ImportType.PNG, "hi");
      cmd.apply(null);
    }

    @Test
    public void importTest() {
      String dots = "P3\n# Created by GIMP version 2.10.24 PNM plug-in\n2 2\n255\n"
          + "255\n0\n0\n255\n0\n0\n255\n0\n0\n255\n0\n0";

      EnhanceCommandInterface cmd = new ImportFileMock(dots);
      model.addLayer("layer1");
      cmd.apply(model);

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
    }

    @Test
    public void testImportCommand() {
      String easy = "P3\n3 3\n255\n255\n0\n0\n255\n0\n0\n255\n0\n0\n"
          + "255\n0\n0\n255\n0\n0\n255\n0\n0\n255\n0\n0\n255\n0\n0\n255\n0\n0";

      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<List<IPixels>> listOfPixels = new ArrayList<>();
      List<IPixels> rowRed = new ArrayList<>();
      rowRed.add(pixel);
      rowRed.add(pixel);
      rowRed.add(pixel);

      listOfPixels.add(rowRed);
      listOfPixels.add(rowRed);
      listOfPixels.add(rowRed);

      InterfaceImages expected = ImageCreator.create(ImageType.MATRIX2D);
      expected.initiateImage(listOfPixels);

      model.addLayer("hi");
      EnhanceCommandInterface mock = new ImportFileMock(easy);
      mock.apply(model);

      assertEquals(model.getCurrentLayer().getLayerImage(), expected);
    }
  }

  /**
   * Tests methods for {@code KernelCreator} method.
   */
  public static class KernelCreatorTest {

    IProcesses blur;
    IProcesses sharp;

    @Before
    public void setup() {
      blur = KernelCreator.create(KernelType.BLUR);
      sharp = KernelCreator.create(KernelType.SHARPEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullKernelType() {
      KernelCreator.create(null);
    }

    @Test
    public void blurKernelType() {
      assertEquals(blur.getClass(), KernelCreator.create(KernelType.BLUR).getClass());
    }

    @Test
    public void sharpenKernelType() {
      assertEquals(sharp.getClass(), KernelCreator.create(KernelType.SHARPEN).getClass());
    }
  }

  /**
   * Tests the methods inside ExportFileCommand class.
   */
  public static class ExportFileCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new ExportFileCommand(null, "hi");
      EnhanceCommandInterface cmd2 = new ExportFileCommand(ExportType.JPEG, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new ExportFileCommand(ExportType.PNG, "hi");
      cmd.apply(null);
    }

    @Test
    public void exportPPMCommand() {
      model.addLayer("hello");
      InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);

      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      image.initiateImage(redSquare);

      model.setImage(image);

      EnhanceCommandInterface cmd = new ExportFileCommand(ExportType.PPM, "hello");
      cmd.apply(model);
      File file = new File("hello.ppm");
      assertTrue(file.exists());

      file.delete();
    }

    @Test
    public void exportPNGCommand() {
      model.addLayer("hello");
      InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);

      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      image.initiateImage(redSquare);

      model.setImage(image);

      EnhanceCommandInterface cmd = new ExportFileCommand(ExportType.PNG, "hello");
      cmd.apply(model);
      File file = new File("hello.png");
      assertTrue(file.exists());

      file.delete();
    }

    @Test
    public void exportJPEGCommand() {
      model.addLayer("hello");
      InterfaceImages image = ImageCreator.create(ImageType.MATRIX2D);

      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      image.initiateImage(redSquare);

      model.setImage(image);

      EnhanceCommandInterface cmd = new ExportFileCommand(ExportType.JPEG, "hello");
      cmd.apply(model);
      File file = new File("hello.jpeg");
      assertTrue(file.exists());

      file.delete();
    }
  }

  /**
   * Tests methods in {@code CheckerBoardImageGenerator} class.
   */
  public static class CheckerBoardImageGenTest {

    ImageBuilderInterface board;
    IGenerateImage imageGen;

    @Before
    public void setup() {
      board = BuilderCreator.create(GeneratePresets.CHECKERBOARD);
      imageGen = board.generatePresetClass();
    }

    @Test
    public void generateImageTest() {
      board.setWidth(5);
      board.setHeight(5);
      imageGen = board.generatePresetClass();
      InterfaceImages image = imageGen.generateImage();

      List<List<IPixels>> loloPixels = new ArrayList<>();
      List<IPixels> blackWhite = new ArrayList<>();
      blackWhite.add(new PixelsRGB(0, 0, 0));
      blackWhite.add(new PixelsRGB(255, 255, 255));
      blackWhite.add(new PixelsRGB(0, 0, 0));
      blackWhite.add(new PixelsRGB(255, 255, 255));
      blackWhite.add(new PixelsRGB(0, 0, 0));

      List<IPixels> whiteBlack = new ArrayList<>();
      whiteBlack.add(new PixelsRGB(255, 255, 255));
      whiteBlack.add(new PixelsRGB(0, 0, 0));
      whiteBlack.add(new PixelsRGB(255, 255, 255));
      whiteBlack.add(new PixelsRGB(0, 0, 0));
      whiteBlack.add(new PixelsRGB(255, 255, 255));

      loloPixels.add(whiteBlack);
      loloPixels.add(blackWhite);
      loloPixels.add(whiteBlack);
      loloPixels.add(blackWhite);
      loloPixels.add(whiteBlack);

      InterfaceImages expectedImage = ImageCreator.create(ImageType.MATRIX2D);
      expectedImage.initiateImage(loloPixels);

      assertEquals(expectedImage, image);
    }
  }

  /**
   * Tests the methods inside ImageEnhanceCommandImport class.
   */
  public static class ImageScriptingTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test
    public void importBatchTest() {
      String s = "add hi # will ignore everything\n add in #hi\n save out";

      InterfaceScripting test = new ScriptingMock();
      String output = test.importBatchFile(s);

      assertEquals(output, "add hi add in save out ");
    }
  }

  /**
   * Tests the methods inside DeleteLayerCommand class.
   */
  public static class DeleteLayerCommandTest {

    private MultiLayeredEnhanceInterface model;

    @Before
    public void setUp() {
      model = new MultiLayerEnhanceModel();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullConstructor() {
      EnhanceCommandInterface cmd = new DeleteLayerCommand(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullModel() {
      EnhanceCommandInterface cmd = new DeleteLayerCommand("hi");
      cmd.apply(null);
    }

    @Test
    public void applyTest() {
      model.addLayer("hi");
      model.addLayer("hello");
      InterfaceLayers layer = new Layer2DMatrix();
      layer.setName("hi");
      layer.setVisibility(true);
      assertEquals(layer, model.getCurrentLayer());
      assertEquals(2, model.getNumLayers());
      EnhanceCommandInterface cmd = new DeleteLayerCommand("hi");
      cmd.apply(model);

      assertEquals(1, model.getNumLayers());
    }
  }

  /**
   * tests methods for Image2DMatrix class.
   */
  public static class Image2DMatrixTest {

    InterfaceImages img;
    List<List<Double>> easyMatrix;

    @Before
    public void setup() {
      img = ImageCreator.create(ImageType.MATRIX2D);
      easyMatrix = new ArrayList<>();
      List<Double> listOfDouble = new ArrayList<>();
      listOfDouble.add(0.0);
      listOfDouble.add(0.0);
      listOfDouble.add(0.0);
      easyMatrix.add(listOfDouble);
      easyMatrix.add(listOfDouble);
      easyMatrix.add(listOfDouble);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidNullInitiateImage() {
      img.initiateImage(null);
    }

    @Test
    public void validInitiateImage() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      assertEquals(pixel, img.getPixel(0, 0));
      assertEquals(pixel, img.getPixel(1, 0));
      assertEquals(pixel, img.getPixel(0, 1));
      assertEquals(pixel, img.getPixel(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullImageForFilterImage() {
      InterfaceImages nullImg;
      nullImg = ImageCreator.create(ImageType.MATRIX2D);
      nullImg.filterImage(easyMatrix, 0, 255);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatrixForFilterImage() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      img.filterImage(null, 0, 255);
    }

    @Test
    public void ensureLimitsOfMaxAndMin() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      img.filterImage(easyMatrix, -10, 256);
      //HOW TO TEST FOR

      assertEquals(new PixelsRGB(255, 0, 0), img.getPixel(0, 0));
    }

    @Test
    public void imgFiltersToWhite() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      InterfaceImages blackImg = img.filterImage(easyMatrix, 0, 255);
      IPixels blackPix = new PixelsRGB(0, 0, 0);
      assertEquals(blackPix, blackImg.getPixel(0, 0));
      assertEquals(blackPix, blackImg.getPixel(0, 1));
      assertEquals(blackPix, blackImg.getPixel(1, 0));
      assertEquals(blackPix, blackImg.getPixel(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullImageForTransformImage() {
      InterfaceImages nullImg;
      nullImg = ImageCreator.create(ImageType.MATRIX2D);
      nullImg.transformImage(easyMatrix, 0, 255);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMatrixForTransformImage() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      img.transformImage(null, 0, 255);
    }

    @Test
    public void ensureLimitMaxAndMinTransform() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      img.transformImage(easyMatrix, -4, 300);
      // HOW TO TEST FOR

      assertEquals(new PixelsRGB(255, 0, 0), img.getPixel(0, 0));
    }

    // SAME FOR IF MIN IS GREATER THAN MAX AND REVERSE

    @Test
    public void imgTransformsToWhite() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      InterfaceImages blackImg = img.transformImage(easyMatrix, 0, 255);
      IPixels blackPix = new PixelsRGB(0, 0, 0);
      assertEquals(blackPix, blackImg.getPixel(0, 0));
      assertEquals(blackPix, blackImg.getPixel(0, 1));
      assertEquals(blackPix, blackImg.getPixel(1, 0));
      assertEquals(blackPix, blackImg.getPixel(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyImageCannotCopy() {
      img.getDeepCopy();
    }

    @Test
    public void validImageToCopy() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      InterfaceImages deepCopyOfImg = img.getDeepCopy();
      assertEquals(img.getPixel(0, 0), deepCopyOfImg.getPixel(0, 0));
      assertEquals(img.getPixel(1, 0), deepCopyOfImg.getPixel(1, 0));
      assertEquals(img.getPixel(0, 1), deepCopyOfImg.getPixel(0, 1));
      assertEquals(img.getPixel(1, 1), deepCopyOfImg.getPixel(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyImageCantGetWidth() {
      img.getWidth();
    }

    @Test
    public void validImageGetWidth() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      assertEquals(2, img.getWidth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyImageCantGetHeight() {
      img.getHeight();
    }

    @Test
    public void validImageGetHeight() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      assertEquals(2, img.getHeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyImgCantGetPixel() {
      img.getPixel(0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidColumnCannotGetPixel() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      img.getPixel(0, 5);
    }

    @Test
    public void getRedPixel() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      assertEquals(pixel, img.getPixel(0, 1));
      assertEquals(pixel, img.getPixel(1, 1));
    }

    @Test
    public void testEqualsDeepCopy() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      InterfaceImages copy = img.getDeepCopy();
      assertEquals(true, img.equals(copy));
    }

    @Test
    public void testEqualsNotSameObject() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);
      assertEquals(false, img.equals("NOT AN IMAGE"));
    }

    @Test
    public void testEqualsOfDifferentHeight() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      InterfaceImages notSame = ImageCreator.create(ImageType.MATRIX2D);
      List<IPixels> listOfRed3 = new ArrayList<>();
      listOfRed3.add(pixel);
      listOfRed3.add(pixel);
      List<List<IPixels>> redRect = new ArrayList<>();
      redRect.add(listOfRed3);
      redRect.add(listOfRed3);
      redRect.add(listOfRed3);
      notSame.initiateImage(redRect);

      assertEquals(false, img.equals(notSame));
    }

    @Test
    public void testEqualsOfDifferentWidth() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      InterfaceImages notSame = ImageCreator.create(ImageType.MATRIX2D);
      List<IPixels> listOfRed3 = new ArrayList<>();
      listOfRed3.add(pixel);
      listOfRed3.add(pixel);
      listOfRed3.add(pixel);
      List<List<IPixels>> redRect = new ArrayList<>();
      redRect.add(listOfRed3);
      redRect.add(listOfRed3);
      notSame.initiateImage(redRect);

      assertEquals(false, img.equals(notSame));
    }

    @Test
    public void testEqualsOfDifferentPixel() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      InterfaceImages notSame = ImageCreator.create(ImageType.MATRIX2D);
      IPixels bPixel = new PixelsRGB(45, 0, 45);
      List<IPixels> listOfRed3 = new ArrayList<>();
      listOfRed3.add(bPixel);
      listOfRed3.add(bPixel);
      List<List<IPixels>> difColor = new ArrayList<>();
      difColor.add(listOfRed3);
      difColor.add(listOfRed3);
      notSame.initiateImage(difColor);

      assertEquals(false, img.equals(notSame));
    }

    @Test
    public void testHashCode() {
      IPixels pixel = new PixelsRGB(255, 0, 0);
      List<IPixels> listOfRed = new ArrayList<>();
      listOfRed.add(pixel);
      listOfRed.add(pixel);
      List<List<IPixels>> redSquare = new ArrayList<>();
      redSquare.add(listOfRed);
      redSquare.add(listOfRed);
      img.initiateImage(redSquare);

      assertEquals(286, img.hashCode());
    }
  }

  /**
   * Tests the methods for {@code ExportModelCreator} class.
   */
  public static class ExportModelCreatorTest {

    ExportInterface ppm;
    ExportInterface jpeg;
    ExportInterface png;

    @Before
    public void setup() {
      ppm = ExportModelCreator.create(ExportType.PPM);
      jpeg = ExportModelCreator.create(ExportType.JPEG);
      png = ExportModelCreator.create(ExportType.PNG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullExportType() {
      ExportModelCreator.create(null);
    }

    @Test
    public void ppmExportCreate() {
      assertEquals(ppm.getClass(), ExportModelCreator.create(ExportType.PPM).getClass());
    }

    @Test
    public void jpegExportCreate() {
      assertEquals(jpeg.getClass(), ExportModelCreator.create(ExportType.JPEG).getClass());
    }

    @Test
    public void pngExportCreate() {
      assertEquals(png.getClass(), ExportModelCreator.create(ExportType.PNG).getClass());
    }
  }

  /**
   * Tests the methods inside CreateDirectoryConcrete class.
   */
  public static class CreateDirectoryConcreteTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullDirectory() {
      CreateDirectoryConcrete.createDirectory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void directoryExisted() {
      File delete = new File("testFolder");
      if (delete.exists()) {
        delete.delete();
      }

      CreateDirectoryConcrete.createDirectory("testFolder");
      CreateDirectoryConcrete.createDirectory("testFolder");
    }

    @Test
    public void createDirectory() {
      File delete = new File("testFolder1");
      if (delete.exists()) {
        delete.delete();
      }

      CreateDirectoryConcrete.createDirectory("testFolder1");

      try {
        CreateDirectoryConcrete.createDirectory("testFolder1");
      } catch (IllegalArgumentException e) {
        int x = 0;
        assertEquals(0, x);
      }
    }
  }

  /**
   * Tests the methods for {@code BuilderCreator} class.
   */
  public static class BuilderCreatorTest {

    CheckerBoardBuilder checkBoard;

    @Before
    public void setup() {
      checkBoard = new CheckerBoardBuilder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullGeneratePresets() {
      BuilderCreator.create(null);
    }

    @Test
    public void checkerBoardCreate() {
      assertEquals(checkBoard.getClass(),
          BuilderCreator.create(GeneratePresets.CHECKERBOARD).getClass());
    }
  }

  /**
   * Tests for {@Code CheckerBoardBuilder} class.
   */
  public static class CheckerBoardBuilderTest {

    ImageBuilderInterface image;

    @Before
    public void setup() {
      image = BuilderCreator.create(GeneratePresets.CHECKERBOARD);
    }

    @Test
    public void checkReset() {
      ImageBuilderInterface resetImage = BuilderCreator.create(GeneratePresets.CHECKERBOARD);
      image.setHeight(200);
      image.setWidth(300);
      image.reset();

      InterfaceImages built = image.generatePresetClass().generateImage();

      assertNotEquals(200, built.getHeight());
      assertNotEquals(300, built.getWidth());
    }

    @Test
    public void changeWidth() {
      ImageBuilderInterface resetImage = BuilderCreator.create(GeneratePresets.CHECKERBOARD);

      image.setWidth(300);
      InterfaceImages built = image.generatePresetClass().generateImage();
      InterfaceImages unChanged = resetImage.generatePresetClass().generateImage();

      assertEquals(300, built.getWidth());
      assertEquals(500, unChanged.getWidth());
    }

    @Test
    public void changeHeight() {
      ImageBuilderInterface resetImage = BuilderCreator.create(GeneratePresets.CHECKERBOARD);

      image.setHeight(300);
      InterfaceImages built = image.generatePresetClass().generateImage();
      InterfaceImages unChanged = resetImage.generatePresetClass().generateImage();

      assertEquals(300, built.getHeight());
      assertEquals(500, unChanged.getHeight());

    }

    @Test
    public void changeColors() {
      ImageBuilderInterface changedColor = BuilderCreator.create(GeneratePresets.CHECKERBOARD);
      List<Color> newColor = new ArrayList<>();
      newColor.add(Color.RED);
      newColor.add(Color.BLUE);

      changedColor.setColor(newColor);
      InterfaceImages imageChangedColor = changedColor.generatePresetClass().generateImage();
      IGenerateImage simpleImage = image.generatePresetClass();
      InterfaceImages normal = simpleImage.generateImage();

      assertEquals(new PixelsRGB(0, 0, 255),
          imageChangedColor.getPixel(0, 0));
      assertEquals(new PixelsRGB(255, 0, 0),
          imageChangedColor.getPixel(100, 0));

      assertEquals(new PixelsRGB(0, 0, 0),
          normal.getPixel(100, 0));
      assertEquals(new PixelsRGB(255, 255, 255),
          normal.getPixel(0, 0));
    }

    @Test
    public void changeIntervals() {
      image.setInterval(0.01);
      InterfaceImages built = image.generatePresetClass().generateImage();
      IPixels bPix = new PixelsRGB(0, 0, 0);
      IPixels wPix = new PixelsRGB(255, 255, 255);
      assertEquals(wPix, built.getPixel(0, 0));
      assertEquals(bPix, built.getPixel(0, 1));
      assertEquals(wPix, built.getPixel(0, 2));
      assertEquals(bPix, built.getPixel(0, 3));
      assertEquals(wPix, built.getPixel(0, 4));
    }

    /**
     * The test class for the overwitten stack class.
     */
    public static class FixedSizeStackTest {

      @Test(expected = IllegalArgumentException.class)
      public void pushNull() {
        Stack<Integer> stackOfInteger = new FixedSizeStack<>(50);

        stackOfInteger.push(null);
      }

      @Test
      public void pushTest() {
        Stack<String> stackOfStrings = new FixedSizeStack<>(4);
        stackOfStrings.push("a");
        stackOfStrings.push("b");
        stackOfStrings.push("c");

        assertEquals("c", stackOfStrings.pop());

        stackOfStrings.push("d");
        assertEquals("a", stackOfStrings.get(0));

        stackOfStrings.push("e");
        stackOfStrings.push("f");
        assertEquals("b", stackOfStrings.get(0));
        assertEquals("f", stackOfStrings.pop());
      }
    }

    /**
     * Tests the methods for {@code ImportModelCreator} class.
     */
    public static class ImportModelCreatorTest {

      ImportInterface ppm;
      ImportInterface png;
      ImportInterface jpeg;

      @Before
      public void setup() {
        ppm = ImportModelCreator.create(ImportType.PPM);
        png = ImportModelCreator.create(ImportType.PNG);
        jpeg = ImportModelCreator.create(ImportType.JPEG);
      }

      @Test(expected = IllegalArgumentException.class)
      public void nullImportTypeCannotCreate() {
        ImportModelCreator.create(null);
      }

      @Test
      public void createPPMImport() {
        assertEquals(ppm.getClass(), ImportModelCreator.create(ImportType.PPM).getClass());
      }

      @Test
      public void createPNGImport() {
        assertEquals(png.getClass(), ImportModelCreator.create(ImportType.PNG).getClass());
      }

      @Test
      public void createJPEGImport() {
        assertEquals(jpeg.getClass(), ImportModelCreator.create(ImportType.JPEG).getClass());
      }
    }

    /**
     * Tests the methods inside RunScriptCommand class.
     */
    public static class RunScriptCommandTest {

      private MultiLayeredEnhanceInterface model;

      @Before
      public void setUp() {
        model = new MultiLayerEnhanceModel();
      }

      @Test(expected = IllegalArgumentException.class)
      public void nullConstructor() {
        EnhanceCommandInterface cmd = new RunScriptCommand(null, new StringBuilder());
        EnhanceCommandInterface cmd2 = new RunScriptCommand("hi", null);
      }

      @Test(expected = IllegalArgumentException.class)
      public void nullModel() {
        EnhanceCommandInterface cmd = new RunScriptCommand("hi", new StringBuilder());
        cmd.apply(null);
      }

      @Test
      public void testRunScriptCommand() {
        String command = "add hi # will ignore everything\n add in #hi\n save out";
        StringBuilder log = new StringBuilder();
        EnhanceCommandInterface cmd = new RunScriptMock(command, log);
        cmd.apply(model);

        assertEquals(log.toString(), "add hi add in save out ");
      }
    }

    /**
     * Tests the methods inside SaveCommand class.
     */
    public static class SaveCommandTest {

      private MultiLayeredEnhanceInterface model;

      @Before
      public void setUp() {
        model = new MultiLayerEnhanceModel();
      }

      @Test(expected = IllegalArgumentException.class)
      public void nullConstructor() {
        EnhanceCommandInterface cmd = new SaveCommand(null);
      }

      @Test(expected = IllegalArgumentException.class)
      public void nullModel() {
        EnhanceCommandInterface cmd = new SaveCommand("hi");
        cmd.apply(null);
      }

      @Test
      public void saveTest() {
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

        EnhanceCommandInterface mock = new SaveCommandMock("hi", log);
        mock.apply(model);

        assertEquals("layer1\nvisible\n0\n2 2\n255 0 0\n255 0 0\n255 0 "
            + "0\n255 0 0layer2\nvisible\n1\n2 2\n255 0 0\n255 0 0\n255 0 0\n255 "
            + "0 0hi/layersLocation", log.toString());
      }
    }

    /**
     * Test class for save im2dmatrix.
     */
    public static class SaveIm2DMatrixTest {

      @Test
      public void saveMultiTest() {
        StringBuilder log = new StringBuilder();

        InterfaceLayers layer1 = new Layer2DMatrix();
        layer1.setName("layer1");
        InterfaceLayers layer2 = new Layer2DMatrix();
        layer2.setName("layer2");

        InterfaceImages img = ImageCreator.create(ImageType.MATRIX2D);
        List<List<IPixels>> loloPixels = new ArrayList<>();
        IPixels red = new PixelsRGB(255, 0, 0);
        List<IPixels> row = new ArrayList<>();
        row.add(red);
        row.add(red);

        loloPixels.add(row);
        loloPixels.add(row);
        img.initiateImage(loloPixels);
        layer1.setImageInLayer(img);
        layer2.setImageInLayer(img);

        List<InterfaceLayers> loLayers = new ArrayList<>();
        loLayers.add(layer1);
        loLayers.add(layer2);

        SaveMultiInterface mock = new SaveIm2DMock(log);
        mock.saveMultiLayersImage(loLayers, "hi");

        assertEquals("layer1\nvisible\n0\n2 2\n255 0 0\n255 0 0\n255 0 "
            + "0\n255 0 0layer2\nvisible\n1\n2 2\n255 0 0\n255 0 0\n255 0 0\n255 "
            + "0 0hi/layersLocation", log.toString());
      }
    }
  }
}