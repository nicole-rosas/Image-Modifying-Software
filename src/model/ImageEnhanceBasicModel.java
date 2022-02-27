package model;

import model.generateimage.BuilderCreator;
import model.generateimage.GeneratePresets;
import model.generateimage.IGenerateImage;
import model.generateimage.ImageBuilderInterface;
import model.images.image.InterfaceImages;
import model.processes.colortransform.TransformCreator;
import model.processes.IProcesses;
import model.processes.kernelconvolution.KernelCreator;
import model.processes.KernelType;
import model.processes.TransformType;
import java.awt.Color;
import java.util.List;

/**
 * Represents an ImageEnhanceModel that takes in a simple image file(PPM)
 * and processes it.
 */
public class ImageEnhanceBasicModel extends ImageEnhanceModelAbstract {

  /**
   * Constructor of ImageEnhanceModel that takes in nothing.
   */
  public ImageEnhanceBasicModel() {
    super();
  }

  @Override
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Name given cannot be null.");
    }
    else if (name.length() == 0) {
      this.filename = "image";
    }
    else {
      this.filename = name;
    }
  }

  @Override
  public String getName() {
    return this.filename;
  }

  @Override
  public InterfaceImages getImage() {
    return this.image.getDeepCopy();
  }

  @Override
  public void transformSepia() {
    this.transformCustomClamp(TransformType.SEPIA, 0, 255);
  }

  @Override
  public void convolutionBlur() {
    this.convolutionCustomClamp(KernelType.BLUR, 0, 255);
  }

  @Override
  public void transformGreyscale() {
    this.transformCustomClamp(TransformType.GREYSCALE, 0, 255);
  }

  @Override
  public void convolutionSharpen() {
    this.convolutionCustomClamp(KernelType.SHARPEN, 0, 255);
  }

  /**
   * Given the tranform type, clamp it at the given min, max values.
   * @param type The type of transform process.
   * @param min minimum clamp value.
   * @param max maximum clamp value.
   */
  private void transformCustomClamp(TransformType type, int min, int max) {
    if (type == null) {
      throw new IllegalArgumentException("Transform Type cannot be null.");
    }

    //  this.saveImage(image.getDeepCopy());

    // No need to create a copy of the image, because the process will create a new instance of
    // the image.
    IProcesses process = TransformCreator.create(type);
    this.image = process.apply(image, type.getMatrix(), min, max);
  }

  /**
   * Given the KernelType, clamp it at the given min and max.
   * @param type the type of convolve process.
   * @param min minimum clamp value.
   * @param max maximum clamp value.
   */
  private void convolutionCustomClamp(KernelType type, int min, int max) {
    if (type == null) {
      throw new IllegalArgumentException("Convolution Type cannot be null.");
    }

    //  this.saveImage(image.getDeepCopy());

    IProcesses process = KernelCreator.create(type);
    // No need to create a copy of the image, because the process will create a new instance of
    // the image.
    this.image = process.apply(image, type.getMatrix(), min, max);
  }

  @Override
  public void setImage(InterfaceImages image) {
    if (image == null) {
      throw new IllegalArgumentException("Image given cannot be null.");
    }
    this.image = image.getDeepCopy();
  }

  /**
   * saves a given image.
   * @param images image to be saved.
   */
  private void saveImage(InterfaceImages images) {
    if (images == null) {
      throw new IllegalArgumentException("Image to be copied cannot be null.");
    }
    savedImages.push(images);
  }

  @Override
  public void generateCheckerBoardImage(int width, int height,
      Double interval, List<Color> color) {
    if (color == null) {
      throw new IllegalArgumentException("preset or color is null!");
    }

    GeneratePresets presetType = GeneratePresets.CHECKERBOARD;
    ImageBuilderInterface builder = BuilderCreator.create(presetType);
    builder.setHeight(height);
    builder.setWidth(width);
    builder.setColor(color);
    builder.setInterval(interval);

    IGenerateImage imageGenerator = builder.generatePresetClass();

    this.image = imageGenerator.generateImage();
  }
}
