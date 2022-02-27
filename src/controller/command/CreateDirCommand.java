package controller.command;

import controller.command.EnhanceCommandInterface;
import controller.CreateDirectoryConcrete;
import model.MultiLayeredEnhanceInterface;

/**
 * Represents the user creating a directory for the model.
 */
public class CreateDirCommand implements EnhanceCommandInterface {

  private final String directory;

  /**
   * constructor for CreateDirCommand that takes in a string.
   *
   * @param directory String that will be where the file is saved.
   */
  public CreateDirCommand(String directory) {
    if (directory == null) {
      throw new IllegalArgumentException("Directory provided cannot be null.");
    }
    this.directory = directory;
  }

  @Override
  public void apply(MultiLayeredEnhanceInterface m) {
    if (m == null) {
      throw new IllegalArgumentException("Model provided cannot be null.");
    }

    CreateDirectoryConcrete.createDirectory(directory);
  }
}