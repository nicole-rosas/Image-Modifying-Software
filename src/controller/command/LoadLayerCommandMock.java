package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.LoadLayerMock;
import controller.LoadMultiInterface;
import model.MultiLayeredEnhanceInterface;

/**
 * Mock for LoadLayerCommand that is to be used for testing.
 */
public class LoadLayerCommandMock implements EnhanceCommandInterface {

  private final LoadMultiInterface loader;

  /**
   * Constructor for LoadLayerCommandMock that is used for testing.
   */
  public LoadLayerCommandMock(String... files) {
    loader = new LoadLayerMock(files);
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    m.setIntoLayers(loader.importLayer("random"));
  }
}
