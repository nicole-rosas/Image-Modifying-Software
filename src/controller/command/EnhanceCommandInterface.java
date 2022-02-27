package controller.command;

import model.MultiLayeredEnhanceInterface;

/**
 * interface for the commands the user can use to alter/edit in image processing file.
 */
public interface EnhanceCommandInterface {

  /**
   * runs the command to the given model.
   * @param m model that will run the command.
   */
  void apply(MultiLayeredEnhanceInterface m);
}
