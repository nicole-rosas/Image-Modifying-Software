package controller.command;

import controller.ImportInterface;
import controller.importing.ImportModelCreator;
import controller.importing.ImportType;
import model.MultiLayeredEnhanceInterface;

/**
 * Importing Command class for future implementation.
 */
public class ImportFileCommand implements EnhanceCommandInterface {
  private ImportType type;
  private final String filename;

  /**
   * Constructor for the class.
   */
  public ImportFileCommand(ImportType type, String filename) {
    if (filename == null || type == null) {
      throw new IllegalArgumentException("File name or type provided cannot be null.");
    }

    this.filename = filename;
    this.type = type;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    ImportInterface importModel = ImportModelCreator.create(type);
    m.setImage(importModel.importImage(filename));
  }
}
