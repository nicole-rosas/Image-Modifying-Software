package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.ImportInterface;
import controller.importing.PPMImportMock;
import model.MultiLayeredEnhanceInterface;

/**
 * Mock for ImportFileCommand to be use for testing.
 */
public class ImportFileMock implements EnhanceCommandInterface {

  private String content;

  /**
   * Constructor for ImportFileMock that is used for testing.
   */
  public ImportFileMock(String content) {
    if (content == null) {
      throw new IllegalArgumentException("Content provided cannot be null.");
    }

    this.content = content;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    ImportInterface importModel = new PPMImportMock();
    m.setImage(importModel.importImage(this.content));
  }
}
