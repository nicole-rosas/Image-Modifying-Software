package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.SaveIm2DMock;
import controller.SaveMultiInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Mock for SaveCommand class that is to be used for tests.
 */
public class SaveCommandMock implements EnhanceCommandInterface {

  private final String location;
  private StringBuilder log;

  /**
   * Constructor for SaveCommandMock to be used for testing and takes in string and StringBuilder.
   * @param location String of where the image is saved.
   * @param log StringBuilder
   */
  public SaveCommandMock(String location, StringBuilder log) {
    if (location == null || log == null) {
      throw new IllegalArgumentException("Location provided cannot be null.");
    }

    this.location = location;
    this.log = log;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    SaveMultiInterface saveMock = new SaveIm2DMock(log);
    saveMock.saveMultiLayersImage(m.getAllLayers(), location);
  }
}
