package controller.command;

import controller.export.ExportInterface;
import controller.export.ExportModelCreator;
import controller.export.ExportType;
import model.MultiLayeredEnhanceInterface;
import model.images.image.InterfaceImages;

/**
 * Represents the user exporting the image processing file.
 */
public class ExportFileCommand implements EnhanceCommandInterface {
  private final ExportType type;
  private final String filename;

  /**
   * Constructor for ExportFileCommand that takes in an ExportType and String.
   * @param type will determine what the file exports as.
   * @param filename the name of the exported file.
   */
  public ExportFileCommand(ExportType type, String filename) {
    if (type == null || filename == null) {
      throw new IllegalArgumentException("Parameters provided cannot be null.");
    }
    this.type = type;
    this.filename = filename;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    ExportInterface modelCreator = ExportModelCreator.create(type);
    InterfaceImages image = m.getTopVisLayerImage();
    modelCreator.export(image, filename);
  }
}
